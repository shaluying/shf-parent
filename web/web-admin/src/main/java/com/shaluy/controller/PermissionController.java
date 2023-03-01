package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.Permission;
import com.shaluy.helper.PermissionHelper;
import com.shaluy.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    //去首页
    @RequestMapping("")
    public String index(Model model){
        //获取所有权限
        List<Permission> permissionList = permissionService.findAllPermission();

        List<Permission> treePermission = PermissionHelper.bulid(permissionList);

        model.addAttribute("list", treePermission);

        return "permission/index";
    }

    //去到新增页面
    @RequestMapping("/create")
    public String toCreatePage(Permission permission, Model model){
//        //根据id获得对应权限
//        Permission parentPermission = permissionService.getById(permission.getParentId());
//
//        //赋值
//        if (parentPermission != null){
//            permission.setParentName(parentPermission.getName());
//        }

        model.addAttribute("permission",permission);

        return "permission/create";
    }

    //保存新增
    @RequestMapping("/save")
    public String save(Permission permission) {

        permissionService.insert(permission);

        return "common/successPage";
    }

    //去到编辑的页面
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Permission permission = permissionService.getById(id);

        model.addAttribute("permission",permission);

        return "permission/edit";
    }

    //保存更新
    @RequestMapping(value="/update")
    public String update(Permission permission) {
        permissionService.update(permission);

        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        permissionService.delete(id);

        return "redirect:/permission";
    }

}
