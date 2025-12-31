package com.mindteck.common.service;

import com.mindteck.models_cas.ClientRegistration;
import com.mindteck.common.models.rest.AppRegRequest;
import com.mindteck.common.models.rest.AppRegResponse;

public interface AppService {

    AppRegResponse saveAppData(AppRegRequest appRegRequest);

    ClientRegistration getAppInfoById(Long appId);
}
