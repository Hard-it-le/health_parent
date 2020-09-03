package com.yjl.service;

import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-03-21:23
 **/
public interface OrderSettingService {
    public void add(List<OrderSetting> list);

    //参数格式为：2019-03
    public List<Map> getOrderSettingByMonth(String date);

    public void editNumberByDate(OrderSetting orderSetting);


    public PageResult pageQuery(QueryPageBean queryPageBean);
}
