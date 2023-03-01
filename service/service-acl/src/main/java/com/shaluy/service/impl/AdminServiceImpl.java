package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.Admin;
import com.shaluy.entity.Permission;
import com.shaluy.entity.Role;
import com.shaluy.helper.PermissionHelper;
import com.shaluy.mapper.*;
import com.shaluy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    protected BaseMapper<Admin> getEntityDao() {
        return this.adminMapper;
    }

    @Override
    public List<Admin> findAll() {
        //查到所有用户
        List<Admin> adminList = adminMapper.findAll();

        return adminList;
    }

    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        //根据用户id获得对应的角色id
        List<Long> roleIdList = adminRoleMapper.getRoleIdByAdminId(adminId);

        //获取所有的角色
        List<Role> roles = roleMapper.selectAll();

        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();

        //遍历roles
        for (Role role : roles) {
            if (roleIdList.contains(role.getId())){//角色已分配
                assginRoleList.add(role);
            }else {//角色未分配
                noAssginRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList",noAssginRoleList);
        roleMap.put("assginRoleList",assginRoleList);

        return roleMap;
    }

    @Override
    public void deleteRolesByAdminId(Long adminId) {
        adminRoleMapper.deleteByAdminId(adminId);
    }

    @Override
    public void addRolesByAdminIdAndRoleId(Long adminId, Long roleId) {
        adminRoleMapper.addByAdminIdAndRoleId(adminId, roleId);
    }

    @Override
    public List<Permission> getPermissionsByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        //判断是否是系统管理员
        if (adminId == 1){
            //是系统管理员，获取所有的权限
            permissionList = permissionMapper.getAllPermission();
        }else {
            //不是系统管理员，根据用户id查询用户所拥有的权限
            permissionList = permissionMapper.getPermissionsByAdminId(adminId);
        }

        //通过工具类将list转换成树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);


        return treeList;
    }

    @Override
    public Admin getByUsername(String username) {
        //根据用户名查询用户
        Admin admin = adminMapper.getByUsername(username);
        return admin;
    }
}
