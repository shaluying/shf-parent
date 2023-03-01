package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.Admin;
import com.shaluy.entity.HouseBroker;
import com.shaluy.service.AdminService;
import com.shaluy.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    //去添加页面
    @RequestMapping("/create")
    public String toAddPage(@RequestParam("houseId") Long houseId, Model model){
        //将houseId放入到请求域中
        model.addAttribute("houseId",houseId);

        //获取所有的用户
        List<Admin> adminList = adminService.findAll();
        //将所有用户放入到请求域中
        model.addAttribute("adminList",adminList);

        return "houseBroker/create";
    }

    //保存一个经纪人
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        //根据经纪人id(用户id)获得用户信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        //保存一个经纪人
        houseBrokerService.insert(houseBroker);

        return "common/successPage";
    }

    //去到编辑页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id, Model model){
        //根据id获取经纪人
        HouseBroker houseBroker = houseBrokerService.getById(id);
        //将经纪人放到请求域中
        model.addAttribute("houseBroker", houseBroker);

        //获取所有的用户
        List<Admin> adminList = adminService.findAll();
        //将所有用户放入到请求域中
        model.addAttribute("adminList",adminList);

        return "houseBroker/edit";
    }

    //更新一个经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        //根据经纪人id(用户id)获得用户信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        //更新经纪人
        houseBrokerService.update(houseBroker);

        return "common/successPage";
    }

    //删除一个经纪人
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id){
        //根据id删除经纪人
        houseBrokerService.delete(id);

        return "redirect:/house/"+houseId;

    }

}
