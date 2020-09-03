package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.User;

import java.util.List;

/**
 * @author yjl
 * @create 2020-08-26-19:10
 **/
public interface UserManageService {
    public PageResult pageQuery(QueryPageBean queryPageBean);

    void add(User user, Integer[] roleIds);

    public User findById(Integer id);

    public List<Integer> findUserIdsAndRoleIds(Integer id);

    public void exit(User user, Integer[] roleIds);

    public void deleteById(Integer id);
}
