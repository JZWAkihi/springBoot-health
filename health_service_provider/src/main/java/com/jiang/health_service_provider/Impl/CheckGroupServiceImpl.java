package com.jiang.health_service_provider.Impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jiang.entity.PageResult;
import com.jiang.health_service_provider.dao.CheckGroupDao;
import com.jiang.pojo.CheckGroup;
import com.jiang.service.CheckGroupService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;



    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //新增检查组  操作 t_checkGroup 表
        checkGroupDao.add(checkGroup);

        //获取到刚刚插入的检查组中的id
        Integer checkGroupId = checkGroup.getId();

        //设置检查组和检查项的多对多关联
        //操作t_checkGroup_checkItem
        if (checkItemIds != null && checkItemIds.length > 0){
           for (Integer checkItemId : checkItemIds){
               HashMap<String,Integer> map = new HashMap();

               map.put("checkgroup_id",checkGroupId);
               map.put("checkitem_id",checkItemId);

               //调用dao传递
               checkGroupDao.setCheckGroupAndCheckItem(map);
           }
        }


    }


    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);

        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.deleteAssociation(checkGroup.getId());
        System.out.println("checkGroupDao.deleteAssociation");
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        System.out.println("setCheckGroupAndCheckItem()");
        checkGroupDao.edit(checkGroup);
        System.out.println("checkGroupDao.edit");
    }

    /**
     * 向中间表(t_checkgroup_checkitem)插入数据
     *
     */
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if(checkitemIds != null && checkitemIds.length > 0){
            for(Integer checkItemId : checkitemIds){
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkItemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    @Override
    public boolean delete(Integer id) {
        System.out.println("CheckGroupServiceImpl.delete");
        System.out.println(findCheckItemIdsByCheckGroupId(id));
        if(findCheckItemIdsByCheckGroupId(id).size() != 0){
            return false;
        }
        System.out.println("findCheckItemIdsByCheckGroupId");
        checkGroupDao.delete(id);
        return true;
    }

}
