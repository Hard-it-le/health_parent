package com.yjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yjl.constant.MessageConstant;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.pojo.Role;
import com.yjl.service.RoleManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-19:21
 **/
@RestController
@RequestMapping("/roleManage")
public class RoleManageController {

    @Reference
    private RoleManageService roleManageService;


    @RequestMapping("/findAll")
    public Result findAll(Role role) {
        try {
            List<Role> list = roleManageService.findAll(role);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }


    /**
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Role role = roleManageService.findById(id);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


    /**
     * 根据id删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            roleManageService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Role role, Integer[] permissionIds) {
        try {
            roleManageService.add(role, permissionIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);

    }

    /**
     * 修改角色权限
     *
     * @param role
     * @param permissionIds
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer[] permissionIds) {
        try {
            roleManageService.edit(role, permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);

    }


    /**
     * 分页查询角色
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return roleManageService.pageQuery(queryPageBean);
    }

    /**
     * 根据角色id查询权限信息回显
     *
     * @param id
     * @return
     */
    @RequestMapping("/findPermissionIdsByRoleIds")
    public Result findPermissionIdsByRoleIds(Integer id) {
        try {
            List<Integer> permissionIds = roleManageService.findPermissionIdsByRoleIds(id);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }


}
