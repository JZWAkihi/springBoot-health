package com.jiang.service;


import com.github.pagehelper.Page;
import com.jiang.entity.PageResult;
import com.jiang.entity.QueryPageBean;
import com.jiang.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    void add(CheckItem checkItem);
    CheckItem findOne(Integer id);
    PageResult pageQuery(QueryPageBean queryPageBean);
    void deleteById(Integer id);
    List<CheckItem> findAll();
    void edit(CheckItem checkItem);
}
