package com.shaluy.mapper;

import com.shaluy.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    //根据用户id获得角色id
    List<Long> getRoleIdByAdminId(Long adminId);

    //根据用户id删除角色id
    void deleteByAdminId(Long adminId);

    //根据用户id和角色id新增
    void addByAdminIdAndRoleId(@Param("adminId") Long adminId, @Param("roleId") Long roleId);
}
