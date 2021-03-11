package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/3
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void bathAdd(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            // 判断当前的日期之前是否已经被设置过预约日期，使用当前时间作为条件查询数量
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if (count > 0){
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        //1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = date + "-01";
        String dateEnd = date + "-31";
        // 2.查询当前月份的预约设置
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(dateBegin, dateEnd);
        //3.将List<OrderSetting>，组织成List<Map>
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map map = new HashMap();
            map.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            map.put("number", orderSetting.getNumber());//可预约人数
            map.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(map);
        }
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
