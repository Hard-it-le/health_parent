package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.mapper.RoleManageMapper;
import com.yjl.pojo.Role;
import com.yjl.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-26-19:24
 **/
@Service(interfaceClass = RoleManageService.class)
@Transactional
public class RoleManageServiceImpl implements RoleManageService {
    @Autowired
    private RoleManageMapper roleManageMapper;

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
        //我们只需要设置查询条件，这个sql语句就需要自己写了，返回的查询对象赋值给page对象
        Page<Role> page = roleManageMapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<Role> rows = page.getResult();
        return new PageResult(total, rows);
    }

    /**
     * 根据角色id查询角色信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findPermissionIdsByRoleIds(Integer id) {
        return roleManageMapper.findPermissionIdsByRoleIds(id);
    }

    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     */
    @Override
    public void add(Role role, Integer[] permissionIds) {
        //新增角色，操作t_role表
        roleManageMapper.add(role);
        //设置角色和权限的多对多的关联关系，操作t_role_permission表
        Integer roleId = role.getId();
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("permissionId", permissionId);
                roleManageMapper.setRoleAndPermission(map);
            }
        }
    }

    /**
     * 编辑角色权限
     *
     * @param role
     * @param permissionIds
     */
    @Override
    //提交表单
    public void edit(Role role, Integer[] permissionIds) {
        //修改编辑检查组
        roleManageMapper.exit(role);
        //先删除中间表关系
        roleManageMapper.deleteAssoication(role.getId());
        //重新建立当前检查组和检查项的关联关系,操作t_role_permission表
        Integer roleId = role.getId();
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("permissionId", permissionId);
                roleManageMapper.setRoleAndPermission(map);
            }
        }
    }

    /**
     * 查询所有的角色信息
     * @param role
     * @return
     */
    @Override
    public List<Role> findAll(Role role) {
        return roleManageMapper.findAll(role);
    }


    /**
     * 根据id删除角色
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //根据角色id清理中间表关联关系
        roleManageMapper.deleteAssoication(id);
        //删除检查组信息
        roleManageMapper.deleteById(id);
    }

    /**
     * 根据id查询到角色信息
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        return roleManageMapper.findById(id);
    }


}
