package com.shaluy.service;

import com.shaluy.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {
    //获取所有的权限
    List<Map<String, Object>> getAllPermission();

    //根据角色id获取权限
    List<Map<String, Object>> findPermissionServiceByRoleId(Long roleId);

    //获取所有的权限
    List<Permission> findAllPermission();

    //根据用户id获取当前用户的权限码
    List<String> getPermissionCodesByAdminId(Long id);
}
