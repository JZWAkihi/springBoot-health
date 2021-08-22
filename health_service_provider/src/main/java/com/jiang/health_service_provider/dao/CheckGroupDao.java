package com.jiang.health_service_provider.dao;

import com.github.pagehelper.Page;
import com.jiang.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(Map map);
    Page<CheckGroup> selectByCondition(String queryString);
    CheckGroup findById(Integer id);
    void deleteAssociation(Integer id);
    void edit(CheckGroup checkGroup);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    List<CheckGroup> findAll();
    void delete(Integer id);
}
