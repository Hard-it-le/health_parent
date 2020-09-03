package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-07-30-15:45
 * 体检套餐服务接口
 **/
public interface SetmealService {


    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public Setmeal findById(Integer id);

    public List<Integer> findCheckGroupIdssAndSetmealId(Integer id);

    public void exit(Setmeal setmeal, Integer[] checkgroupIds);

    public List<Setmeal> findAll();

    public List<Map<String, Object>> findSetmealCount();
}
