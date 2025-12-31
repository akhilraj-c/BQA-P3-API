package com.mindteck.common.dao.daoImpl;

import com.mindteck.common.dao.AppDao;
import com.mindteck.models_cas.ClientRegistration;
import com.mindteck.repository_cas.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDaoImpl implements AppDao {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public ClientRegistration getAppInfo(Long appId) {
        return applicationRepository.getByAppId(appId);
    }

    @Override
    public ClientRegistration getAppInfoByDeviceId(String deviceId) {
        return applicationRepository.getByDeviceId(deviceId);
    }

    @Override
    public List<String> getFcmToken(List<Long> userId) {
        return null;
    }

    @Override
    public ClientRegistration save(ClientRegistration clientRegistration) {
        return applicationRepository.save(clientRegistration);
    }

    @Override
    public void delete(ClientRegistration clientRegistration) {
        applicationRepository.delete(clientRegistration);
    }
}
