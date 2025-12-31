package com.mindteck.common.utils;

import com.mindteck.common.models.rest.Status;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public final class WebUtils {
    private static final String RESPONSE_STATUS = "Status";
    private static final String IS_DEBUG = "isDebug";
    public static final String USER_ID = "userId";

    public static final String APP_ID = "appId";

    public static final String USER_TYPE = "userType";

    public static final String SUB_TYPE = "subType";

    public static final String IP_ADDRESS = "ipAddress";

    public static final String AES_TOKEN = "aesToken";

    public WebUtils() {

    }

    public static String getHeader(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest().getHeader(headerName);
    }

    public static void setStatus(Status status) {
        RequestContextHolder.getRequestAttributes().setAttribute(RESPONSE_STATUS, status,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static Status getStatus() {
        return (Status) RequestContextHolder.getRequestAttributes().getAttribute(RESPONSE_STATUS,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static void setIsDebug(Boolean isDebug) {
        RequestContextHolder.getRequestAttributes().setAttribute(IS_DEBUG, isDebug, RequestAttributes.SCOPE_REQUEST);
    }

    public static Boolean isDebug() {
        return (Boolean) RequestContextHolder.getRequestAttributes().getAttribute(IS_DEBUG,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static void setUserId(Long userId) {
        RequestContextHolder.getRequestAttributes().setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
    }

    public static Long getUserId() {
        return (Long) RequestContextHolder.getRequestAttributes().getAttribute(USER_ID,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static void setUserType(Integer userType) {
        RequestContextHolder.getRequestAttributes().setAttribute(USER_TYPE, userType, RequestAttributes.SCOPE_REQUEST);
    }

    public static Integer getUserType() {
        return (Integer) RequestContextHolder.getRequestAttributes().getAttribute(USER_TYPE,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static void setSubType(Integer subType) {
        RequestContextHolder.getRequestAttributes().setAttribute(SUB_TYPE, subType, RequestAttributes.SCOPE_REQUEST);
    }

    public static Integer getSubType() {
        return (Integer) RequestContextHolder.getRequestAttributes().getAttribute(SUB_TYPE,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static void setClientIpAddress(String ipAddress) {
        RequestContextHolder.getRequestAttributes().setAttribute(IP_ADDRESS , ipAddress , RequestAttributes.SCOPE_REQUEST);
    }

    public static String getClientIpAddress() {
        return (String) RequestContextHolder.getRequestAttributes().getAttribute(IP_ADDRESS , RequestAttributes.SCOPE_REQUEST);
    }

    public static void setAppId(Long appId) {
        RequestContextHolder.getRequestAttributes().setAttribute(APP_ID, appId, RequestAttributes.SCOPE_REQUEST);
    }

    public static Long getAppId() {
        return (Long) RequestContextHolder.getRequestAttributes().getAttribute(APP_ID,
                RequestAttributes.SCOPE_REQUEST);
    }

    public static void setAesToken(String aesToken) {
        RequestContextHolder.getRequestAttributes().setAttribute(AES_TOKEN,aesToken,RequestAttributes.SCOPE_REQUEST);
    }

    public static String getAesToken() {
        return (String) RequestContextHolder.getRequestAttributes().getAttribute(AES_TOKEN,RequestAttributes.SCOPE_REQUEST);
    }

}
