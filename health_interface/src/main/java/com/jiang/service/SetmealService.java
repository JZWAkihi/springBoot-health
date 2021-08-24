package com.jiang.service;

import com.jiang.entity.PageResult;
import com.jiang.pojo.Setmeal;

import java.util.List;
import java.util.Set;

public interface SetmealService {
    void add(Setmeal setmeal,Integer[] checkgroupIds);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    List<Setmeal> findAll();
    Setmeal findById(Integer id);
}
