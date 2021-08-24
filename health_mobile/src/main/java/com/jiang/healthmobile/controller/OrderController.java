package com.jiang.healthmobile.controller;


import com.jiang.constant.MessageConstant;
import com.jiang.constant.RedisMessageConstant;
import com.jiang.entity.Result;
import com.jiang.pojo.Order;
import com.jiang.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map map){
        String telephone = (String) map.get("telephone");

        //
        Set members = redisTemplate.boundSetOps(telephone + RedisMessageConstant.SENDTYPE_ORDER).members();

        String codeInRedis = null;
        for (Object member : members) {
            codeInRedis = (String) member;
        }
        //校验用户输入的验证码是否正确
        String validateCode =  (String)map.get("validateCode");
        System.out.println("validateCode " + validateCode);
        System.out.println("codeInRedis" + codeInRedis);
        if(codeInRedis == null || !codeInRedis.equals(validateCode)){
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }


        //通过dubbo调用服务实现预约逻辑
        Result result = null;
        try{
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }

        if(result.isFlag()){
//            String orderDate = (String) map.get("orderDate");
            //给用户发送短信通知
//            try {
//                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
//            } catch (ClientException e) {
//                e.printStackTrace();
//            }
            System.out.println("预约成功 ===  给用户发送短信通知");
        }
        System.out.println(result.getData());
        return result;
    }


    @PostMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }



}
