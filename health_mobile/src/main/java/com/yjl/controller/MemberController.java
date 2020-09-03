package com.yjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yjl.constant.MessageConstant;
import com.yjl.constant.RedisMessageConstant;
import com.yjl.entity.Result;
import com.yjl.pojo.Member;
import com.yjl.service.MemberService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-08-9:04
 * 用户相关的控制器
 **/
@RestController
@RequestMapping("/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 手机号快速登录
     * @param map
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestBody Map map, HttpServletResponse  response){

        // 1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        //从map中取出手机号和验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //  2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
        //从redis中获得保存的验证码
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCodeInRedis != null && validateCode!= null && validateCode.equals(validateCodeInRedis)){
            //验证码正确
            //判断当前用户是否为会员（查询会员表来确定）
            Member member = memberService.findByTelephone(telephone);
            if (member != null){
                //不是会员，自动完成注册（自动将当前用户信息保存到会员表）
                //获取注册时间
                member.setRegTime(new Date());
                //获取手机号
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }
            // 3、向客户端写入Cookie，内容为用户手机号
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            //  4、将会员信息保存到Redis，使用手机号作为key，保存时长为30分钟
            //路径
            cookie.setPath("/");
            //设置cookie保存时间
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            //将会员信息保存到redis中
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,60*30,json);
            return  new Result(true, MessageConstant.LOGIN_SUCCESS);
        }else {
            //验证码错误
            return  new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }

    }
}
