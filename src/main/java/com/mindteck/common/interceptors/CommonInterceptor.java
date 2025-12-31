package com.mindteck.common.interceptors;

import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.UserStatus;
import com.mindteck.common.dao.UserLoginInfoDao;
import com.mindteck.common.exceptionHandler.UserNotFoundException;
import com.mindteck.models_cas.User;
import com.mindteck.models_cas.UserLoginInfo;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
@Slf4j
public class CommonInterceptor implements AsyncHandlerInterceptor {
    @Value("${server.debug}")
    String debugStatus;

    @Autowired
    UserLoginInfoDao userLoginInfoDao;
    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().contains("api/")) {
           /* CustomRequestWrapper customRequestWrapper = new CustomRequestWrapper(request);
            String modifiedBody = customRequestWrapper.getBody();
            customRequestWrapper.setBody(modifiedBody);

            System.out.println("Modified body : " + modifiedBody);

            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(customRequestWrapper));
*/
            LOGGER.debug(request.getRequestURI());
            if(null != request.getHeader("debug"))
                WebUtils.setIsDebug(Boolean.valueOf(request.getHeader("debug")));
            else
                WebUtils.setIsDebug(Boolean.valueOf(debugStatus));
        } else {
            WebUtils.setIsDebug(true);
        }





        Status status = new Status();
        status.setStartTime(System.currentTimeMillis());
        status.setApiUrl(request.getRequestURI());
        WebUtils.setStatus(status);
        Enumeration headerNames = request.getHeaderNames();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if(ipAddress == null || ipAddress.equals(""))
            ipAddress = request.getRemoteAddr();
        WebUtils.setClientIpAddress(ipAddress);
        if(null != WebUtils.getHeader("Authorization")) {
            final String token = WebUtils.getHeader("Authorization").split(" ")[1];
            final UserLoginInfo userLoginInfo = userLoginInfoDao.getByToken(token);
            if(null != userLoginInfo) {
                User user = userDao.getByUserId(userLoginInfo.getUserId());
                if(user.getActive() == UserStatus.INACTIVE.getCode()) {
                    throw new UserNotFoundException(ErrorCode.USER_INACTIVE);
                }
                WebUtils.setUserId(userLoginInfo.getUserId());
                WebUtils.setUserType(userLoginInfo.getUserType());
                WebUtils.setSubType(userLoginInfo.getSubType());
                WebUtils.setAppId(userLoginInfo.getAppId());
                WebUtils.setAesToken(userLoginInfo.getAesKey());
                LOGGER.debug("Login userId : {} , appId : {}",WebUtils.getUserId() , userLoginInfo.getAppId());
            }


        }

        return true;
    }
}
