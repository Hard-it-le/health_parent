package com.yjl.mapper;

import com.github.pagehelper.Page;
import com.yjl.pojo.Role;

import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-26-19:25
 **/
public interface RoleManageMapper {
    public Page<Role> selectByCondition(String queryString);

    public List<Integer> findPermissionIdsByRoleIds(Integer id);

    public void add(Role role);

    public void setRoleAndPermission(Map map);


    public long findCountByRoleId(Integer id);

    public Role findById(Integer id);

    public void exit(Role role);

    /***
     * 删除中间表
     * @param id
     */
    public void deleteAssoication(Integer id);

    public void deleteById(Integer id);

   public List<Role> findAll(Role role);
}
