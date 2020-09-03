package com.yjl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yjl.constant.MessageConstant;
import com.yjl.entity.PageResult;
import com.yjl.entity.QueryPageBean;
import com.yjl.entity.Result;
import com.yjl.pojo.OrderSetting;
import com.yjl.service.OrderSettingService;
import com.yjl.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yjl
 * @create 2020-08-03-20:15
 * 预约设置控制台
 **/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    /**
     * 分页查询预约
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return orderSettingService.pageQuery(queryPageBean);
    }


    /**
     * 文件上传，实现预约设置数据批量导入
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //使用poi解析表格数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : list) {
                String orderDate = strings[0];
                String number = strings[1];
                Date orderDate1 = new Date((orderDate));
                Integer number1 = Integer.parseInt(number);
                OrderSetting orderSetting = new OrderSetting(orderDate1, number1);
                data.add(orderSetting);
            }
            //通过dubbo远程调用服务实习数据批量导入数据库
            orderSettingService.add(data);
        } catch (IOException e) {
            e.printStackTrace();
            //解析文件失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return null;
    }

    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * //参数格式为：2019-03
     *
     * @param date
     * @return
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {//参数格式为：2019-03
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            //获取预约设置数据成功
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            //获取预约设置数据失败
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据指定日期修改指定日期的预约人数
     *
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            //预约设置成功
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //预约设置失败
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
