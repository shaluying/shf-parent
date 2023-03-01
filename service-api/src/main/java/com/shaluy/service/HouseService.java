package com.shaluy.service;

import com.github.pagehelper.PageInfo;
import com.shaluy.entity.House;
import com.shaluy.vo.HouseQueryVo;
import com.shaluy.vo.HouseVo;

public interface HouseService extends BaseService<House>{
    /**
     * 发布或取消发布
     * @param id 房源id
     * @param status 发布状态码
     */
    void publish(Long id, Integer status);

    /**
     * 前端分页及带条件查询的方法
     * @param pageNum
     * @param pageSize
     * @param houseQueryVo
     * @return
     */
    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
