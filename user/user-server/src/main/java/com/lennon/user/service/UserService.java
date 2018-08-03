package com.lennon.user.service;

import com.lennon.user.dataobject.UserInfo;

public interface UserService {
    UserInfo findByOpenId(String openId);
}
