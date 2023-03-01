package com.shaluy.service;

import com.shaluy.entity.Role;

import java.util.List;

public interface RoleService extends BaseService<Role>{

    /**
     * 查询所有的用户角色
     * @return
     */
    public List<Role> findAll();

    //根据角色id和要分配的权限id给角色新增权限
    void addPermissionByRoleIdAndPermissionId(Long roleId, Long[] permissionIds);

//    /**
//     * 保存一个用户
//     * @param role 用户
//     * @return
//     */
//    Integer insert(Role role);
//
//    /**
//     * 删除一个用户
//     * @param id 删除用户的id
//     */
//    void delete(Long id);
//
//    /**根据用户id得到一个用户
//     * @param id 用户id
//     * @return
//     */
//    Role getById(Long id);
//
//    /**
//     * 更新一个用户
//     * @param role 用户
//     * @return
//     */
//    Integer update(Role role);
//
//
//    PageInfo<Role> findPage(Map<String, Object> filters);
}
