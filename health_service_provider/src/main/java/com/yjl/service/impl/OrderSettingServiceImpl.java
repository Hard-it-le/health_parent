package com.yjl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.mapper.OrderMapper;
import com.yjl.mapper.OrderSettingMapper;

import com.yjl.pojo.CheckItem;
import com.yjl.pojo.Order;
import com.yjl.pojo.OrderSetting;
import com.yjl.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yjl
 * @create 2020-08-03-21:31
 **/
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingDao;


    /**
     * 批量导入预约设置数据
     *
     * @param list
     */
    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //判断当前日期是否已经进行了预约设置,检查此数据（日期）是否存在
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0) {
                    //已经进行预约设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    //没有进行预约设置,执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * 参数格式为：2019-03
     *
     * @param date
     * @return
     */
    //根据月份查询对应的预约设置数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {//格式：yyyy-MM
        String begin = date + "-1";//2019-6-1
        String end = date + "-31";//2019-6-31
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //调用DAO，根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> m = new HashMap<>();
                //获取日期数字（几号）
                m.put("date", orderSetting.getOrderDate().getDate());
                m.put("number", orderSetting.getNumber());
                m.put("reservations", orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    /**
     * //根据日期设置对应的预约设置数据
     *
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if (count > 0) {
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//查询条件
        //完成分页查询，基于mybatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> map = new HashMap<>();
       /* //说明有条件
        if (queryString != null && queryString.length() > 0) {
            for (int i = 0; i < queryString.length(); i++) {
                if (!Character.isDigit(queryString.charAt(i))) {
                    //说明不是数字
                    map.put("name", queryString);
                } else {
                    //说明是数字
                    map.put("phoneNumber", queryString);
                }
            }
        }*/
        //我们只需要设置查询条件，这个sql语句就需要自己写了，返回的查询对象赋值给page对象
        Page<Map<String, Object>> page = orderSettingDao.selectByCondition(map);
        long total = page.getTotal();
        List<Map<String, Object>> rows = page.getResult();
        return new PageResult(total, rows);
    }


}
