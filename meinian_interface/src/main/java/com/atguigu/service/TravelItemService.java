package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;

import java.util.List;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/2/27
 */
public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    TravelItem get(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
