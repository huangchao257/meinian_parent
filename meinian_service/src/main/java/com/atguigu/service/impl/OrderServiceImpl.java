package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/5
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    MemberDao memberDao;

    /**
     * 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
     * 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
     * 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
     * 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
     * 5、预约成功，更新当日的已预约人数
     *
     * @param map
     * @return
     */
    @Override
    public Result saveOrder(Map map) throws Exception {
        String setmealId = (String) map.get("setmealId");
        String name = (String) map.get("name");
        String telephone = (String) map.get("telephone");
        String sex = (String) map.get("sex");
        String idCard = (String) map.get("idCard");
        String orderDate = (String) map.get("orderDate");
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
            int reservations = orderSetting.getReservations();
            int number = orderSetting.getNumber();
            if (number <= reservations) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }
        //3、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        Member member = memberDao.findMemberByTelephone(telephone);
        if (member == null) {
            member = new Member();
            member.setName(name);
            member.setSex(sex);
            member.setIdCard(idCard);
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberDao.save(member);//主键回填
        }
        //4、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("memberId", member.getId());
        param.put("orderDate", date);
        param.put("setmealId", Integer.parseInt(setmealId));
        Order order = orderDao.findOrderByMap(param);
        if (order != null) {
            return new Result(false, MessageConstant.HAS_ORDERED);
        } else {
            order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            order.setSetmealId(Integer.parseInt(setmealId));
            orderDao.saveOrder(order); //主键回填
        }
        //5、预约成功，更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById(Integer orderId) throws Exception {
        Map map = orderDao.findById(orderId);
        Date orderDate = (Date) map.get("orderDate");
        map.put("orderDate", DateUtils.parseDate2String(orderDate));
        return map;
    }
}
