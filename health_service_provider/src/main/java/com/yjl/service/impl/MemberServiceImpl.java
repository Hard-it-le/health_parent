package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yjl.mapper.MemberMapper;
import com.yjl.pojo.Member;
import com.yjl.service.MemberService;
import com.yjl.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yjl
 * @create 2020-08-08-14:38
 **/
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberDao;

    /**
     * 根据手机号查询会员
     *
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加用户信息
     *
     * @param member
     */
    @Override
    public void add(Member member) {
        if (member.getPassword() != null) {
            //使用md5将明文密码加密
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    /**
     * 根据月份查询会员数量
     *
     * @param months
     * @return
     */
    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + ".31";
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
    }
}
