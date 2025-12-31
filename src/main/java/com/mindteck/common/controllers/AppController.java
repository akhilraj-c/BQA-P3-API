package com.mindteck.common.controllers;

import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.AppRegRequest;
import com.mindteck.common.models.rest.AppRegResponse;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.service.AppService;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;


    @RestController
    @Api(tags = {SwaggerHeads.APP_REGISTRATION_API})
    @Slf4j
    public class AppController {

        @Autowired
        AppService appService;


        @PostMapping(ApiUrls.APP_REGISTRATION)
        @ApiOperation(value = "Api for registering the app")
        public ResponseEntity<AppRegResponse> appRegistration(@RequestBody @Valid AppRegRequest appRegRequest,
                                                              BindingResult bindingResult) {

            Status status = WebUtils.getStatus();
            status.setApiId(1);
            WebUtils.setStatus(status);
            try {

                if (bindingResult.hasErrors()) {
                    status.setEndTime(System.currentTimeMillis());
                    LOGGER.debug("Input validation failed");
                    throw new ControllerException(status, bindingResult, ErrorCode.APP_REGISTRATION_VALIDATION_FAILED);
                }

                AppRegResponse  appRegResponse= appService.saveAppData(appRegRequest);
                return ResponseEntity.ok(appRegResponse);
            } catch (Exception e) {
                LOGGER.error(Arrays.toString(e.getStackTrace()));
                throw e;
            }
        }
    }
