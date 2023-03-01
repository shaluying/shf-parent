package com.shaluy.mapper;

import com.shaluy.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageMapper extends BaseMapper<HouseImage> {
    /**
     * 根据房源id和图片类型查询对应图片
     * @param houseId 房源id
     * @param type 图片类型 1代表房源图片2代表房产图片
     * @return
     */
    List<HouseImage> getHouseImageListByHouseIdAndType(@Param("houseId") Long houseId, @Param("type") Integer type);
}
