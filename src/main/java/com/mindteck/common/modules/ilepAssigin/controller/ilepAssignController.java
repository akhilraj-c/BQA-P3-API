package com.mindteck.common.modules.ilepAssigin.controller;

import com.amazonaws.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.ilepAssigin.models.*;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.ilepAssigin.service.IlepAssignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RestController
@Api(tags = {SwaggerHeads.ILEP_ASSIGN})
@Slf4j
public class ilepAssignController {

    @Autowired
    IlepAssignService ilepAssignService;

    @PostMapping(ApiUrls.CREATE_ILEP_MEMBER_SEP)
    @ApiOperation(value = "To create ILEP PAnel separate")
    public ResponseEntity<CreateIlepMemberSepResponse> createILEPMemberSep(@RequestBody @Valid CreateIlepMemberSepRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(91);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.CREATE_ILEP_MEMBER_SEP_INPUT_VALIDATION_FAILED
                );
            }

            if (CollectionUtils.isNullOrEmpty(request.getMemberId())) {
                LOGGER.debug("memberId cannot be empty");
                throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_SEP_INPUT_MEMBER_EMPTY);
            }

            CreateIlepMemberSepResponse response = ilepAssignService.createILEPMemberSep(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_APPROVE_ILEP_SEP)
    @ApiOperation(value = "Separate API for updating the panel status by DFO")
    public ResponseEntity<DFOApproveIlepSepResponse> approvePanelSep(@RequestBody @Valid DFOApprovePanelSepRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(92);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DFO_APPROVE_PANEL_SEP_INPUT_VALIDATION_FAILED
                );
            }

            DFOApproveIlepSepResponse response = ilepAssignService.approvePanelSep(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_ASSIGNED_ILEP_MEMBERS_SEP)
    @ApiOperation(value = "Separate API for getting assigned ilep members")
    public ResponseEntity<IlepGetSepResponse> ilepGetMembersSep(@PathVariable Long formUniqueId) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(102);
        WebUtils.setStatus(status);
        try {

            if (null == formUniqueId) {
                throw new ControllerException(
                        ErrorCode.GET_ILEP_MEMBER_SEP_INPUT_VALIDATION_FAILED
                );
            }

            IlepGetSepResponse response = ilepAssignService.getIlepMemberSep(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTION_CONFLICT_SEP)
    @ApiOperation(value = "Separate API for creating  conflict against ilep members by institution")
    public ResponseEntity<CreateInstConflictSepResponse> createInstConflictSep(@RequestBody @Valid CreateInstConflictSepRequest request, BindingResult bindingResult) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(93);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INSTITUTION_CONFLICT_ACTION_SEP_INPUT_VALIDATION_FAILED
                );
            }

            CreateInstConflictSepResponse response = ilepAssignService.createInstConflictSep(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_APPROVE_CONFLICT_SEP)
    @ApiOperation(value = "Separate API for approving conflict against ilep members by am")
    public ResponseEntity<AmConflictApproveSepResponse> amConflictApproveSep(@RequestBody @Valid AmApproveConflictSepRequest request, BindingResult bindingResult) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(96);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.AM_APPROVE_CONFLICT_SEP_INPUT_VALIDATION_FAILED
                );
            }

            AmConflictApproveSepResponse response = ilepAssignService.amConflictApproveSep(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_APPROVE_ILEP_CONFLICT_SEP)
    @ApiOperation(value = "Separate API for approving   conflict against ilep members by dfo")
    public ResponseEntity<DFOApproveConflictSepResponse> dfoApproveConflictSep(@RequestBody @Valid DFOApproveConflictSepRequest request, BindingResult bindingResult) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(98);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DFO_APPROVE_CONFLICT_SEP_INPUT_VALIDATION_FAILED
                );
            }

            DFOApproveConflictSepResponse response = ilepAssignService.dfoConflictApproveSep(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.ILEP_CONFLICT_SEP)
    @ApiOperation(value = "Separate API for creating   conflict against institution members by ilep")
    public ResponseEntity<CreateIlepConflictSepResponse> createIlepConflictSep(@RequestBody @Valid CreateIlepConflictSepRequest request, BindingResult bindingResult) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(100);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ILEP_CONFLICT_ACTION_SEP_INPUT_VALIDATION_FAILED
                );
            }

            CreateIlepConflictSepResponse response = ilepAssignService.createIlepConflict(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.REMOVE_PANEL_SEP)
    @ApiOperation(value = "Separate API for removing ilep member")
    public ResponseEntity<RemoveIlepSepResponse> removeIlepSep(@RequestBody @Valid RemoveIlepSepRequest request, BindingResult bindingResult) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(99);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.REMOVE_PANEL_SEP_INPUT_VALIDATION_FAILED
                );
            }

            RemoveIlepSepResponse response = ilepAssignService.removeIlep(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_GRAND_ACCESS_TO_ILEP_SEP)
    @ApiOperation(value = "Separate API for granting access to ilep member")
    public ResponseEntity<GrandAccessSepResponse> grandAccessSep(@RequestBody @Valid GrandAccessSepRequest request, BindingResult bindingResult) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(103);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.AM_GRAND_ACCESS_SEP_INPUT_VALIDATION_FAILED
                );
            }

            GrandAccessSepResponse response = ilepAssignService.grandAccessSep(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
}
