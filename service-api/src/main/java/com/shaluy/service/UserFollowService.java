package com.shaluy.service;

import com.github.pagehelper.PageInfo;
import com.shaluy.entity.House;
import com.shaluy.entity.UserFollow;
import com.shaluy.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow>{
    //判断用户是否关注房源
    Boolean isUserFollowedHouse(Long userId, Long houseId);

    /**
     * 根据登录用户id查取对应的关注房源信息
     * @param userId 登录用户id
     * @param pageNum 房源分页第几页
     * @param pageSize 房源分页每页几个
     * @return
     */
    PageInfo<UserFollowVo> getFollowedHouseByUserId(Long userId, Integer pageNum, Integer pageSize);

    //根据id取消用户关注
    void cancelFollow(Long id);
}
