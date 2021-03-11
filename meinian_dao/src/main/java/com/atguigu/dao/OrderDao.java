package com.atguigu.dao;

import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/5
 */
public interface OrderDao {
    Order findOrderByMap(Map<String, Object> param);

    void saveOrder(Order order);

    void editReservationsByOrderDate(OrderSetting orderSetting);

    Map findById(Integer orderId);

    int getTodayOrderNumber(String date);

    int getTodayVisitsNumber(String date);

    int getThisWeekAndMonthOrderNumber(Map<String, Object> map);

    int getThisWeekAndMonthVisitsNumber(Map<String, Object> map);

    List<Map<String,Object>> findHotSetmeal();

}
