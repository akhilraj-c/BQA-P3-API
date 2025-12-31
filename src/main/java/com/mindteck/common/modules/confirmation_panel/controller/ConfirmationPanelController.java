package com.mindteck.common.modules.confirmation_panel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelRequest;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.service.ConfirmationPanelService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.user.model.rest.GetApplicationDetailsRequest;
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
@Api(tags = {SwaggerHeads.MAPPING_PANEL})
@Slf4j
public class ConfirmationPanelController {

    @Autowired
    ConfirmationPanelService confirmationPanelService;

    @GetMapping(ApiUrls.GET_CONFIRMATION_PANEL)
    @ApiOperation(value = "Get application data details", tags = {SwaggerHeads.STANDARDS_WORK_FLOW})
    public ResponseEntity<ConfirmationPanelResponse> getMappingPanels(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult
            bindingResult) throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            ConfirmationPanelResponse response = confirmationPanelService.getPanels(request.getFormUniqueId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
    @PostMapping(ApiUrls.SAVE_CONFIRMATION_PANEL)
    @ApiOperation(value = "ILEP member evaluate The Application")
    public ResponseEntity<SaveConfirmationPanelResponse> saveProgramStructures(@RequestBody @Valid SaveConfirmationPanelRequest request, BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            SaveConfirmationPanelResponse response = confirmationPanelService.savePanels(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
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
