package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/1
 */
public interface TravelGroupService {
    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    TravelGroup get(Integer id);

    List<Integer> getTravelItemIdAndTravelGroupId(Integer id);

    void edit(TravelGroup travelGroup, Integer[] travelItemIds);

    List<TravelGroup> findAll();
}
