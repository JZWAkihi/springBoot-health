package com.jiang.service;

import com.jiang.entity.PageResult;
import com.jiang.pojo.Setmeal;

public interface SetmealService {
    void add(Setmeal setmeal,Integer[] checkgroupIds);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
