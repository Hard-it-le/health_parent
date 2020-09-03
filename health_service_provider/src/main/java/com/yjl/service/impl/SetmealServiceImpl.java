package com.yjl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjl.constant.RedisConstant;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.mapper.SetmealMapper;
import com.yjl.pojo.Setmeal;
import com.yjl.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.config.annotation.Service;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-07-30-15:45
 * 体检套餐服务实现类
 **/
@Service(interfaceClass = SetmealService.class)
@Transactional//事务接口
public class SetmealServiceImpl implements  SetmealService{
    @Autowired
    private SetmealMapper setmealDao;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 新增套餐信息，同时需要关联检查组
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
        //将图片名称保存到Redis集合中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    /**
     * 分页查询套餐
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //获得当前页码 总页码
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        long total = page.getTotal();
        List<Setmeal> rows = page.getResult();
        return new PageResult(total, rows);
    }

    /**
     * 根据套餐id删除，先删除中间表的关联关系，最后在删除套餐表的数据
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //根据套餐id清理中间表关联关系
        setmealDao.deleteAssoication(id);
        //删除套餐信息
        setmealDao.deleteById(id);
    }

    /**
     * 根据id查询套餐数据
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return  setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询关联的多个检查项id，查询中间表
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdssAndSetmealId(Integer id) {
        return setmealDao.findCheckGroupIdssAndSetmealId(id);
    }

    /**
     * 编辑套餐信息
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void exit(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改编辑检查组
        setmealDao.exit(setmeal);
        //清理当前检查组关联的检查项，操作中间表
        setmealDao.deleteAssoication(setmeal.getId());
        //重新建立当前检查组和检查项的关联关系,操作t_checkgroup_checkitem表
        Integer setmealId = setmeal.getId();
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkgroupId", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

    /**
     * 查找所有的套餐
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    /**
     * 查询套餐预约占比数据
     * @return
     */
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    /**
     * 移动端根据套餐id查询套餐的所有信息和检查组和检查项信息
     * @param id
     * @return
     */
    /*@Override
    public Setmeal findById1(int id) {
        return setmealDao.findById1();
    }*/
}
