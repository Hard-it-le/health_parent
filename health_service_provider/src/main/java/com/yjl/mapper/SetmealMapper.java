package com.yjl.mapper;

import com.github.pagehelper.Page;

import com.yjl.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-07-30-15:45
 **/
public interface SetmealMapper {

    /**
     * 添加套餐
     *
     * @param setmeal
     */
    public void add(Setmeal setmeal);

    /**
     * 套餐和检查组关联
     *
     * @param map
     */
    public void setSetmealAndCheckGroup(Map map);

    /**
     * 套餐分页查询
     *
     * @param queryString
     * @return
     */
    public Page<Setmeal> findByCondition(String queryString);

    /**
     * 删除中间表的数据
     *
     * @param id
     */
    public void deleteAssoication(Integer id);

    /**
     * 根据id删除套餐
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    public Setmeal findById(Integer id);

    /**
     * 根据套餐id查询关联的多个检查项id，查询中间表
     *
     * @param id
     * @return
     */
    public List<Integer> findCheckGroupIdssAndSetmealId(Integer id);

    /**
     * 编辑套餐id
     *
     * @param setmeal
     */
    public void exit(Setmeal setmeal);

    /**
     * 查询所有的套餐
     *
     * @return
     */
    public List<Setmeal> findAll();

    /**
     * 查询套餐预约占比数据
     *
     * @return
     */
    public List<Map<String, Object>> findSetmealCount();

    /**
     * 移动端查询套餐id
     * @return
     */
    /*public Setmeal findById1();*/


}
