package com.shaluy.mapper;

import com.shaluy.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    //查询所有权限
    List<Permission> getAllPermission();

    //根据用户id查询用户所拥有的权限
    List<Permission> getPermissionsByAdminId(Long adminId);

    //获取所有的权限编码
    List<String> getAllPermissionCodes();

    //根据用户id获取对应的权限编码
    List<String> getPermissionCodesByAdminId(Long id);
}
