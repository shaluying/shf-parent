package com.shaluy.mapper;

import com.shaluy.entity.Admin;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin>{
    //查询所有用户
    List<Admin> findAll();

    //根据用户名查询用户
    Admin getByUsername(String username);
}
