package com.shaluy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.Role;
import com.shaluy.service.PermissionService;
import com.shaluy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    private static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;


//    //查询所有角色
//    @RequestMapping("")
//    public String index(Model model){
//        //查出所有用户角色
//        List<Role> roleList = roleService.findAll();
//        //将数据共享到请求域中
//        model.addAttribute("list",roleList);
//
//        return "role/index";
//    }

    //分页和带条件查询的方法
    @RequestMapping("")
    @PreAuthorize("hasAuthority('role.show')")
    public String index(Model model, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放入request请求域中
        model.addAttribute("filters",filters);
        //调用RoleService中分页及带条件查询的方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        //将pageInfo对象放入request请求域中
        model.addAttribute("page",pageInfo);
        return "role/index";

    }

    //去到添加角色的页面
    @RequestMapping("/create")
    @PreAuthorize("hasAuthority('role.create')")
    public String creat(){
        return "role/create";
    }

    //添加角色
    @RequestMapping("/save")
    @PreAuthorize("hasAuthority('role.create')")
    public String addRole(Role role){
        //调用roleService的方法添加用户
        roleService.insert(role);
        //重定向到查询所有页面
//        return "redirect:/role";
        //去common下的成功页面
        return SUCCESS_PAGE;
    }

    //删除角色
    @RequestMapping("/delete/{roleId}")
    @PreAuthorize("hasAuthority('role.delete')")
    public String deleteRole(@PathVariable("roleId") Long id){
        //调用删除方法
        roleService.delete(id);

        //重定向到查询所有页面
        return "redirect:/role";
    }

    //去到修改角色的页面
    @RequestMapping("/edit/{roleId}")
    @PreAuthorize("hasAuthority('role.edit')")
    public String goEditPage(@PathVariable("roleId") Long id, Model model){
        //调用获得一个用户的方法
        Role role = roleService.getById(id);

        //将获得的用户添加到请求域中
        model.addAttribute("role",role);

        //转发到edit.html页面
        return "role/edit";
    }

    //保存修改角色
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('role.edit')")
    public String saveRole(Role role){
        //调用更新角色的方法
        roleService.update(role);

        //去common下的成功页面
        return SUCCESS_PAGE;
    }


//    /**
//     * 封装页面提交的分页参数及搜索条件
//     * @param request
//     * @return
//     */
//    private Map<String, Object> getFilters(HttpServletRequest request) {
//        Enumeration<String> paramNames = request.getParameterNames();
//        Map<String, Object> filters = new TreeMap();
//        while(paramNames != null && paramNames.hasMoreElements()) {
//            String paramName = (String)paramNames.nextElement();
//            String[] values = request.getParameterValues(paramName);
//            if (values != null && values.length != 0) {
//                if (values.length > 1) {
//                    filters.put(paramName, values);
//                } else {
//                    filters.put(paramName, values[0]);
//                }
//            }
//        }
//        if(!filters.containsKey("pageNum")) {
//            filters.put("pageNum", 1);
//        }
//        if(!filters.containsKey("pageSize")) {
//            filters.put("pageSize", 3);
//        }
//
//        return filters;
//    }

    //去到分配权限页面
    @RequestMapping("/assignShow/{roleId}")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String toAssignShowPage(@PathVariable("roleId") Long roleId, Model model){
        model.addAttribute("roleId",roleId);

        //获取所有的权限
        List<Map<String, Object>> permissionList = permissionService.getAllPermission();
        //根据角色id获取权限
        List<Map<String, Object>> zNodes = permissionService.findPermissionServiceByRoleId(roleId);
        //将所有权限放入到请求域中
        model.addAttribute("zNodes",zNodes);

        return "role/assginShow";
    }

    //分配权限
    @RequestMapping("/assignPermission")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String assignPermission(Long roleId, Long[] permissionIds){
        //根据角色id和要分配的权限id给角色新增权限
        roleService.addPermissionByRoleIdAndPermissionId(roleId,permissionIds);

        return "common/successPage";
    }
}
