package com.jiang.health_service_provider.dao;

import com.jiang.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);
    long findCountByOrderDate(String orderDate);
    //根据日期范围查询预约设置信息
    List<OrderSetting> getOrderSettingByMonth(Map date);
    void editNumberByOrderDate(OrderSetting orderSetting);
    OrderSetting findByOrderDate(String orderDate);
    //更新已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
