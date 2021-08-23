package com.jiang.health_service_provider.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jiang.constant.RedisConstant;
import com.jiang.entity.PageResult;
import com.jiang.entity.Result;
import com.jiang.health_service_provider.dao.SetmealDao;
import com.jiang.pojo.Setmeal;
import com.jiang.service.SetmealService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);

        Integer id = setmeal.getId();
        String fileName = setmeal.getImg();
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
        setSetmealAndCheckGroup(id,checkgroupIds);

    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }


    //设置套餐和检查组多对多关系  操作t_setmeal_checkgroup
    public void setSetmealAndCheckGroup(Integer setmealID,Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap();
                map.put("setmealId",setmealID);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }


}
