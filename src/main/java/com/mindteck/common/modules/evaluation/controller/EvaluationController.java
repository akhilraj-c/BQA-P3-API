package com.mindteck.common.modules.evaluation.controller;

import com.amazonaws.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.evaluation.rest.*;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.evaluation.service.EvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;

@RestController
@Api(tags = {SwaggerHeads.FORM_EVALUATION})
@Slf4j
public class EvaluationController {
    @Autowired
    EvaluationService evaluationService;

    @PostMapping(ApiUrls.INSTITUTION_CONFLICT)
    @ApiOperation(value = "API for creating conflict by institution against ilep memeber")
    public ResponseEntity<CreateInstConflictResponse> createInstConflict(@RequestBody @Valid CreateInstConflictRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(15);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INSTITUTION_CONFLICT_ACTION_INPUT_VALIDATION_FAILED
                );
            }

            CreateInstConflictResponse response = evaluationService.createInstConflict(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.CREATE_ILEP_MEMBER)
    @ApiOperation(value = "To create ILEP PAnel")
    public ResponseEntity<CreateILEPMemberResponse> createILEPMember(@RequestBody @Valid CreateILEPMemberRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(16);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.CREATE_ILEP_MEMBER_INPUT_VALIDATION_FAILED
                );
            }

            if (CollectionUtils.isNullOrEmpty(request.getMemberId())
                    && (Objects.isNull(request.getIsVerificationPanelRequired()) || request.getIsVerificationPanelRequired() == 1)
            ) {
                LOGGER.debug("memberId cannot be empty");
                throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_INPUT_MEMBER_EMPTY);
            }

            CreateILEPMemberResponse response = evaluationService.createILEPMember(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_PANEL_APPROVE)
    @ApiOperation(value = "API for updating the panel status by DFO")
    public ResponseEntity<DfoApprovePanelResponse> approvePanel(@RequestBody @Valid DfoApprovePanelRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(15);
        WebUtils.setStatus(status);
        try {
            
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DFO_APPROVE_PANEL_INPUT_VALIDATION_FAILED
                );
            }

            DfoApprovePanelResponse response = evaluationService.approvePanel(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.UPLOAD_MOM)
    @ApiOperation(value = "API for uploading MOM of the meeting")
    public ResponseEntity<UploadMomResponse> uploadMom(@RequestBody @Valid UploadMomRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(15);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.UPLOAD_MOM_INPUT_VALIDATION_FAILED
                );
            }

            UploadMomResponse response = evaluationService.uploadMom(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.REMOVE_PANEL)
    @ApiOperation(value = "API for removing the panel")
    public ResponseEntity<RemovePanelResponse> removePanel(@RequestBody @Valid RemovePanelRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(15);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.REMOVE_PANEL_INPUT_VALIDATION_FAILED
                );
            }

            RemovePanelResponse response = evaluationService.removePanel(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.CREATE_MEETING)
    @ApiOperation(value = "API to create meeting")
    public ResponseEntity<CreateMeetingResponse> createMeeting(@RequestBody @Valid CreateMeetingRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(20);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.CREATE_MEETING_INPUT_VALIDATION_FAILED
                );
            }

            CreateMeetingResponse response = evaluationService.createMeeting(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_AM_CONFLICT_DETAILS)
    @ApiOperation(value = "To get conflict form details for AM user")
    public ResponseEntity<GetAMConflictFormResponse> getAMConflictFormDetails(@PathVariable(value = "formUniqueId") Long formUniqueId) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(21);
        try {

            GetAMConflictFormResponse response = evaluationService.getAMConflictFormDetails(formUniqueId);
                
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }    


    @PostMapping(ApiUrls.ILEP_CONFLICT)
    @ApiOperation(value = "API to create conflict against institution by ilep member")
    public ResponseEntity<CreateILepConflictResponse> createIlepConflict(@RequestBody @Valid CreateILepConflictRequest request , BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(16);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ILEP_CONFLICT_ACTION_INPUT_VALIDATION_FAILED
                );
            }

            CreateILepConflictResponse response = evaluationService.createIlepConflict(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_INSTITUTE_OWN_CONFLICT)
    @ApiOperation(value = "To get conflict form details of Institution")
    public ResponseEntity<GetInstituteOwnConflictDetailsResponse> getInstituteOwnConflictDetails(@PathVariable(value = "formUniqueId") Long formUniqueId) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(21);
        try {

            GetInstituteOwnConflictDetailsResponse response = evaluationService.getInstitutionOwnConflictDetails(formUniqueId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_ILEP_OWN_CONFLICT)
    @ApiOperation(value = "To get conflict form details of ILEP")
    public ResponseEntity<GetILEPOwnConflictDetailsResponse> getILEPOwnConflictDetails(@PathVariable(value = "formUniqueId") Long formUniqueId) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(21);
        try {

            GetILEPOwnConflictDetailsResponse response = evaluationService.getGetILEPOwnConflictDetails(formUniqueId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_FORM_ILEP_MEMBERS)
    @ApiOperation(value = "To get conflict form details of ILEP")
    public ResponseEntity<GetFormIlepMemberResponse> getFormILepMembers(@PathVariable(value = "formUniqueId") Long formUniqueId) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(23);
        try {
            if( null == formUniqueId)
            {
                throw new ControllerException(ErrorCode.GET_FORM_ILEP_MEMBERS_INPUT_VALIDATION_FAILED);
            }
            GetFormIlepMemberResponse response = evaluationService.getFormIlepMembers(formUniqueId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_APPROVE_CONFLICT)
    @ApiOperation(value = "Api to update conflict form status")
    public ResponseEntity<ConflictApproveResponse> approveConflict(@RequestBody @Valid ConflictApproveRequest request,
                                                                   BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(25);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.AM_APPROVE_CONFLICT_INPUT_VALIDATION_FAILED
                );
            }

            ConflictApproveResponse response = evaluationService.approveConflict(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_INSTITUTE_CONFLICT_DFO)
    @ApiOperation(value = "To get conflict form details of Institution for DFO Director")
    public ResponseEntity<GetInstituteOwnConflictDetailsResponse> getConflictDetailsForDFO(@PathVariable(value = "formUniqueId") Long formUniqueId) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(26);
        try {

            GetInstituteOwnConflictDetailsResponse response = evaluationService.getInstitutionOwnConflictDetails(formUniqueId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_APPROVE_ILEP_CONFLICT)
    @ApiOperation(value = "API for the Approval of conflict by DFO")
    public ResponseEntity<DfoApproveIlepPanelConflictResponse> dfoApproveConflict(@RequestBody @Valid DfoApproveIlepPanelConflictRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(15);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DFO_APPROVE_PANEL_INPUT_VALIDATION_FAILED
                );
            }

            DfoApproveIlepPanelConflictResponse response = evaluationService.approvePanel(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_GRAND_ACCESS_TO_ILEP )
    @ApiOperation(value = "API for grand access to ilep member")
    public ResponseEntity<GrandAccessResponse> amGrandAccessToIlep(@RequestBody @Valid GrandAccessRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(66);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.AM_GRAND_ACCESS_INPUT_VALIDATION_FAILED
                );
            }

            GrandAccessResponse response = evaluationService.amGrandAccessToIlepUser(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.REVERT_CONFLICT )
    @ApiOperation(value = "API for reverting accidental submit")
    public ResponseEntity<RevertConflictResponse> revertConflict(@RequestBody @Valid RevertConflictRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(110);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.REVERT_CONFLICT_INPUT_VALIDATION_FAILED
                );
            }

            RevertConflictResponse response = evaluationService.revertConflict(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.UPLOAD_REPORT_DOCUMENT )
    @ApiOperation(value = "API for uploading meeting report document")
    public ResponseEntity<UploadMeetingReportResponse> uploadMeetingReport(@RequestBody @Valid UploadMeetingReportRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(116);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.UPLOAD_MEETING_REPORT_INPUT_VALIDATION_FAILED
                );
            }

            UploadMeetingReportResponse response = evaluationService.meetingReportUpload(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
}
