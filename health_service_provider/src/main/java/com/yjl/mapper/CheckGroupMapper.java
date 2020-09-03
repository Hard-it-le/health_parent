package com.yjl.mapper;

import com.github.pagehelper.Page;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.pojo.CheckGroup;
import com.yjl.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {
    public void add(CheckGroup checkGroup);

    public void setCheckGroupAndCheckItem(Map map);

    public Page<CheckGroup> findByCondition(String queryString);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void exit(CheckGroup checkGroup);

    public void deleteAssoication(Integer id);

    public void deleteById(Integer id);

    public   List<CheckGroup> findAll(CheckGroup checkGroup);


}
