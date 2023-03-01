package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.HouseUser;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.HouseUserMapper;
import com.shaluy.service.HouseService;
import com.shaluy.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {

    @Autowired
    private HouseUserMapper houseUserMapper;

    @Override
    protected BaseMapper<HouseUser> getEntityDao() {
        return this.houseUserMapper;
    }

    @Override
    public List<HouseUser> getHouseUserListByHouseId(Long houseId) {
        //根据房源id查询房东信息
        List<HouseUser>  houseUserList = houseUserMapper.getHouseUserListByHouseId(houseId);


        return houseUserList;
    }
}
