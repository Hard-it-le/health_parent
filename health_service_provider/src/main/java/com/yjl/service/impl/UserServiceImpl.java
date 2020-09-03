package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yjl.mapper.PermissionMapper;
import com.yjl.mapper.RoleMapper;
import com.yjl.mapper.UserMapper;
import com.yjl.pojo.Permission;
import com.yjl.pojo.Role;
import com.yjl.pojo.User;
import com.yjl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author yjl
 * @create 2020-08-14-16:43
 **/
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    @Autowired
    private RoleMapper roleDao;
    @Autowired
    private PermissionMapper permissionDao;

    /**
     * 根据用户名查询数据库获取用户信息和关联的用户信息同时需要查询角色的权限信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        //查询用户基本信息，不包含角色
        User user = userDao.findByUsername(username);
        if (user == null) {
            //用户不存在
            return null;
        }
        Integer userId = user.getId();
        //根据用户id查询对应的角色
        Set<Role> roles = roleDao.findByUserId(userId);
        //根据角色id查询所对应的权限
        for (Role role : roles) {
            Integer roleId = role.getId();
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            //让角色关联权限
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
        return user;
    }
}
