package com.jiang.healthquartz.Task;

import com.jiang.constant.RedisConstant;
import com.jiang.utils.QiniuUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Set;

public class ClearImgJob extends QuartzJobBean {

    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Set<Object> set = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if (set != null){
            for (Object picName : set){
                System.out.println(picName);
                QiniuUtils.deleteFileFromQiniu(picName.toString());
                redisTemplate.opsForSet().remove("setmealPicResources",picName);
            }
        }
        System.out.println("--------------");

    }
}
