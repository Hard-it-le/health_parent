package com.yjl.service;

import com.yjl.pojo.User;

/**
 * @author yjl
 * @create 2020-08-14-16:29
 * 查询用户的接口
 **/
public interface UserService {

    public User findByUsername(String username);
}
