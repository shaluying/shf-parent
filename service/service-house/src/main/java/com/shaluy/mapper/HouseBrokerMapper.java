package com.shaluy.mapper;

import com.shaluy.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    /**
     * 根基房源id查询经纪人信息
     * @param houseId 房源id
     * @return
     */
    List<HouseBroker> getHouseBrokerListByHouseId(Long houseId);
}
