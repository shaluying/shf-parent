package com.shaluy.service;

import com.shaluy.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community> {
    /**
     * 查出所有的小区
     * @return
     */
    List<Community> findAll();
}
