package com.jiang.health_service_provider.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jiang.entity.PageResult;
import com.jiang.entity.QueryPageBean;
import com.jiang.health_service_provider.dao.CheckItemDao;
import com.jiang.pojo.CheckItem;
import com.jiang.service.CheckItemService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        System.out.println("CheckItemServiceImpl.add");
        checkItemDao.add(checkItem);
    }

    @Override
    public CheckItem findOne(Integer id) {
        System.out.println("CheckItemServiceImpl.findOne");

        return checkItemDao.findOne(id);
    }


    /***
     * BUG  无法进行分页处理  加入@org.jetbrains.annotations.NotNull 之后可以
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(@org.jetbrains.annotations.NotNull QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//查询条件

        System.out.println(currentPage + "  " + pageSize);

        //完成分页查询，基于mybatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage, pageSize);

        //select * from t_checkitem limit 0,10
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);


        long total = page.getTotal();
        System.out.println(page.toString());
        System.out.println(total);
        List<CheckItem> rows = page.getResult();

        return new PageResult(total,rows);
    }

    @Override
    public void deleteById(Integer id){
        long count = checkItemDao.findCountByCheckItemId(id);

        if(count > 0){
            //当前检查项已经被关联到检查组，不允许删除
            new RuntimeException("当前检查项已经被关联到检查组，不允许删除");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }


}
