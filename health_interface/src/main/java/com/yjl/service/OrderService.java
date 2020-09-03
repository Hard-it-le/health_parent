package com.yjl.service;

import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;

import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-06-19:06
 * 体检预约服务接口
 **/
public interface OrderService {

    public Result order(Map map) throws Exception;

    public Map findById(Integer id) throws Exception;

}
