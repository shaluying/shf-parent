package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shaluy.entity.House;
import com.shaluy.entity.UserFollow;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.UserFollowMapper;
import com.shaluy.service.DictService;
import com.shaluy.service.HouseService;
import com.shaluy.service.UserFollowService;
import com.shaluy.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Reference
    private DictService dictService;

    @Override
    protected BaseMapper<UserFollow> getEntityDao() {
        return this.userFollowMapper;
    }

    @Override
    public Boolean isUserFollowedHouse(Long userId, Long houseId) {
        Integer count = userFollowMapper.isUserFollowedHouse(userId,houseId);

        if (count > 0){
            //用户关注了房源
            return true;
        }else {
            //用户没有关注房源
            return false;
        }
    }

    @Override
    public PageInfo<UserFollowVo> getFollowedHouseByUserId(Long userId, Integer pageNum, Integer pageSize) {
        //开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        //获取分页信息根据登录用户id
        Page<UserFollowVo> page = userFollowMapper.getFollowedHouseByUserId(userId);
        //遍历page
        for (UserFollowVo userFollowVo : page) {
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            userFollowVo.setDirectionName(directionName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setHouseTypeName(houseTypeName);
        }

        return new PageInfo<>(page,5);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowMapper.cancelFollow(id);
    }
}
