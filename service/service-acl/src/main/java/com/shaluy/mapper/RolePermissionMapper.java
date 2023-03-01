package com.shaluy.mapper;

import com.shaluy.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    //根据角色id获取权限id
    List<Long> getPermissionIdByRoleId(Long roleId);

    //根据角色id和要分配的权限id新增
    void addByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
