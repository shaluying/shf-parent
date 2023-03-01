package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.Community;
import com.shaluy.entity.Dict;
import com.shaluy.service.CommunityService;
import com.shaluy.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    //分页及带条件查询
    @RequestMapping("")
    public String index(Model model, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放入请求域中
        model.addAttribute("filters",filters);
        //调用communityService中分页及带条件查询的方法
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        //将pageInfo放入请求域中
        model.addAttribute("page",pageInfo);

        //根据编码查出所有的子节点
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //将子节点放入请求域中
        model.addAttribute("areaList",areaList);

        return "community/index";
    }

    //去到新增页面
    @RequestMapping("/create")
    public String toCreatPage(Model model){
        //根据编码获得子节点
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //将子节点放入请求域中
        model.addAttribute("areaList",areaList);

        return "community/create";
    }

    //保存新增页面信息
    @RequestMapping("/save")
    public String save(Community community){
        //保存一个小区
        communityService.insert(community);

        return "common/successPage";
    }

    //去到修改页面
    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable(value = "id") Long id, Model model){
        //根据id获得小区
        Community community = communityService.getById(id);
        //将小区放到请求域中
        model.addAttribute("community",community);

        //根据编码获得子节点
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //将子节点放入请求域中
        model.addAttribute("areaList",areaList);

        return "community/edit";
    }

    @RequestMapping("/update")
    public String update(Community community){
        //更新一个小区
        communityService.update(community);

        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id){
        //根据id删除小区（逻辑删除）
        communityService.delete(id);

        return "redirect:/community";
    }

}
