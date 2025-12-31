package com.mindteck.common.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.ErrorResponseBuilder.Data;
import com.mindteck.common.ErrorResponseBuilder.ErrorResponse;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        LOGGER.info(request.getRequestURI());
        setErrorResponse(HttpStatus.BAD_REQUEST, response, "Unauthorized access");
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Data errorData =  Data.builder()
                .code(40001)
                .message(message)
                .build();
        Status commonStatus = WebUtils.getStatus();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(commonStatus)
                .data(errorData)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
