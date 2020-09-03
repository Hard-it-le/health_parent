package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.CheckItem;

import java.util.List;

/**
 * @author yjl
 * @create 2020-07-28-9:13
 * 服务层接口
 **/
public interface CheckItemService {


    public void add(CheckItem checkItem);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll(CheckItem checkItem);
}
