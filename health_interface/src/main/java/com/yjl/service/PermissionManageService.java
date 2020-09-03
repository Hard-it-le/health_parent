package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.Permission;
import com.yjl.pojo.Role;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-18:11
 **/
public interface PermissionManageService {
    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void add(Permission permission);

    public List<Role> findAll();

    public void deleteById(Integer id);

    public Permission findById(Integer id);

    public void edit(Permission permission);
}
