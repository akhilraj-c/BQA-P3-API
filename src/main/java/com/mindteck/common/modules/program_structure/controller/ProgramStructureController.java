package com.mindteck.common.modules.program_structure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.program_structure.model.*;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.program_structure.service.ProgramStructureService;
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
@Api(tags = {SwaggerHeads.PROGRAM_STRUCTURE})
@Slf4j
public class ProgramStructureController {

    @Autowired
    ProgramStructureService programStructureService;

    @GetMapping(ApiUrls.GET_PROGRAM_STRUCTURE)
    @ApiOperation(value = "Get application data details", tags = {SwaggerHeads.STANDARDS_WORK_FLOW})
    public ResponseEntity<GetProgramStructureResponse> getProgramStructures(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult
            bindingResult) throws JsonProcessingException {

        return getProgramStructureResponseResponseEntity(request);
    }
    @GetMapping(ApiUrls.GET_PROGRAM_STRUCTURE_CHECKLIST)
    public ResponseEntity<PsChecklistResponse> getChecklist(@PathVariable Long formUniqueId, @PathVariable Integer slNo) {
        Status status = WebUtils.getStatus();
        status.setApiId(134);
        WebUtils.setStatus(status);
        try {

            PsChecklistResponse response = programStructureService.getChecklist(formUniqueId,slNo);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error works");
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }


    @PostMapping(ApiUrls.SAVE_PROGRAM_STRUCTURE_CHECKLIST)
    @ApiOperation(value = "Save program structure checklist")
    public ResponseEntity<SavePsChecklistResponseModel> savePsChecklist(@Valid @RequestBody SavePsChecklistRequest request) {
        SavePsChecklistResponseModel savedChecklist = programStructureService.savePsChecklist(request);
        return ResponseEntity.ok(savedChecklist);

    }

    @PostMapping(ApiUrls.SAVE_PROGRAM_STRUCTURE)
    @ApiOperation(value = "ILEP member evaluate The Application")
    public ResponseEntity<SaveProgramStructureResponse> saveProgramStructures(@RequestBody @Valid SaveProgramStructureRequest request, BindingResult bindingResult) {

        return saveProgramStructureResponseResponseEntity(request);
    }


    private ResponseEntity<GetProgramStructureResponse> getProgramStructureResponseResponseEntity(GetApplicationDetailsRequest request) {
        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            GetProgramStructureResponse response = programStructureService.getProgramStructures(request.getFormUniqueId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private ResponseEntity<SaveProgramStructureResponse> saveProgramStructureResponseResponseEntity(SaveProgramStructureRequest request) {
        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            SaveProgramStructureResponse response = programStructureService.saveProgramStructure(request);
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
