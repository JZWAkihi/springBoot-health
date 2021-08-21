package com.jiang.health_service_provider.dao;

import com.jiang.pojo.CheckItem;

import org.springframework.stereotype.Repository;
import com.github.pagehelper.Page;

import java.util.List;

@Repository
public interface CheckItemDao {

    void add(CheckItem checkItem);

    CheckItem findOne(Integer id);

    Page<CheckItem> selectByCondition(String queryString);

    void deleteById(Integer id);
    long findCountByCheckItemId(Integer id);

    List<CheckItem> findAll();
    void edit(CheckItem checkItem);
}
