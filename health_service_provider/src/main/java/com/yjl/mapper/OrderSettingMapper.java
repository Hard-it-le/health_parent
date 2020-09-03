package com.yjl.mapper;

import com.github.pagehelper.Page;
import com.yjl.pojo.Order;
import com.yjl.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-03-21:31
 **/
public interface OrderSettingMapper {

    /**
     * 添加预约
     *
     * @param orderSetting
     */
    public void add(OrderSetting orderSetting);

    /**
     * 更新可预约人数
     *
     * @param orderSetting
     */
    public void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 根据预约日期查询预约设置信息
     *
     * @param orderDate
     * @return
     */
    public long findCountByOrderDate(Date orderDate);

    /**
     * 根据日期范围查询
     *
     * @param map
     * @return
     */
    public List<OrderSetting> getOrderSettingByMonth(Map map);


    /**
     * 根据日期范围查询预约设置信息
     *
     * @param parseString2Date
     * @return
     */
    public OrderSetting findByOrderDate(Date parseString2Date);

    /**
     * //更新已预约人数
     *
     * @param orderSetting
     */
    public void editReservationsByOrderDate(OrderSetting orderSetting);

    /**
     * 查询预约列表
     *
     * @param queryString
     * @return
     */
    public Page<Order> findByCondition(String queryString);

    public Page<Map<String, Object>> selectByCondition(Map<String, Object> map);
}
