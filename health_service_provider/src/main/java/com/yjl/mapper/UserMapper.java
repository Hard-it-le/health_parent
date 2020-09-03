package com.yjl.mapper;

import com.yjl.pojo.User;

/**
 * @author yjl
 * @create 2020-08-14-16:44
 **/
public interface UserMapper {
    public User findByUsername(String username);
}
