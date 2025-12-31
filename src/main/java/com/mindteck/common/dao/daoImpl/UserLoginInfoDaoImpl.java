package com.mindteck.common.dao.daoImpl;

import com.mindteck.common.dao.UserLoginInfoDao;
import com.mindteck.models_cas.UserLoginInfo;
import com.mindteck.repository_cas.UserLoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserLoginInfoDaoImpl implements UserLoginInfoDao {

    @Autowired
    private UserLoginInfoRepository userLoginInfoRepository;

    @Override
    public UserLoginInfo saveUserLoginInfo(UserLoginInfo loginInfo) {
        return userLoginInfoRepository.save(loginInfo);
    }

    @Override
    public UserLoginInfo getByUserIdAndAppId(Long userId, Long appId) {
        return userLoginInfoRepository.getByUserIdAndAppId(userId, appId);
    }

    @Override
    public void deleteByUserId(Long userId){
        userLoginInfoRepository.deleteByUserId(userId);
    }

    @Override
    public UserLoginInfo getByToken(String token) {
        return userLoginInfoRepository.getByToken(token);
    }

    @Override
    public void delete(UserLoginInfo userLoginInfo) {
        userLoginInfoRepository.delete(userLoginInfo);
    }
}
