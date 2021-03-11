package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/5
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            //1.生成校验码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //2.发送校验码
            SMSUtils.sendShortMessage(telephone,code+"");
            //3.将校验码保存到redis中
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 10 * 60,code+"");
            //4.返回结果
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            //1.发送校验码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            SMSUtils.sendShortMessage(telephone,code+"");
            //2.保存到redis中10分钟有效
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 10 * 60, code + "");

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
