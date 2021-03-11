package com.atguigu.dao;

import com.atguigu.pojo.Address;

import java.util.List;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/8
 */
public interface AddressDao {
    List<Address> findAllMaps();
}
