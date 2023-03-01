package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.Community;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.CommunityMapper;
import com.shaluy.mapper.DictMapper;
import com.shaluy.service.CommunityService;
import com.shaluy.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    
    @Autowired
    private DictMapper dictMapper;

    @Override
    protected BaseMapper<Community> getEntityDao() {
        return this.communityMapper;
    }

    //重写分页的方法 目的是给小区中的区域个板块的名字赋值
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 3);
        //开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        //查询出分页信息
        Page<Community> page = communityMapper.findPage(filters);
        
        //遍历
        for (Community community : page) {
            //根据区域id获得节点区域名字
            String areaName = dictMapper.getNameById(community.getAreaId());
            //根据板块id获得节点板块名字
            String plateName = dictMapper.getNameById(community.getPlateId());
            //赋值到小区
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }

        return new PageInfo<>(page,5);
    }

    //重写获得一个小区的方法为了给小区的一些属性赋值
    @Override
    public Community getById(Serializable id) {
        //根据id获得小区
        Community community = communityMapper.getById(id);

        //根据区域id获得节点区域名字
        String areaName = dictMapper.getNameById(community.getAreaId());
        //根据板块id获得节点板块名字
        String plateName = dictMapper.getNameById(community.getPlateId());
        //赋值到小区
        community.setAreaName(areaName);
        community.setPlateName(plateName);

        return community;
    }

    @Override
    public List<Community> findAll() {
        return communityMapper.findAll();
    }
}
