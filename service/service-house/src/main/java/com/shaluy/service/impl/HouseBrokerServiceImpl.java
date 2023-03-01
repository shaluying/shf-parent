package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.HouseBroker;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.HouseBrokerMapper;
import com.shaluy.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerMapper houseBrokerMapper;


    @Override
    protected BaseMapper<HouseBroker> getEntityDao() {
        return this.houseBrokerMapper;
    }


    @Override
    public List<HouseBroker> getHouseBrokerListByHouseId(Long houseId) {
        //根据房源id查出经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerMapper.getHouseBrokerListByHouseId(houseId);

        return houseBrokerList;
    }
}
