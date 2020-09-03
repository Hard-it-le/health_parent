package com.yjl.mapper;

import com.yjl.pojo.Role;

import java.util.Set;

/**
 * @author yjl
 * @create 2020-08-14-16:47
 **/
public interface RoleMapper {
    public Set<Role> findByUserId(Integer userId);
}
