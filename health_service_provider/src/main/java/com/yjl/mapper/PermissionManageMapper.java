package com.yjl.mapper;

import com.github.pagehelper.Page;
import com.yjl.pojo.Permission;
import com.yjl.pojo.Role;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-18:16
 **/
public interface PermissionManageMapper {

    public Page<Role> selectByCondition(String queryString);

    public void add(Permission permission);

    public List<Role> findAll();

    public long findCountByRoleId(Integer id);

    public void deleteById(Integer id);

    public Permission findById(Integer id);

    public void edit(Permission permission);
}
