package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/6
 */
public interface UserDao {
    User findUserByUsername(String username);
}
