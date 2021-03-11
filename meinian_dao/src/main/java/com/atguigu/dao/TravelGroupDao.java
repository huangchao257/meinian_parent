package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/1
 */
public interface TravelGroupDao {
    void add(TravelGroup travelGroup);

    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup get(Integer id);

    List<Integer> getTravelItemIdAndTravelGroupId(Integer id);

    void delete(Integer id);

    void edit(TravelGroup travelGroup);

    void deleteByTravelGroupId(Integer id);

    List<TravelGroup> findAll();

    //帮忙方法
    List<TravelGroup> findTravelGroupListById(Integer id);
}
