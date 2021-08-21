package com.jiang.health_backend.conrtoller;

import com.jiang.constant.MessageConstant;
import com.jiang.entity.PageResult;
import com.jiang.entity.QueryPageBean;
import com.jiang.entity.Result;
import com.jiang.pojo.CheckItem;
import com.jiang.service.CheckItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;

    @GetMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem = null;
        try{
            checkItem = checkItemService.findOne(id);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            System.out.println("CheckItemController.add");
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        System.out.println(queryPageBean.getCurrentPage());
        System.out.println(queryPageBean.getPageSize());
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    @GetMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    @GetMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> checkItemList = checkItemService.findAll();

            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();

            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

}
