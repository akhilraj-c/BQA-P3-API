package com.mindteck.common.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.dao.UserLoginInfoDao;
import com.mindteck.common.models.rest.EncModel;
import com.mindteck.common.requestModifier.CustomRequestWrapper;
import com.mindteck.common.utils.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestBodyFilter implements Filter {


    UserLoginInfoDao userLoginInfoDao;

    public RequestBodyFilter(UserLoginInfoDao userLoginInfoDao) {
        this.userLoginInfoDao = userLoginInfoDao;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        CustomRequestWrapper customRequestWrapper = null;
        try {
            customRequestWrapper = new CustomRequestWrapper((HttpServletRequest) servletRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String body = customRequestWrapper.getBody();
        if (null != body) {
            //changing request body
            try {

                  if (null != request.getHeader("enc") && Boolean.parseBoolean(request.getHeader("enc"))) {
                ObjectMapper objectMapper = new ObjectMapper();
                EncModel encModel = objectMapper.readValue(body, EncModel.class);
                body = EncryptionUtils.decryptDataWithAES(encModel.getData(), "");

                    }


            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }

        }
        Map<String, String[]> qP = new HashMap<>();
        Map<String, String[]> queryParam = customRequestWrapper.getParameterMap();
        if (null != queryParam) {
            //changing query param
              if (null != request.getHeader("enc") && Boolean.parseBoolean(request.getHeader("enc"))) {
            try {
                String encData = queryParam.get("data")[0];
                encData = encData.replace(" ", "+");
                String query = EncryptionUtils.decryptDataWithAES(encData, "");
                String[] queryList = query.split("&");
                for (String q : queryList) {
                    String[] qElement = q.split("=");
                    qP.put(qElement[0], qElement[1].split(","));
                }
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
           } else {
               qP.putAll(queryParam);
            }


        }

        customRequestWrapper.setQueryParamMap(qP);
        customRequestWrapper.setBody(body);
        filterChain.doFilter(customRequestWrapper, servletResponse);
    }
}
