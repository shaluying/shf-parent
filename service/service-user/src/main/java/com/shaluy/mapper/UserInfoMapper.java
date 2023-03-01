package com.shaluy.mapper;

import com.shaluy.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    //跟手机号查询注册信息
    UserInfo getUserInfoByPhone(String phone);
}
