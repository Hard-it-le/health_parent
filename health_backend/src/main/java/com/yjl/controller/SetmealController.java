package com.yjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yjl.constant.MessageConstant;
import com.yjl.constant.RedisConstant;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.pojo.Setmeal;
import com.yjl.service.SetmealService;
import com.yjl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 体检套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    //使用JedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    /**
     * 文件上传
     *
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        System.out.println(imgFile);
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //获取文件后缀
        String extention = originalFilename.substring(lastIndexOf - 1);
        //使用UUID随机产生文件名称，防止同名文件覆盖
        String fileName = UUID.randomUUID().toString() + extention;
        try {
            //将文件上传到七牛云服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //将图片名称保存到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            //文件保存失败
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        //文件保存成功
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
    }


    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.pageQuery(queryPageBean);
    }


    /**
     * 根据id删除套餐
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            setmealService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    /**
     * 根据套餐id查询套餐信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            /*System.out.println(setmeal);*/
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

    /**
     *根据套餐id查出中间表的数据
     * @param id
     * @return
     */
    @RequestMapping("/findCheckGroupIdssAndSetmealId")
    public Result findCheckGroupIdssAndSetmealId(Integer id) {
        try {
            List<Integer> checkgroupIds = setmealService.findCheckGroupIdssAndSetmealId(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);//新增失败
        }
    }

    /**
     * 编辑套餐id
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/exit")
    public Result exit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.exit(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

}
