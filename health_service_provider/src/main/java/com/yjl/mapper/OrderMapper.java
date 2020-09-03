package com.yjl.mapper;

import com.yjl.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-06-19:06
 **/
public interface OrderMapper {
    /**
     * 动态条件查询
     *
     * @param order
     * @return
     */
    public List<Order> findByCondition(Order order);

    /**
     * 新增预约信息
     *
     * @param order
     */
    public void add(Order order);

    /**
     * 根据预约ID查询预约相关信息（体检人姓名、预约日期、套餐名称、预约类型）
     *
     * @param id
     * @return
     */
    public Map findById(Integer id);


    /**
     * 每日预约订单
     *
     * @param today
     * @return
     */
    public Integer findOrderCountByDate(String today);

    /**
     * 每周预约订单
     *
     * @param thisWeekMonday
     * @return
     */
    public Integer findOrderCountAfterDate(String thisWeekMonday);

    /**
     * 每日到诊人数
     */
    public Integer findVisitsCountByDate(String today);

    /**
     * 每周到诊人数
     *
     * @param thisWeekMonday
     * @return
     */
    public Integer findVisitsCountAfterDate(String thisWeekMonday);

    /**
     * 热门套餐
     *
     * @return
     */
    public List<Map> findHotSetmeal();

    /**
     * 查询所有套餐
     *
     * @param queryString
     * @return
     */
    public List<Order> findAll(String queryString);


}
