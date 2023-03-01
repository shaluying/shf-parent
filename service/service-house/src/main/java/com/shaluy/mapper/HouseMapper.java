package com.shaluy.mapper;

import com.github.pagehelper.Page;
import com.shaluy.entity.House;
import com.shaluy.vo.HouseQueryVo;
import com.shaluy.vo.HouseVo;

public interface HouseMapper extends BaseMapper<House> {
    //前台分页及带条件查询
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
