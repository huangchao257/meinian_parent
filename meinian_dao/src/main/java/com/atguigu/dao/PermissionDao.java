package com.atguigu.dao;

import com.atguigu.pojo.Permission;

import java.util.Set;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/6
 */
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer id);
}
