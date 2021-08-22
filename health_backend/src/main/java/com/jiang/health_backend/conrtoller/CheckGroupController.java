package com.jiang.health_backend.conrtoller;


import com.jiang.constant.MessageConstant;
import com.jiang.entity.PageResult;
import com.jiang.entity.QueryPageBean;
import com.jiang.entity.Result;
import com.jiang.pojo.CheckGroup;
import com.jiang.service.CheckGroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {


    @Reference
    CheckGroupService checkGroupService;

    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkItemIds){
        try{
            checkGroupService.add(checkGroup,checkItemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );

        return pageResult;
    }


    @GetMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_SUCCESS);
        }
    }

    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try{
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            System.out.println("CheckGroupController.edit");
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        if(checkGroupList != null && checkGroupList.size() > 0){
            Result result = new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @GetMapping("/delete")
    public Result delete(Integer id){
        boolean b = checkGroupService.delete(id);
        System.out.println(b);
        if(!b) {
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

}
