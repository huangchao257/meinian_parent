package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author huangchao
 * @date 2021/3/8
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    AddressService addressService;

    @RequestMapping("/findAllMaps")
    public Map findAllMaps() {
        try {
            Map map = new HashMap();
            List<Address> addresses = addressService.findAllMaps();
            //定义分店名称集合
            List<Map> nameMaps = new ArrayList<>();
            //定义分店坐标集合
            List<Map> gridnMaps = new ArrayList<>();

            for (Address address : addresses) {
                //获取经纬度
                Map gridnMap=new HashMap();
                gridnMap.put("lng", address.getLng());
                gridnMap.put("lat", address.getLat());
                gridnMaps.add(gridnMap);
                //获取地址的名字
                Map nameMap = new HashMap();
                nameMap.put("addressName", address.getAddressName());
                nameMaps.add(nameMap);
            }
            map.put("nameMaps", nameMaps);
            map.put("gridnMaps", gridnMaps);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
