package com.jiang.healthmobile.controller;


import com.jiang.constant.MessageConstant;
import com.jiang.constant.RedisMessageConstant;
import com.jiang.entity.Result;
import com.jiang.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/validatecode")
public class ValidateCodeController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/send4Order")
    public Result send4order(String telephone){
        //生成4位数字验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //发送短信
        try{
            System.out.println(code);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //将生成的验证码发送到缓存redis中
        Set<Object> members = redisTemplate.boundSetOps(telephone + RedisMessageConstant.SENDTYPE_ORDER).members();
        if(members != null && members.size() > 0){
            redisTemplate.opsForSet().pop(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        }
        redisTemplate.opsForSet().add(telephone + RedisMessageConstant.SENDTYPE_ORDER,code.toString());
        redisTemplate.expire(telephone + RedisMessageConstant.SENDTYPE_ORDER,5 * 60, TimeUnit.SECONDS);


        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }




}
