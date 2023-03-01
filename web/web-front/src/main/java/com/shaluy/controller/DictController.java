package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.Dict;
import com.shaluy.result.Result;
import com.shaluy.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    @RequestMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode){
        //根据编码获取所有的的子节点
        List<Dict> dictList = dictService.findListByDictCode(dictCode);

        Result ok = Result.ok(dictList);
        System.out.println("ok = " + ok);

        return Result.ok(dictList);
    }

    @RequestMapping("/findListByParentId/{id}")
    @ResponseBody
    public Result findListByParentId(@PathVariable("id") Long id){
        //根据id获取所有的子节点
        List<Dict> listByParentId = dictService.findListByParentId(id);

        return Result.ok(listByParentId);
    }
}
