package com.mindteck.common.dao;

import com.mindteck.models_cas.UserLoginInfo;

public interface UserLoginInfoDao {

    UserLoginInfo saveUserLoginInfo(UserLoginInfo loginInfo);

    UserLoginInfo getByUserIdAndAppId(Long userId, Long appId);

    UserLoginInfo getByToken(String token);

    void delete(UserLoginInfo userLoginInfo);

    void deleteByUserId(Long userId);

}
