package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shaluy.entity.Admin;
import com.shaluy.entity.Permission;
import com.shaluy.service.AdminService;
import com.shaluy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Reference
    private RoleService roleService;

    @Reference
    private AdminService adminService;


//    //去首页
//    @RequestMapping("/")
//    public String index(){
//        return "frame/index";
//    }

    //去首页
    @RequestMapping("/")
    public String index(Model model){
//        //设置默认用户的id
//        Long adminId = 2L;
//        //根据用户id查询用户
//        Admin admin = adminService.getById(adminId);

        //通过Spring Security获取User对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据用户名获取Admin对象
        Admin admin = adminService.getByUsername(user.getUsername());

        //根据用户id查询用户所拥有的权限
        List<Permission> permissionList = adminService.getPermissionsByAdminId(admin.getId());

        model.addAttribute("admin", admin);
        model.addAttribute("permissionList",permissionList);

        return "frame/index";
    }

    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    //去spring security的登录页面
    @RequestMapping("/login")
    public String login(){
        return "frame/login.html";
    }

    //去没有权限的提示页面
    @RequestMapping("/auth")
    public String auth(){
        return "frame/auth";
    }

}
