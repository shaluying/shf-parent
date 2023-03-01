package com.shaluy.service;

import com.shaluy.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    /**
     * 根据房源id查询经纪人信息
     * @param houseId 房源id
     * @return
     */
    List<HouseBroker> getHouseBrokerListByHouseId(Long houseId);
}
