package com.shaluy.mapper;

import com.shaluy.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role>{

    /**
     * 查询所有的用户角色
     * @return
     */
    List<Role> selectAll();

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
//    /**
//     * 获得一个用户 根据id
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
//    Page<Role> findPage(Map<String, Object> filters);
}
