package com.shaluy.service;

import com.shaluy.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    /**
     * 根据房源id和图片类型获得对应图片
     * @param houseId 房源id
     * @param type 图片类型 1代表房源图片2代表房产图片
     * @return
     */
    List<HouseImage> getHouseImageListByHouseIdAndType(Long houseId, Integer type);
}
