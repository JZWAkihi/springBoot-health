package com.jiang.service;

import com.jiang.entity.PageResult;
import com.jiang.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupService {

    void add(CheckGroup checkGroup,Integer[] checkItemIds);

    PageResult pageQuery(Integer currentPage,Integer pageSize,String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();

    boolean delete(Integer id);
}
