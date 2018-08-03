package com.lennon.user.service.impl;

import com.lennon.user.dataobject.UserInfo;
import com.lennon.user.repository.UserInfoRepository;
import com.lennon.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenId(String openId) {
        return userInfoRepository.findByOpenid(openId);
    }
}
