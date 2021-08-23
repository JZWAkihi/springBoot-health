package com.jiang.health_backend.conrtoller;

import com.jiang.constant.MessageConstant;
import com.jiang.constant.RedisConstant;
import com.jiang.entity.PageResult;
import com.jiang.entity.QueryPageBean;
import com.jiang.entity.Result;
import com.jiang.pojo.Setmeal;
import com.jiang.service.SetmealService;
import com.jiang.utils.QiniuUtils;
import com.jiang.utils.RedisUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;

/**
 * 体检套餐
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;


    @Reference
    SetmealService setmealService;
    //文件上传
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){

        String originalFilename = imgFile.getOriginalFilename();


        int index = originalFilename.lastIndexOf('.');
        String extention = originalFilename.substring(index-1);

        String fileName = UUID.randomUUID().toString() + extention;

        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkGroupIds){
        try{
            setmealService.add(setmeal,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());

        return pageResult;
    }

}
