package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.Permission;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.PermissionMapper;
import com.shaluy.mapper.RolePermissionMapper;
import com.shaluy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityDao() {
        return this.permissionMapper;
    }

    @Override
    public List<Map<String, Object>> getAllPermission() {
        //获取所有的权限
        List<Permission> permissionList = permissionMapper.getAllPermission();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Permission permission : permissionList) {
            Map<String, Object> permissionMap = new HashMap<>();
            permissionMap.put("id",permission.getId());
            permissionMap.put("pid",permission.getParentId());
            permissionMap.put("name",permission.getName());
            permissionMap.put("checked",true);
            list.add(permissionMap);
        }


        return list;
    }

    @Override
    public List<Map<String, Object>> findPermissionServiceByRoleId(Long roleId) {
        //获取所有权限
        List<Permission> allPermission = permissionMapper.getAllPermission();
        //根据角色id获取已分配权限的id
        List<Long> permissionIdList = rolePermissionMapper.getPermissionIdByRoleId(roleId);

        List<Map<String, Object>> returnList = new ArrayList<>();

        //遍历所有的权限
        for (Permission permission : allPermission) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            if (permissionIdList.contains(permission.getId())){
                map.put("checked",true);
            }
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public List<Permission> findAllPermission() {
        List<Permission> allPermission = permissionMapper.getAllPermission();
        return allPermission;
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodeList = null;

        if (id == 1){
            //证明是系统管理员
            //获取所有的权限编码
            permissionCodeList = permissionMapper.getAllPermissionCodes();

            return permissionCodeList;
        }
        //根据用户id获取对应的权限编码
        permissionCodeList = permissionMapper.getPermissionCodesByAdminId(id);

        return permissionCodeList;
    }
}
