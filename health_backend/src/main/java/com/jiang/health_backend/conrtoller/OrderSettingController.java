package com.jiang.health_backend.conrtoller;


import com.jiang.constant.MessageConstant;
import com.jiang.entity.Result;
import com.jiang.pojo.OrderSetting;
import com.jiang.service.OrderSettingService;
import com.jiang.utils.POIUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile multipartFile){
        try{
            List<String[]> list = POIUtils.readExcel(multipartFile);

            //使用poi来解析表格数据
            List<OrderSetting> data = new ArrayList();
            for(String[] string : list){
                String orderDate = string[0];

                String number = string[1];

                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.parseInt(number));
                data.add(orderSetting);
                //通过dubbo来远程调用服务实现数据批量导入到数据库。
                orderSettingService.add(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }


    @PostMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){

        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }



}
