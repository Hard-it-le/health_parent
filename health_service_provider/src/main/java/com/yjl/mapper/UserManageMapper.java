package com.yjl.mapper;

import com.github.pagehelper.Page;
import com.yjl.pojo.Role;
import com.yjl.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-26-19:15
 **/
public interface UserManageMapper {
    public Page<User> selectByCondition(String queryString);

    public void add(User user);

    public void setUserAndRole(Map map);

    public User findById(Integer id);

    public List<Integer> findUserIdsAndRoleId(Integer id);

    public void exit(User user);

    public void deleteAssoication(Integer id);

    public void deleteById(Integer id);
}
