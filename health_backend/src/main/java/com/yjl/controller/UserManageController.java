package com.yjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yjl.constant.MessageConstant;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.pojo.Role;
import com.yjl.pojo.User;
import com.yjl.service.UserManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-19:08
 **/
@RestController
@RequestMapping("/userManage")
public class UserManageController {

    @Reference
    private UserManageService userManageService;

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            userManageService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    /**
     * 编辑用户信息
     *
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("/exit")
    public Result exit(@RequestBody User user, Integer[] roleIds) {
        try {
            userManageService.exit(user, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


    /**
     * 查询用户相关的角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/findUserIdsAndRoleId")
    public Result findUserIdsAndRoleId(Integer id) {
        try {
            List<Integer> roleIds = userManageService.findUserIdsAndRoleIds(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            //修改失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            User user = userManageService.findById(id);
            return new Result(true, MessageConstant.ADD_PERMISSION_success, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
    }


    /**
     * 添加用户
     *
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds) {
        try {
            userManageService.add(user, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.ADD_PERMISSION_success);
    }


    /**
     * 角色的分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return userManageService.pageQuery(queryPageBean);
    }

}
