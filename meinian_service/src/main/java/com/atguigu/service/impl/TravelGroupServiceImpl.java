package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/1
 */
@Service
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        //添加跟团游
        travelGroupDao.add(travelGroup);//主键回填：保存前没有id属性值，保存后mybatis框架会将数据分配主键赋值给id属性
        Integer travelGroupId = travelGroup.getId();

        //添加中间表关系数据
        setTravelGroupAndTravelItem(travelGroupId, travelItemIds);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<TravelGroup> page = travelGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        List<Integer> list = travelGroupDao.getTravelItemIdAndTravelGroupId(id);
        if (list != null){
            travelGroupDao.delete(id);
        }else {
            throw new RuntimeException("存在关联数据，无法删除。请先解除关系再进行删除");
        }
    }

    @Override
    public TravelGroup get(Integer id) {
        return travelGroupDao.get(id);
    }

    @Override
    public List<Integer> getTravelItemIdAndTravelGroupId(Integer id) {
        return travelGroupDao.getTravelItemIdAndTravelGroupId(id);
    }

    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.edit(travelGroup);
        //先删除中间表关系数据
        travelGroupDao.deleteByTravelGroupId(travelGroup.getId());
        //再增加新的一批关系数据
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        if(travelItemIds!=null && travelItemIds.length>0){
            for (Integer travelItemId : travelItemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("travelGroupId", travelGroupId);
                map.put("travelItemId", travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }

    }
}
