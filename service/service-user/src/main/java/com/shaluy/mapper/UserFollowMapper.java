package com.shaluy.mapper;

import com.github.pagehelper.Page;
import com.shaluy.entity.House;
import com.shaluy.entity.UserFollow;
import com.shaluy.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper extends BaseMapper<UserFollow> {
    //判断用户是否关注了房源
    Integer isUserFollowedHouse(@Param("userId") Long userId, @Param("houseId") Long houseId);

    //根据登录用户id获取关注的房源分页信息
    Page<UserFollowVo> getFollowedHouseByUserId(Long userId);

    //根据id取消关注
    void cancelFollow(Long id);
}
