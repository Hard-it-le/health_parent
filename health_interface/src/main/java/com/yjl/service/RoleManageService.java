package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.CheckGroup;
import com.yjl.pojo.Role;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-19:22
 **/
public interface RoleManageService {
    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    public PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 数据回显
     *
     * @param id
     * @return
     */
    public List<Integer> findPermissionIdsByRoleIds(Integer id);


    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     */
    public void add(Role role, Integer[] permissionIds);

    /**
     * 根据id删除角色
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    public Role findById(Integer id);

    /**
     * 修改角色权限和信息
     *
     * @param role
     * @param permissionIds
     */
    public void edit(Role role, Integer[] permissionIds);

    public List<Role> findAll(Role role);
}
