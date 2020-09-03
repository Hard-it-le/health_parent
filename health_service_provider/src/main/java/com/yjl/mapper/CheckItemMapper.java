package com.yjl.mapper;

import com.github.pagehelper.Page;
import com.yjl.pojo.CheckItem;

import java.util.List;

/**
 * @author yjl
 * @create 2020-07-28-9:24
 * 持久层Dao接口
 **/
public interface CheckItemMapper {




    /**
     * 新增检查项
     *
     * @param checkItem
     */
    public void add(CheckItem checkItem);
    /**
     * 检查项分页查询
     * @param queryString
     * @return
     */
    public Page<CheckItem> selectByCondition(String queryString);
    /**
     * 根据查询检查项id是否关联检查组
     * @param id
     * @return
     */
    public long findCountByCheckItemId(Integer id);
    /**
     * 删除检查项
     * @param id
     */

    public void deleteById(Integer id);
    /**
     * 编辑检查项
     * @param checkItem
     */
    public void edit(CheckItem checkItem);

    /**
     * 查找单个id检查项
     * @param id
     * @return
     */
    public  CheckItem findById(Integer id);

    /**
     * 查找所有检查项的信息
     * @param checkItem
     * @return
     */
    public List<CheckItem> findAll(CheckItem checkItem);
}
