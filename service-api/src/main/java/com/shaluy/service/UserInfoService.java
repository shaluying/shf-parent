package com.shaluy.service;

import com.shaluy.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    //根据手机号查询注册信息
    UserInfo getUserInfoByPhone(String phone);
}
