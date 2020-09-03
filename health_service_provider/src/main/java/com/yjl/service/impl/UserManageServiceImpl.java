package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.mapper.UserManageMapper;
import com.yjl.pojo.Role;
import com.yjl.pojo.User;
import com.yjl.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-26-19:10
 **/
@Service(interfaceClass = UserManageService.class)
@Transactional
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private UserManageMapper userManageMapper;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //这里调用分页助手来设置分页
        PageHelper.startPage(currentPage, pageSize);
        Page<User> page = userManageMapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<User> rows = page.getResult();
        return new PageResult(total, rows);
    }

    /**
     * 添加用户
     *
     * @param user
     * @param roleIds
     */
    @Override
    public void add(User user, Integer[] roleIds) {
        //新增检查组，操作t_user表
        userManageMapper.add(user);
        //设置检查组和检查项的多对多的关联关系，操作t_user_role表
        Integer userId = user.getId();
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("userId", userId);
                userManageMapper.setUserAndRole(map);
            }
        }
    }

    /**
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return userManageMapper.findById(id);
    }

    /**
     * 查询中间表信息回显
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findUserIdsAndRoleIds(Integer id) {
        return userManageMapper.findUserIdsAndRoleId(id);
    }

    /**
     * 修改用户
     *
     * @param user
     * @param roleIds
     */
    @Override
    public void exit(User user, Integer[] roleIds) {
        //修改编辑检查组
        userManageMapper.exit(user);
        //清理当前检查组关联的检查项，操作中间表
        userManageMapper.deleteAssoication(user.getId());
        //重新建立当前检查组和检查项的关联关系,操作t_checkgroup_checkitem表
        Integer userId = user.getId();
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("userId", userId);
                userManageMapper.setUserAndRole(map);
            }
        }
    }

    /**
     * 根据id删除用户信息
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //根据角色id清理中间表关联关系
        userManageMapper.deleteAssoication(id);
        //删除检查组信息
        userManageMapper.deleteById(id);
    }
}
