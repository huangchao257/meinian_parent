package com.atguigu.service;

import com.atguigu.pojo.User;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/6
 */
public interface UserService {
    User findUserByUsername(String username);
}
