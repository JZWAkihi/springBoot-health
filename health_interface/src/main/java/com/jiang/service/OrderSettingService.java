package com.jiang.service;

import com.jiang.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void add(List<OrderSetting> list);
    List<Map> getOrderSettingByMonth(String date) throws Exception; //参数格式为：2019-03
    void editNumberByDate(OrderSetting orderSetting);
}
