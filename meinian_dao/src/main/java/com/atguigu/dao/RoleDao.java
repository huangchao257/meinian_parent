package com.atguigu.dao;

import com.atguigu.pojo.Role;
import org.apache.zookeeper.data.Id;

import java.util.Set;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/6
 */
public interface RoleDao {
    Set<Role> findRolesByUserId(Integer id);
}
