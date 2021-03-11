package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/5
 */
public interface OrderService {
    Result saveOrder(Map map) throws Exception;

    Map findById(Integer orderId) throws Exception;
}
