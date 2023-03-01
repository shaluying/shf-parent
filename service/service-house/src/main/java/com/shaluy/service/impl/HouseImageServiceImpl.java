package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.HouseImage;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.HouseImageMapper;
import com.shaluy.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    private HouseImageMapper houseImageMapper;

    @Override
    protected BaseMapper<HouseImage> getEntityDao() {
        return this.houseImageMapper;
    }

    @Override
    public List<HouseImage> getHouseImageListByHouseIdAndType(Long houseId, Integer type) {
        //根据房源id和图片类型获得对应图片
        List<HouseImage> houseImageList = houseImageMapper.getHouseImageListByHouseIdAndType(houseId,type);

        return houseImageList;
    }
}
