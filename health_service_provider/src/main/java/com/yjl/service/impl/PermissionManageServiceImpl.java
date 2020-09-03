package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.mapper.PermissionManageMapper;
import com.yjl.pojo.Permission;
import com.yjl.pojo.Role;
import com.yjl.service.PermissionManageService;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionManageService.class)
@Transactional
public class PermissionManageServiceImpl implements PermissionManageService {

    @Autowired
    private PermissionManageMapper permissionManageMapper;

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
        Page<Role> page = permissionManageMapper.selectByCondition(queryString);
        long total = page.getTotal();
        List<Role> rows = page.getResult();
        return new PageResult(total, rows);
    }

    /**
     * 添加权限
     *
     * @param permission
     */
    @Override
    public void add(Permission permission) {
        permissionManageMapper.add(permission);
    }

    /**
     * 查找所有权限类型
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return permissionManageMapper.findAll();
    }

    /**
     * 根据id删除权限
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //判断当前权限是否关联到角色
        long count = permissionManageMapper.findCountByRoleId(id);
        if (count > 0) {
            //当前检查项已经被关联到检查组，不允许删除
            new RuntimeException("当前检查项被引用，不能删除");
        }
        permissionManageMapper.deleteById(id);
    }

    /**
     * 根据id查询单个权限信息
     *
     * @param id
     * @return
     */
    @Override
    public Permission findById(Integer id) {
        return permissionManageMapper.findById(id);
    }

    /**
     * 编辑权限信息
     * @param permission
     */
    @Override
    public void edit(Permission permission) {
        permissionManageMapper.edit(permission);
    }
}