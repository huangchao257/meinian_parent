package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/2/27
 */
@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<TravelItem> page = travelItemDao.findPage(queryString);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void delete(Integer id) {
        //从中间表查找关联数据，如果存在，就不进行删除自由行
        int count = travelItemDao.findTravelItemById(id);
        if (count > 0){ //存在关联数据
            throw new RuntimeException("存在关联数据，无法删除。请先解除关系再进行删除");
        }
        travelItemDao.delete(id);
    }

    @Override
    public TravelItem get(Integer id) {
        TravelItem travelItem = travelItemDao.get(id);
        return travelItem;
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        List<TravelItem> list = travelItemDao.findAll();
        return list;
    }


}
