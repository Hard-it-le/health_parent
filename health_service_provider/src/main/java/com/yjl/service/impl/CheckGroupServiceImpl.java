package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.mapper.CheckGroupMapper;
import com.yjl.pojo.CheckGroup;
import com.yjl.pojo.CheckItem;
import com.yjl.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组服务
 */

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupMapper checkGroupDao;

    //新增检查组，同时需要让检查组关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

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
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total, rows);
    }

    /**
     * 根据id查询检查组的信息
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupDao.findById(id);
    }

    /**
     * 根据检查组id查询关联的多个检查项id，查询中间表
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 编辑检查组信息同时关联检查项
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void exit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改编辑检查组
        checkGroupDao.exit(checkGroup);
        //清理当前检查组关联的检查项，操作中间表
        checkGroupDao.deleteAssoication(checkGroup.getId());
        //重新建立当前检查组和检查项的关联关系,操作t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    /**
     * 根据检查组id删除，先删除中间表的关联关系，最后在删除检查组表的数据
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //根据检查组id清理中间表关联关系
        checkGroupDao.deleteAssoication(id);
        //删除检查组信息
        checkGroupDao.deleteById(id);

    }

    /**
     * 查询所有的检查组
     * @param checkGroup
     * @return
     */
    @Override
    public List<CheckGroup> findAll(CheckGroup checkGroup) {
        return checkGroupDao.findAll(checkGroup);
    }


}
