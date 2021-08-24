package com.jiang.healthmobile.controller;


import com.jiang.constant.MessageConstant;
import com.jiang.entity.Result;
import com.jiang.pojo.Setmeal;
import com.jiang.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Reference
    SetmealService setmealService;

    //查询所有套餐
    @PostMapping("/getSetmeal")
    public Result getAllSetmeal(){
        try{
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    /***
     * 套餐的基本信息  对应的检查组信息  检查组对应的检查项信息
     * @param id
     * @return
     */
    @PostMapping("/findById")
    public Result getAllSetmeal(Integer id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
