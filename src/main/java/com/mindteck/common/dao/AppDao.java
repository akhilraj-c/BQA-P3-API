package com.mindteck.common.dao;

import com.mindteck.models_cas.ClientRegistration;

import java.util.List;

public interface AppDao {

    ClientRegistration getAppInfo(Long appId);

    ClientRegistration getAppInfoByDeviceId(String deviceId);

    List<String> getFcmToken(List<Long> userId);

    ClientRegistration save(ClientRegistration clientRegistration);

    void delete (ClientRegistration clientRegistration);
}
