package com.shaluy.mapper;

import com.shaluy.entity.Community;

import java.util.List;

public interface CommunityMapper extends BaseMapper<Community> {
    /**
     * 查询所有的小区
     * @return
     */
    List<Community> findAll();
}
