package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.Admin;
import com.shaluy.result.Result;
import com.shaluy.service.AdminService;
import com.shaluy.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Reference
    private AdminService adminService;

    //注入密码加密器
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("")
    public String findPage(Model model, HttpServletRequest request){
        //获得请求参数
        Map<String, Object> filters = getFilters(request);
        //将请求参数放入到请求域中
        model.addAttribute("filters",filters);
        //调用分页及带条件查询的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        //将页面信息共享到请求域中
        model.addAttribute("page",pageInfo);

        return "admin/index";
    }

    @RequestMapping("/create")
    public String create(){
        return "admin/create";
    }

    @RequestMapping("/save")
    public String save(Admin admin){
        //对admin中的密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //调用保存的方法
        adminService.insert(admin);

        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Long id, Model model){
        //调用根基id获取一个的方法
        Admin admin = adminService.getById(id);
        //将数据共享到请求域
        model.addAttribute("admin",admin);

        return "admin/edit";
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        //调用更新的方法
        adminService.update(admin);

        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        //调用根据id删除一个的方法
        adminService.delete(id);

        return "redirect:/admin";
    }

    //去到上传头像的页面
    @RequestMapping("/uploadShow/{id}")
    public String toUploadPage(@PathVariable("id") Long id, Model model){
        model.addAttribute("id",id);

        return "admin/upload";
    }

    //保存头像
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file){
        try {
            //获取文件的字节数组
            byte[] bytes = file.getBytes();
            //随机生成文件在服务器的姓名
            String qiniuFileName = UUID.randomUUID().toString();

            //将头像文件上传到七牛云
            QiniuUtil.upload2Qiniu(bytes,qiniuFileName);

            //根据id获得一个用户
            Admin admin = adminService.getById(id);
            //将用户头像赋值
            admin.setHeadUrl("http://rq08efj8c.hn-bkt.clouddn.com/"+qiniuFileName);
            //更新用户
            adminService.update(admin);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "common/successPage";
    }


    //去到给用户分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(@PathVariable("adminId") Long adminId, Model model){
        //将adminId放入到请求域中
        model.addAttribute("adminId",adminId);
        //根据用户id获取对应的已分配和未分配角色
        Map<String, Object> roleMap = adminService.getRoleByAdminId(adminId);
        //将数据放入到请求域中
        model.addAllAttributes(roleMap);

        return "admin/assignShow";
    }

    //给用户分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds){
        //先根据用户id删除角色
        adminService.deleteRolesByAdminId(adminId);
        //遍历所有的角色id
        for (Long roleId : roleIds) {
            if (roleId != null){
                //在根据用户id和角色id新增用户的角色
                adminService.addRolesByAdminIdAndRoleId(adminId,roleId);
            }
        }

        return "common/successPage";

    }

}
