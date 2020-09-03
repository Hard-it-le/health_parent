package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.CheckGroup;
import com.yjl.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void exit(CheckGroup checkGroup, Integer[] checkitemIds);


    public void deleteById(Integer id);

    public List<CheckGroup> findAll(CheckGroup checkGroup);


}
