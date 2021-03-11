package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/5
 */
@RestController
@RequestMapping("/Order")
public class OrderController {

    @Reference
    OrderService orderService;

    @Autowired
    JedisPool jedisPool;


    @RequestMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map map) {
        try {
            //1.校验 验证码
            String code = (String) map.get("validateCode");
            String telephone = (String) map.get("telephone");
            String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            if (redisCode == null) {
                return new Result(false, MessageConstant.VALIDATECODE_NOTNULL_ERROR);
            } else {
                if (redisCode.equals(code)) {
                    Result result = orderService.saveOrder(map);
                    return result;
                } else {
                    return new Result(false, MessageConstant.VALIDATECODE_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer orderId) {
        try {
            Map map = orderService.findById(orderId);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
