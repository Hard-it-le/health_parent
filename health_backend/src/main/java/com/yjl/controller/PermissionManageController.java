package com.yjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yjl.constant.MessageConstant;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.pojo.Permission;
import com.yjl.pojo.Role;
import com.yjl.service.PermissionManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-18:09
 **/
@RestController
@RequestMapping("/permissionManage")
public class PermissionManageController {

    @Reference
    private PermissionManageService permissionManageService;


    /**
     * 根据id修改权限信息
     *
     * @param permission
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            permissionManageService.edit(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


    /**
     * 根据id查询单个权限信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Permission permission = permissionManageService.findById(id);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permission);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


    /**
     * 根据权限id进行删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            permissionManageService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);

    }


    /**
     * 查询所有权限信息
     *
     * @param permission
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(Permission permission) {
        try {
            List<Role> list = permissionManageService.findAll();
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }

    }


    /**
     * 新增权限
     *
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission) {
        try {
            permissionManageService.add(permission);
        } catch (Exception e) {
            e.printStackTrace();
            //新增失败
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.ADD_PERMISSION_success);
    }

    /**
     * 权限分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return permissionManageService.pageQuery(queryPageBean);
    }


}
