package com.mindteck.common.service.impl;

import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.dao.AppDao;
import com.mindteck.models_cas.ClientRegistration;
import com.mindteck.common.models.rest.AppRegRequest;
import com.mindteck.common.models.rest.AppRegResponse;
import com.mindteck.common.models.rest.AppRegResponseModel;
import com.mindteck.common.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AppServiceImpl implements AppService {

    @Autowired
    private AppDao appDao;

    @Override
    @Transactional
    public AppRegResponse saveAppData(AppRegRequest appRegRequest) {

        ClientRegistration appInfo = appDao.getAppInfoByDeviceId(appRegRequest.getDeviceId());
        if (appInfo == null) {
            appInfo = new ClientRegistration();
            appInfo.setOsInfo(appRegRequest.getOsInfo());
            appInfo.setDeviceId(appRegRequest.getDeviceId());
            appInfo.setModelInfo(appRegRequest.getDeviceInfo());
            appInfo.setAppVersion(appRegRequest.getAppVersion());
            appInfo.setDeviceToken(appRegRequest.getDeviceToken());
            appInfo.setCount(0);
            appInfo = appDao.save(appInfo);
            LOGGER.debug("AppInfo details saved successfully with AppId:{}", appInfo.getAppId());
        } else {
            appInfo.setOsInfo(appRegRequest.getOsInfo());
            appInfo.setDeviceId(appRegRequest.getDeviceId());
            appInfo.setModelInfo(appRegRequest.getDeviceInfo());
            appInfo.setAppVersion(appRegRequest.getAppVersion());
            appInfo.setDeviceToken(appRegRequest.getDeviceToken());
            appInfo = appDao.save(appInfo);
            LOGGER.debug("App Info successfully updated AppId : {}", appInfo.getAppId());
        }
        return AppRegResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder()
                        .statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(AppRegResponseModel.builder().appId(appInfo.getAppId()).build())
                .build();
    }

    @Override
    public ClientRegistration getAppInfoById(Long appId) {
        return appDao.getAppInfo(appId);
    }
}
