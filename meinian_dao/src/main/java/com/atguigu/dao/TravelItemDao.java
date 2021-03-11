package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.apache.zookeeper.data.Id;

import java.util.List;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/2/27
 */
public interface TravelItemDao {

    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    void delete(Integer id);

    int findTravelItemById(Integer id);

    TravelItem get(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();

    List<TravelItem> findTravelItemListById(Integer id);

}
