package com.mindteck.common.modules.form.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.GetLogRequest;
import com.mindteck.common.models.rest.GetLogResponse;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.evaluation.rest.GetFQDetailsResponse;
import com.mindteck.common.modules.form.rest.*;
import com.mindteck.common.modules.form.service.FormService;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RestController
@Api(tags ={SwaggerHeads.FORM_API})
@Slf4j
public class FormController {

    @Value("${security-key}")
    private String SECURITY_KEY;

    @Autowired
    FormService formService;

    @PostMapping(ApiUrls.BQA_UPDATE)
    @ApiOperation(value = "Update registration status and date extension")
    public ResponseEntity<BQAUpdateResponse> updateRegistrationStatus(@RequestBody @Valid BQAUpdateRequest bqaUpdateRequest , BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(6);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.BQA_UPDATE_INPUT_VALIDATION_FAILED
                );
            }

            BQAUpdateResponse bqaUpdateResponse = formService.updateRegistrationStatus(bqaUpdateRequest);
            return ResponseEntity.ok(bqaUpdateResponse);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @PostMapping(ApiUrls.GET_APPLICATION_LIST)
    @ApiOperation(value = "API for get form list for all users")
    public ResponseEntity<GetFormListResponse> getFormList(@ModelAttribute @Valid GetFormListRequest getFormListRequest,
                                                           BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(11);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.FORM_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetFormListResponse response = formService.getFormList(getFormListRequest);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.INSTITUTION_UPDATE_FORM)
    @ApiOperation("Api for updating all data by information")
    public ResponseEntity<UpdateMyFormResponse> updateMyForm(@RequestBody @Valid UpdateMyFormRequest request ,
                                                             BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(11);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.UPDATE_MY_FORM_INPUT_VALIDATION_FAILED
                );
            }

            UpdateMyFormResponse response = formService.updateMyForm(request);
           return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTION_ADD_BRANCH)
    @ApiOperation("Api for adding branch to institution")
    public ResponseEntity<AddBranchResponse> addBranch(@RequestBody @Valid AddBranchRequest request ,
                                                       BindingResult bindingResult)  {
        Status status = WebUtils.getStatus();
        status.setApiId(29);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INSITUTION_ADD_BRANCH_INPUT_VALIDATION_FAILED
                );
            }

            AddBranchResponse response = formService.addBranch(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.INSTITUTION_GET_ALL_BRANCH)
    @ApiOperation("Api for getting all branch of an institution")
    public ResponseEntity<GetBranchesResponse> getAllBranches(@PathVariable @NotNull  Long formUniqueId)  {
        Status status = WebUtils.getStatus();
        status.setApiId(29);
        WebUtils.setStatus(status);
        try {
            if (formUniqueId == null) {
                throw new ControllerException(
                        ErrorCode.INSITUTION_GET_ALL_BRANCH_INPUT_VALIDATION_FAILED
                );
            }

            GetBranchesResponse response = formService.getInstitutionBranches(formUniqueId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.CHANGE_APPLICATION_STATUS)
    @ApiOperation("Api for changing the status of application")
    public ResponseEntity<ChangeApplicationStatusResponse> changeApplicationStatus(@RequestBody @Valid ChangeApplicationStatusRequest request , BindingResult bindingResult)  {
        Status status = WebUtils.getStatus();
        status.setApiId(65);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        ErrorCode.CHANGE_APPLICATION_STATUS_INPUT_VALIDATION_FAILED
                );
            }

            ChangeApplicationStatusResponse response = formService.changeApplicationStatus(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_APPROVE_APPEAL)
    @ApiOperation("Api for approving the appeal by dfo")
    public ResponseEntity<DFOApproveAppealResponse> changeApplicationStatus(@RequestBody @Valid DFOApproveAppealRequest request , BindingResult bindingResult)  {
        Status status = WebUtils.getStatus();
        status.setApiId(85);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        ErrorCode.DFO_APPROVE_APPEAL_INPUT_VALIDATION_FAILED
                );
            }

            DFOApproveAppealResponse response = formService.dfoApproveAppeal(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
    /**
     *commented due to flow change on Aug 19 2023
     */
/*    @PostMapping(ApiUrls.DFO_CHIEF_SIGN_REPORT)
    @ApiOperation("Api for dfo chief to sign the report")
    public ResponseEntity<DFOChiefSignResponse> dfoChiefSignReport(@RequestBody @Valid DFOChiefSignRequest request , BindingResult bindingResult)  {
        Status status = WebUtils.getStatus();
        status.setApiId(88);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        ErrorCode.DFO_CHIEF_SIGN_REPORT_INPUT_VALIDATION_FAILED
                );
            }

            DFOChiefSignResponse response = formService.dfoChiefSign(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }*/

    @PostMapping(ApiUrls.SET_ALL_APPROVE_HISTORY)
    @ApiOperation("Api for setting all approve status history")
    public ResponseEntity<SetAllApproveResponse> setAllApproveHistory(@RequestBody @Valid SetAllApproveRequest request , BindingResult bindingResult)  {
        Status status = WebUtils.getStatus();
        status.setApiId(105);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        ErrorCode.SET_APPROVE_HISTORY_INPUT_VALIDATION_FAILED
                );
            }

            SetAllApproveResponse response = formService.setAllApproveHistory(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_ALL_APPROVE_HISTORY)
    @ApiOperation("Api for getting all approve status history")
    public ResponseEntity<GetAllApproveResponse> getAllApproveHistory(@PathVariable @NotNull Long formUniqueId)  {
        Status status = WebUtils.getStatus();
        status.setApiId(104);
        WebUtils.setStatus(status);
        try {
            if (formUniqueId == null) {
                throw new ControllerException(
                        ErrorCode.GET_APPROVE_HISTORY_INPUT_VALIDATION_FAILED
                );
            }

            GetAllApproveResponse response = formService.getAllApproveHistory(formUniqueId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_LOGS)
    @ApiOperation("Api for getting all logs of a form")
    public ResponseEntity<GetLogResponse> getLogs(@ModelAttribute @Valid GetLogRequest request, BindingResult bindingResult)  {
        Status status = WebUtils.getStatus();
        status.setApiId(106);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        ErrorCode.GET_LOGS_INPUT_VALIDATION_FAILED
                );
            }

            GetLogResponse response = formService.getLogs(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.RESET_APPLICATION_STATUS)
    @ApiOperation("API to resert the form status to previsous state with date reset")
    public ResponseEntity<ResetApplicationStatusResponse> resetApplicationStatus(@PathVariable(value = "formUniqueId") Long formUniqueId) {
        Status status = WebUtils.getStatus();
        status.setApiId(111);
        WebUtils.setStatus(status);

        try {

            ResetApplicationStatusResponse response = formService.resetApplicationStatus(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.REJECT_APPLICATION_STATUS)
    @ApiOperation("API to reject the form ")
    public ResponseEntity<ResetApplicationStatusResponse> rejectApplication(@PathVariable(value = "formUniqueId") Long formUniqueId,
                                                                            @PathVariable(value = "rejectionReason") String rejectionReason) {
        Status status = WebUtils.getStatus();
        status.setApiId(112);
        WebUtils.setStatus(status);

        try {

            ResetApplicationStatusResponse response = formService.amRejectApplicationStatus(formUniqueId,rejectionReason);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.REJECT_SITE_VISIT_DOCUMENTS)
    @ApiOperation("API to reject the form ")
    public ResponseEntity<ResetApplicationStatusResponse> rejectSiteVisitDoc(@RequestBody @Valid  RejectSiteVisitRequest request, BindingResult result) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(115);
        WebUtils.setStatus(status);
        if(result.hasErrors()) {
            throw new ControllerException(result,ErrorCode.REJECT_APPLICATION_STATUS_INPUT_VALIDATION_FAILED);
        }

        try {
            ResetApplicationStatusResponse response = formService.amRejectSiteVisitDocuments(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_REJECTION_REASON)
    @ApiOperation("Api to get the rejection reason of institution")
    public ResponseEntity<GetRejectionDetailsResponse> getRejectionReason(@PathVariable @NotNull  Long formUniqueId)  {
        Status status = WebUtils.getStatus();
        status.setApiId(29);
        WebUtils.setStatus(status);
        try {
            if (formUniqueId == null) {
                throw new ControllerException(
                        ErrorCode.INPUT_VALIDATION_FAILED
                );
            }

            GetRejectionDetailsResponse response = formService.getRejectionDetails(formUniqueId);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.APPROVE_PAYMENT)
    @ApiOperation(value = "Approve payment")
    public ResponseEntity<PaymentApprovedResponse> approvePayment(@PathVariable Long formUniqueId){
        Status status = WebUtils.getStatus();
        status.setApiId(135);
        WebUtils.setStatus(status);
        try{
            PaymentApprovedResponse response = formService.approvePayment(formUniqueId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }
    @PostMapping(ApiUrls.SAVE_QP_ID)
    @ApiOperation("Api for saving the qp-id")
    public ResponseEntity<SaveQpIdResponse> saveQpId(@RequestBody @Valid SaveQpIdRequest request)  {
        Status status = WebUtils.getStatus();
        status.setApiId(150);
        WebUtils.setStatus(status);
        try {
            SaveQpIdResponse response = formService.saveQpId(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_QUALIFICATION_DATA_FOR_BQA_TEAM)
    @ApiOperation("Get institute details with status")
    public ResponseEntity<GetQualificationsWithStatusResponse> getQualificationsWithStatusForBQA(@RequestHeader("X-API-KEY") String securityKey)  {

        if (!SECURITY_KEY.equals(securityKey)) {
            throw new ServiceException(ErrorCode.INVALID_STATIC_KEY);
        }

        Status status = WebUtils.getStatus();
        status.setApiId(162);
        WebUtils.setStatus(status);
        try {
            GetQualificationsWithStatusResponse response = formService.getQualificationWithStatusForBQA();
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_FQ_DETAILS+"/{formUniqueId}")
    @ApiOperation(value = "Get FQ details")
    public ResponseEntity<GetFQDetailsResponse> getFqDetails(@PathVariable Long formUniqueId) {
        Status status = WebUtils.getStatus();
        status.setApiId(163);
        WebUtils.setStatus(status);
        try {
            GetFQDetailsResponse response = formService.getFqDetails(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }
}
