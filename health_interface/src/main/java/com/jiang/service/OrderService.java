package com.jiang.service;

import com.jiang.entity.Result;

import java.util.Map;

public interface OrderService{
    //体检预约
    public Result order(Map map) throws Exception;

    Map findById(Integer id);
}
