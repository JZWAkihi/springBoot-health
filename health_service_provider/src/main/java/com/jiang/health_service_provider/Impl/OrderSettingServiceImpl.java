package com.jiang.health_service_provider.Impl;

import com.jiang.health_service_provider.dao.OrderSettingDao;
import com.jiang.pojo.OrderSetting;
import com.jiang.service.OrderSettingService;
import com.jiang.utils.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());

                if(countByOrderDate > 0){
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) throws Exception {
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";

        HashMap map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);


        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);

        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting: list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date", DateUtils.parseString2Date(orderSetting.getOrderDate()));
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            data.add(orderSettingMap);
        }

        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());

        if(count > 0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            orderSettingDao.add(orderSetting);
        }


    }

}
