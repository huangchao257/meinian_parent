package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

//@Component
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    //清理垃圾图片任务
    public void clearImg(){
        Set<String> imgNames = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                                        RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String imgName : imgNames) {
            System.out.println("imgName = " + imgName);
            QiniuUtils.deleteFileFromQiniu(imgName);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
        }

    }
}
