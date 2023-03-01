package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.House;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.DictMapper;
import com.shaluy.mapper.HouseMapper;
import com.shaluy.service.HouseService;
import com.shaluy.vo.HouseQueryVo;
import com.shaluy.vo.HouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    protected BaseMapper<House> getEntityDao() {
        return this.houseMapper;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        //设置id
        house.setId(id);
        //设置状态
        house.setStatus(status);
        //调用更新方法
        houseMapper.update(house);
    }

    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        //开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //前台分页及带条件查询
        Page<HouseVo> page = houseMapper.findPageList(houseQueryVo);

        //遍历page并给期中元素赋值
        for (HouseVo houseVo : page) {
            String houseTypeName = dictMapper.getNameById(houseVo.getHouseTypeId());
            String floorName = dictMapper.getNameById(houseVo.getFloorId());
            String directionName = dictMapper.getNameById(houseVo.getDirectionId());
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }

        return new PageInfo<>(page,5);
    }

    //重写为了给house更多的属性赋值
    @Override
    public House getById(Serializable id) {
        //根据id获取一个房源
        House house = houseMapper.getById(id);

        //根据id查询户型
        String houseType = dictMapper.getNameById(house.getHouseTypeId());
        //根据id查询楼层
        String floor = dictMapper.getNameById(house.getFloorId());
        //根据id查询建筑结构
        String buildStructure = dictMapper.getNameById(house.getBuildStructureId());
        //根据id查询朝向
        String direction = dictMapper.getNameById(house.getDirectionId());
        //根据id查询装修情况
        String decoration = dictMapper.getNameById(house.getDecorationId());
        //根据id查询房屋用途
        String houseUse = dictMapper.getNameById(house.getHouseUseId());
        //给房源的一些属性赋值
        house.setHouseTypeName(houseType);
        house.setFloorName(floor);
        house.setBuildStructureName(buildStructure);
        house.setDirectionName(direction);
        house.setDecorationName(decoration);
        house.setHouseUseName(houseUse);

        return house;
    }
}
