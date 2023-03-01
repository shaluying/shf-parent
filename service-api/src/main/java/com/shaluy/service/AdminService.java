package com.shaluy.service;

import com.shaluy.entity.Admin;
import com.shaluy.entity.Permission;

import java.util.List;
import java.util.Map;

public interface AdminService extends BaseService<Admin> {
    //获得所有用户
    List<Admin> findAll();

    //根据用户id获取对应的分配和未分配的角色
    Map<String, Object> getRoleByAdminId(Long adminId);

    //根据用户id删除对应的角色
    void deleteRolesByAdminId(Long adminId);

    //根据用户id和角色id新增用户和角色
    void addRolesByAdminIdAndRoleId(Long adminId, Long roleId);

    //根据用户id查询用户所拥有的权限
    List<Permission> getPermissionsByAdminId(Long adminId);

    //根据用户名查询admin对象
    Admin getByUsername(String username);
}
