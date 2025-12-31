package com.mindteck.common.modules.date_extension.controller;

import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.date_extension.service.DateExtensionService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.form.rest.DateExtensionRequest;
import com.mindteck.common.modules.form.rest.DateExtensionResponse;
import com.mindteck.common.modules.user.model.rest.DateExtensionApprovalRequest;
import com.mindteck.common.modules.user.model.rest.DateExtensionApprovalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@CrossOrigin
@Api(tags = {SwaggerHeads.PROGRAM_STRUCTURE_FLOW})
@Slf4j
public class DateExtensionController {

    @Autowired
    DateExtensionService dateExtensionService;

    @PostMapping(ApiUrls.REQUEST_DATE_EXTENSION)
    @ApiOperation(value = "API for requesting date extension by institution for submission date for both additional data form filling")
    public ResponseEntity<DateExtensionResponse> dateExtension(@RequestBody @Valid DateExtensionRequest dateExtensionRequest,
                                                               BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(10);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DATE_EXTENSION_INPUT_VALIDATION_FAILED
                );
            }

            DateExtensionResponse dateExtensionResponse = dateExtensionService.dateExtension(dateExtensionRequest);
            return ResponseEntity.ok(dateExtensionResponse);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.DATE_EXTENSION_APPROVAL)
    @ApiOperation(value = "Action for the date Extension Requested", tags = {SwaggerHeads.AUTH_API})
    public ResponseEntity<DateExtensionApprovalResponse> approveDateExtension(@Valid @RequestBody DateExtensionApprovalRequest request,
                                                                              BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DATE_EXTENSION_APPROVAL_INPUT_VALIDATION_FAILED
                );
            }
            final DateExtensionApprovalResponse response = dateExtensionService.updateDateExtensionStatus(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }



}
