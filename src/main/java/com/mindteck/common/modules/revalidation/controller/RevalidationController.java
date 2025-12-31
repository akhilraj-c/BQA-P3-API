package com.mindteck.common.modules.revalidation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.revalidation.model.EnforceMonitoringRequest;
import com.mindteck.common.modules.revalidation.model.EnforceRevalidationRequest;
import com.mindteck.common.modules.revalidation.model.MonitoringFormGetResponse;
import com.mindteck.common.modules.revalidation.service.RevalidationService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.user.model.rest.RegistrationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@CrossOrigin
@Api(tags = {SwaggerHeads.REVALIDATION})
@Slf4j
public class RevalidationController {

    @Autowired
    RevalidationService revalidationService;

    @PostMapping(ApiUrls.ENFORCE_REVALIDATION)
    @ApiOperation(value = "Enforces Revalidation")
    public ResponseEntity<RegistrationResponse> enforceRevalidation(@RequestBody @Valid EnforceRevalidationRequest request)  throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            RegistrationResponse response = revalidationService.enforceMonitoring(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.ENFORCE_MONITORING)
    @ApiOperation(value = "Enforces Monitoring")
    public ResponseEntity<RegistrationResponse> enforceMonitoring(@RequestBody @Valid EnforceMonitoringRequest request)  throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(139);
        try {

            RegistrationResponse response = revalidationService.enforceMonitoring(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_MONITORING_FORM)
    @ApiOperation(value = "Get monitoring form")
    public ResponseEntity<MonitoringFormGetResponse> getMonitoringForm(@PathVariable Long formUniqueId) {
        Status status = WebUtils.getStatus();
        status.setApiId(140);
        WebUtils.setStatus(status);
        try {
            MonitoringFormGetResponse response = revalidationService.getMonitoringForm(formUniqueId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
/*
    @PostMapping(ApiUrls.ILEP_EVALUATE)
    @ApiOperation(value = "ILEP member evaluate The Application")
    public ResponseEntity<IlepEvaluateResponse> ilepEvaluateApplication(@RequestBody @Valid IlepEvaluateRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(6);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ILEP_EVALUATE_INPUT_VALIDATION_FAILED
                );
            }

            IlepEvaluateResponse response = ilepEvaluatioService.ilepEvaluate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }*/

}
