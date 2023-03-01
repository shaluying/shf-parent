package com.shaluy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shaluy.entity.UserInfo;
import com.shaluy.mapper.BaseMapper;
import com.shaluy.mapper.UserInfoMapper;
import com.shaluy.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    protected BaseMapper<UserInfo> getEntityDao() {
        return this.userInfoMapper;
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoMapper.getUserInfoByPhone(phone);
    }
}
