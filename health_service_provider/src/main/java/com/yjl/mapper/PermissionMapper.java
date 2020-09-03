package com.yjl.mapper;

import com.yjl.pojo.Permission;

import java.util.Set;

/**
 * @author yjl
 * @create 2020-08-14-16:50
 **/
public interface PermissionMapper {
   public Set<Permission> findByRoleId(Integer roleId);
}
