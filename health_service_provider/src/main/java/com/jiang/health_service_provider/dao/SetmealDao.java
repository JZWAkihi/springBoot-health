package com.jiang.health_service_provider.dao;

import com.github.pagehelper.Page;
import com.jiang.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    void add(Setmeal setmeal);
    void setSetmealAndCheckGroup(Map map);
    Page<Setmeal> selectByCondition(String queryString);
    List<Setmeal> findAll();
    Setmeal findById(Integer id);
}
