package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.Dict;
import com.shaluy.result.Result;
import com.shaluy.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController{

    @Reference
    private DictService dictService;

    //去展示数据字典的页面
    @RequestMapping("")
    public String index(){
        return "dict/index";
    }

    //获取字典数据中的数据
    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        //调用DictService中查询数据字典的数据的方法
        List<Map<String, Object>> zNodes = dictService.findZnodes(id);

        return Result.ok(zNodes);
    }

    @RequestMapping("/findListByParentId/{areaId}")
    @ResponseBody
    public Result findListByParentId(@PathVariable(value = "areaId") Long id){
        //根据id获得所有的子节点
        List<Dict> plateList = dictService.findListByParentId(id);

        return Result.ok(plateList);
    }
}
