package com.mindteck.common.modules.program_structure_work_flow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowRequest;
import com.mindteck.common.modules.program_structure_work_flow.service.ProgramStructureFlowService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.program_structure_work_flow.model.GetProgramStructureFlowResponse;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowResponse;
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
@Api(tags = {SwaggerHeads.PROGRAM_STRUCTURE_FLOW})
@Slf4j
public class ProgramStructureFlowController {

    final int TYPE_SAVE = 0;
    final int TYPE_VERIFY = 1;

    @Autowired
    ProgramStructureFlowService programStructureService;

    @GetMapping(ApiUrls.GET_PROGRAM_STRUCTURE_FLOW)
    @ApiOperation(value = "Get application data details", tags = {SwaggerHeads.STANDARDS_WORK_FLOW})
    public ResponseEntity<GetProgramStructureFlowResponse> getProgramStructureFlows(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult
            bindingResult) throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {
            GetProgramStructureFlowResponse response = programStructureService.getProgramStructureFlows(request.getFormUniqueId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
    @PostMapping(ApiUrls.SAVE_PROGRAM_STRUCTURE_FLOW)
    @ApiOperation(value = "ILEP member evaluate The Application")
    public ResponseEntity<SaveProgramStructureFlowResponse> saveProgramStructureFlows(
            @RequestBody @Valid SaveProgramStructureFlowRequest request,
            BindingResult bindingResult) {
        return performProgramStructureFlows(request, bindingResult, TYPE_SAVE);
    }

    @PostMapping(ApiUrls.VERIFY_PROGRAM_STRUCTURE_FLOW)
    @ApiOperation(value = "Evaluate Program structure")
    public ResponseEntity<SaveProgramStructureFlowResponse> verifyProgramStructureFlows(
            @RequestBody @Valid SaveProgramStructureFlowRequest request,
            BindingResult bindingResult) {
        return performProgramStructureFlows(request, bindingResult, TYPE_VERIFY);
    }

    public ResponseEntity<SaveProgramStructureFlowResponse> performProgramStructureFlows(
            @RequestBody @Valid SaveProgramStructureFlowRequest request,
            BindingResult bindingResult,
            int type) {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            SaveProgramStructureFlowResponse response;
            if(type == TYPE_VERIFY) {
                response = programStructureService.verifyProgramStructureFlow(request);
            } else {
                response = programStructureService.saveProgramStructureFlow(request);
            }
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
