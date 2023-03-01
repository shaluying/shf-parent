package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.Role;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.RoleMapper;
import com.shaluy.mapper.RolePermissionMapper;
import com.shaluy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    @Override
    public void addPermissionByRoleIdAndPermissionId(Long roleId, Long[] permissionIds) {
        //根据角色id删除对应的权限id
        rolePermissionMapper.delete(roleId);

        //根据角色id和要分配的权限id新增
        for (Long permissionId : permissionIds) {
            if (permissionId != null){
                rolePermissionMapper.addByRoleIdAndPermissionId(roleId,permissionId);
            }
        }
    }

    @Override
    protected BaseMapper<Role> getEntityDao() {
        return this.roleMapper;
    }

//    @Override
//    public Integer insert(Role role) {
//        return roleMapper.insert(role);
//    }
//
//    @Override
//    public void delete(Long id) {
//        roleMapper.delete(id);
//    }
//
//    @Override
//    public Role getById(Long id) {
//        return roleMapper.getById(id);
//    }
//
//    @Override
//    public Integer update(Role role) {
//        return roleMapper.update(role);
//    }
//
//    @Override
//    public PageInfo<Role> findPage(Map<String, Object> filters) {
//        //获取当前页和每页显示的条数
//        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
//        int pageSize = CastUtil.castInt(filters.get("pageSize"), 3);
//
//        //通过PageHelper插件开启分页功能
//        PageHelper.startPage(pageNum,pageSize);
//
//        //调用roleMapper中分页及带条件查询的方法
//        Page<Role> page = roleMapper.findPage(filters);
//        return new PageInfo<>(page,5);
//    }
}
