package com.mindteck.common.modules.IlepEvaluationForm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.IlepEvaluationForm.models.*;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.AmEvaluatedUploadedDocResponse;
import com.mindteck.common.modules.IlepEvaluationForm.service.IlepEvaluationService;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RestController
@Api(tags = {SwaggerHeads.ILEP_EVALUATION})
@Slf4j
public class IlepEvaluationController {

    @Autowired
    IlepEvaluationService ilepEvaluatioService;

    @GetMapping(ApiUrls.GET_ILEP_EVALUATION_APPLICATION_FORM)
    @ApiOperation(value = "To get comments and details of ILEP evaluation ")
    public ResponseEntity<GetILEPEvaluationDetailsResponse> getILEPEvaluationDetails(@PathVariable(value = "formUniqueId") Long formUniqueId) {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            GetILEPEvaluationDetailsResponse response = ilepEvaluatioService.getILEPEvaluationDetails(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

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
    }

    @PostMapping(ApiUrls.AM_APPROVES_THE_REPORT)
    @ApiOperation(value = "Am approves the report created by ilep")
    public ResponseEntity<AmUpdateReportResponse> amUpdateReportStatus(@PathVariable(value = "formUniqueId") Long formUniqueId ,
                                                                       @RequestBody @Valid AmApproveReportRequest request,
                                                                       BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(32);
        try {
            if (null == formUniqueId || bindingResult.hasErrors()) {
                throw new ControllerException(ErrorCode.AM_APPROVES_REPORT_INPUT_VALIDATION_FAILED);
            }
            AmUpdateReportResponse response = ilepEvaluatioService.amUpdateReportStatus(formUniqueId,request );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.ILEP_SUBMIT_SUMMARY)
    @ApiOperation(value = "Submit Ilep evaluation summary details")
    public ResponseEntity<ILEPSubmitSummaryResponse> submitILEPSummary(@RequestBody @Valid ILEPSubmitSummaryRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(33);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ILEP_SUBMIT_SUMMARY_INPUT_VALIDATION_FAILED
                );
            }

            ILEPSubmitSummaryResponse response = ilepEvaluatioService.submitILEPSummary(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.QAC_APPROVES_REPORT)
    @ApiOperation(value = "QAC approves the report created by ilep")
    public ResponseEntity<QACApproveReportResponse> qacApprovesReport(@PathVariable(value = "formUniqueId") Long formUniqueId) {
        Status status = WebUtils.getStatus();
        status.setApiId(34);
        try {
            if (null == formUniqueId) {
                throw new ControllerException(ErrorCode.QAC_APPROVES_REPORT_INPUT_VALIDATION_FAILED);
            }
            QACApproveReportResponse response = ilepEvaluatioService.qacApprovesReport(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_APPROVES_THE_REPORT)
    @ApiOperation(value = "DFO approves the report created by ilep")
    public ResponseEntity<DfoApprovesReportResponse> dfoUpdateReportStatus(@PathVariable(value = "formUniqueId") Long formUniqueId) {
        Status status = WebUtils.getStatus();
        status.setApiId(32);
        try {
            if (null == formUniqueId) {
                throw new ControllerException(ErrorCode.DFO_APPROVES_REPORT_INPUT_VALIDATION_FAILED);
            }
            DfoApprovesReportResponse response = ilepEvaluatioService.dfoUpdateReportStatus(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.ILEP_UPDATE_EVALUATION)
    @ApiOperation(value = "ILEP member Update the Report")
    public ResponseEntity<IlepEvaluateResponse> ilepEvaluateApplication(@RequestBody @Valid IlepUpdateEvaluationRequest request, BindingResult bindingResult) {
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

            IlepEvaluateResponse response = ilepEvaluatioService.ilepUpdateEvaluation(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTE_SIGN_NON_CONFIDENTIAL)
    @ApiOperation(value = "To sign no  confidentiality agreement by institution for date extend request")
    public ResponseEntity<NoConfidentialityAgreementResponse> signNoConfidentiality(@PathVariable(value = "formUniqueId") Long formUniqueId,
                                                                                    @RequestParam("status") @Valid @NotNull(message = "status is required") Integer confidentialStatus) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(35);
        try {

            NoConfidentialityAgreementResponse response = ilepEvaluatioService.signNoConfidentiality(formUniqueId, confidentialStatus);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_SITE_VISIT_DATA)
    @ApiOperation(value = "GET Site visit Data ")
    public ResponseEntity<GetSiteVisitDataResponse> getSiteVisitData(@PathVariable(value = "formUniqueId") Long formUniqueId) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            GetSiteVisitDataResponse response = ilepEvaluatioService.getSiteVisitDetails(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @GetMapping(ApiUrls.GET_MEETING_DETAILS)
    @ApiOperation(value = "GET Meeting details ")
    public ResponseEntity<GetMeetingDetailsResponse> getMeetingDetails(@PathVariable(value = "formUniqueId") Long formUniqueId) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            GetMeetingDetailsResponse response = ilepEvaluatioService.getMeetingDetails(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.CREATE_SITE_VISIT_DATA)
    @ApiOperation(value = "Api for creating site visit  ")
    public ResponseEntity<CreateSiteVisitResponse> getMeetingDetails(@RequestBody @Valid CreateSiteVisitRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(36);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit request validation failed : {}", CreateSiteVisitRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.SITE_VISIT_CREATE_INPUT_VALIDATION_FAILED);
            }

            CreateSiteVisitResponse response = ilepEvaluatioService.createSiteVisit(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
    @GetMapping(ApiUrls.GET_ILEP_EVALUATION_COPY)
    @ApiOperation(value = "Api for geting ilep evaluation copy")
    public ResponseEntity<GetILEPEvaluationDetailsResponse> getIlepEvaluationCopy(@PathVariable Long formUniqueId) throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(121);
        WebUtils.setStatus(status);
        try {
            if (formUniqueId == null) {
                throw new ControllerException(ErrorCode.GET_ILEP_EVALUATION_COPY_INPUT_VALIDATION_FAILED);
            }

            GetILEPEvaluationDetailsResponse response = ilepEvaluatioService.getIlepEvaluationCopy(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTION_UPDATE_SITE_VISIT_DOCUMENT)
    @ApiOperation(value = "Api for updating site visit data by institution site visit  ")
    public ResponseEntity<InstUpdateDocumentResponse> updateDocumentByInsititution(@RequestBody @Valid InstituteUpdateDocumentRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(37);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  update request validation failed : {}", InstituteUpdateDocumentRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INSTITUTION_UPDATE_DOC_INPUT_VALIDATION_FAILED);
            }

            InstUpdateDocumentResponse response = ilepEvaluatioService.institutionUpdateDocument(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTION_ACCEPT_SITE_VISIT_DATE)
    @ApiOperation(value = "Api for institution to accept site visit")
    public ResponseEntity<InstitutionAcceptDateResponse> institutionAcceptSiteVisitDate(@RequestBody @Valid InstitutionAcceptDateRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(38);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", InstitutionAcceptDateRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INSTITUTION_ACCEPT_DATE_INPUT_VALIDATION_FAILED);
            }

            InstitutionAcceptDateResponse response = ilepEvaluatioService.instituteAcceptSiteVisitDate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTION_REQUEST_NEW_SITE_VISIT_DATE)
    @ApiOperation(value = "Api for institution request site visit date change")
    public ResponseEntity<SiteVisitDateChangeResponse> institutionRequestDateChange(@RequestBody @Valid SiteVisitDateChangeRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", SiteVisitDateChangeRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_INPUT_VALIDATION_FAILED);
            }

            SiteVisitDateChangeResponse response = ilepEvaluatioService.instituteRequestSiteVisitDateChange(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_SITE_VISIT_DATE_CHANGE_DETAILS)
    @ApiOperation(value = "Api for getting date change details")
    public ResponseEntity<GetSiteVisitDateChangeResponse> getSiteVisitDateChangeDetails(@Valid @PathVariable("formUniqueId") Long formUniqueId) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(40);
        try {
            if (formUniqueId == null) {
                LOGGER.error("Site visit  date change details request validation failed ");
                throw new ControllerException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_INPUT_VALIDATION_FAILED);
            }

            GetSiteVisitDateChangeResponse response = ilepEvaluatioService.getSiteVisitDateChangeDetails(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_ACTION__VISIT_MEETING_HELD)
    @ApiOperation(value = "Api for Updating the Meeting held Status")
    public ResponseEntity<MeetingHeldResponse> updateMeetingHeld(@RequestBody @Valid MeetingHeldRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", SiteVisitDateChangeRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.AM_ACTION_MEETING_HELD_INPUT_VALIDATION_FAILED);
            }

            MeetingHeldResponse response = ilepEvaluatioService.updateMeetingHeldStatus(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_ACTION_DATE_REQUEST)
    @ApiOperation(value = "Api to update site visit date from am side")
    public ResponseEntity<AMUpdateDateRequestResponse> amUpdateDateRequest(@RequestBody @Valid AMUpdateDateRequest request,
                                                                           BindingResult bindingResult) throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(42);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date update request validation failed : {}", AMUpdateDateRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.AM_ACTION_DATE_REQUEST_INPUT_VALIDATION_FAILED);
            }

            AMUpdateDateRequestResponse response = ilepEvaluatioService.amUpdateDateRequest(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.GDQ_UPLOAD_DOCUMENTS)
    @ApiOperation(value = "Api for am to upload the word document updated by gdq")
    public ResponseEntity<GDQUploadDocumentResponse> amUploadGDQDocuments(@RequestBody @Valid GDQUploadDocumentRequest request,
                                                                          BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(43);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Gdq updated docupload request validation failed : {}", GDQUploadDocumentRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.GDQ_UPLOAD_DOCUMENT_INPUT_VALIDATION_FAILED);
            }

            GDQUploadDocumentResponse response = ilepEvaluatioService.gdqUploadDocuments(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.GDQ_GET_DOCUMENTS)
    @ApiOperation(value = "Api for getting the  word document updated by gdq")
    public ResponseEntity<GDQGetDocumentResponse> getGDQUpdatedDocument(@PathVariable("formUniqueId") Long formUniqueId) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(44);
        try {
            if (formUniqueId == null) {
                LOGGER.error("Get GDQ updated document  request validation failed ");
                throw new ControllerException(ErrorCode.GDQ_GET_DOCUMENT_INPUT_VALIDATION_FAILED);
            }

            GDQGetDocumentResponse response = ilepEvaluatioService.getGdqUploadedDocuments(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @PostMapping(ApiUrls.AM_ALLOW_GRANT_ACCESS)
    @ApiOperation(value = "Api for Granting Access")
    public ResponseEntity<AllowGrantAccessResponse> grantAccess(@RequestBody @Valid AllowGrantAccessRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", AllowGrantAccessRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            AllowGrantAccessResponse response = ilepEvaluatioService.allowGrantAccess(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_DOC_SHARED_STATUS_UPDATE)
    @ApiOperation(value = "Api for updating Status as doc shared")
    public ResponseEntity<AMUpdateSharedDocStatusResponse> updateAmDocSharedStatus(@RequestBody @Valid AMUpdateSharedDocStatusRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", AllowGrantAccessRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            AMUpdateSharedDocStatusResponse response = ilepEvaluatioService.updateAmDocSharedStatus(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.GDQ_REVIEW_COMPLETED)
    @ApiOperation(value = "Api for GDQ review Completed")
    public ResponseEntity<GDQReviewCompletedResponse> gdqReviewCompleted(@RequestBody @Valid GDQReviewCompletedRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            GDQReviewCompletedResponse response = ilepEvaluatioService.updateGdqReviewCompleted(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @PostMapping(ApiUrls.PARTIALLY_MET_AM_UPDATE_STATUS)
    @ApiOperation(value = "Api for am to update the date and comment for partially met form")
    public ResponseEntity<AMPartiallyMetUpdateResponse> amUpdatePartiallyMetFormStatus(@RequestBody @Valid AMPartiallyMetUpdateRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(54);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Am update date and comment for partially met form failed: {}", AMPartiallyMetUpdateRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.AM_UPDATE_PARTIAL_MET_STATUS_INPUT_VALIDATION_FAILED);
            }

            AMPartiallyMetUpdateResponse response = ilepEvaluatioService.amUpdatePartiallyMetStatus(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_CHANGE_SITE_VISIT_DATE)
    @ApiOperation(value = "To change site visite date bby AM")
    public ResponseEntity<AMChangeSiteVisitDateResponse> changeSiteVisitDate(@RequestBody @Valid AMChangeSiteVisitDateRequest request, BindingResult bindingResult) throws JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(67);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(bindingResult, ErrorCode.AM_CHANGE_SITE_VISIT_DATE_INPUT_VALIDATION_FAILED);
            }

            AMChangeSiteVisitDateResponse response = ilepEvaluatioService.changeSiteVisitDate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_EVALUATE_WITH_REVERT_COMMENT)
    @ApiOperation(value = "To evalaute with revert comments ")
    public ResponseEntity<AMEvaluateWithCommentResponse> evaluateWithRevertComment(@RequestBody @Valid AMEvaluateWithCommentRequest request,
                                                                                   BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(68);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(bindingResult, ErrorCode.AM_EVALUATE_WITH_REVERT_COMMENT_INPUT_VALIDATION_FAILED);
            }

            AMEvaluateWithCommentResponse response = ilepEvaluatioService.evaluateWithRevertComment(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.EVALUATION_FEEDBACK_COMMENTS_UPLOAD)
    @ApiOperation(value = "To Upload feedbacks shared by DFO Chief , GDQ AC , QAC ")
    public ResponseEntity<EvaluationFeedbackUploadResponse> evaluationFeedbackUploadFeedback(@RequestBody @Valid EvaluationFeedbackUploadRequest request,
                                                                                   BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(81);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(bindingResult, ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_INPUT_VALIDATION_FAILED);
            }

            EvaluationFeedbackUploadResponse response = ilepEvaluatioService.evaluationFeedbackUloadFile(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.AM_FACTUAL_ACCURACY_WITH_DEFERED)
    @ApiOperation(value = "Am started factual accuracy with defered status ")
    public ResponseEntity<FactualDeferedResponse> factualDeferedStarted(@RequestBody @Valid FactualDeferedRequest request, BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(90);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Factual accuracy started with defered status  request validation failed ");
                throw new ControllerException(ErrorCode.AM_FACTUAL_DEFERED_INPUT_VALIDATION_FAILED);
            }


            FactualDeferedResponse response = ilepEvaluatioService.factualDeferedStarted(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.UPLOAD_AGENDA_FORM)
    @ApiOperation(value = "Api for uploading Agenda form  ")
    public ResponseEntity<SubmitAgendaFormResponse> uploadAgendaForm(@RequestBody @Valid SubmitAgendaFormRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(36);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Agenda Form upload request validation failed : {}", SubmitAgendaFormRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            SubmitAgendaFormResponse response = ilepEvaluatioService.uploadAgendaForm(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @PostMapping(ApiUrls.INSTITUTE_UPLOAD_AGENDA_FORM)
    @ApiOperation(value = "Api for uploading Agenda form by Institute ")
    public ResponseEntity<InstituteUploadAgendaResponse> InstUploadAgendaForm(@RequestBody @Valid InstituteUploadAgendaRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(36);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Agenda Form upload request validation failed : {}", InstituteUploadAgendaRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            InstituteUploadAgendaResponse response = ilepEvaluatioService.instUploadAgendaForm(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @PostMapping(ApiUrls.AM_UPLOAD_SITE_VISIT_EVALUATED_DOC)
    @ApiOperation(value = "Api for uploading site visit evaluated doc by am")
    public ResponseEntity<AmEvaluatedUploadedDocResponse> AmUploadSiteVisitEvaluatedDoc(@RequestBody @Valid AmEvaluatedUploadedDocRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(36);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Am Evaluated Form upload request validation failed : {}", AmEvaluatedUploadedDocRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            AmEvaluatedUploadedDocResponse response = ilepEvaluatioService.uploadAmEvaluatedDoc(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.ILEP_UPLOAD_SITE_VISIT_EVALUATED_DOC)
    @ApiOperation(value = "Api for uploading site visit evaluated doc by Ilep ")
    public ResponseEntity<IlepEvaluatedUploadedDocResponse> AmUploadSiteVisitEvaluatedDoc(@RequestBody @Valid IlepEvaluatedUploadedDocRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(36);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Ilep Evaluated  Form upload request validation failed : {}", IlepEvaluatedUploadedDocRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            IlepEvaluatedUploadedDocResponse response = ilepEvaluatioService.uploadIlepEvaluatedDoc(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.SITE_VISIT_DONE)
    @ApiOperation(value = "Api for Updating the Site vist done status")
    public ResponseEntity<SiteVisitDoneResponse> siteVisitDone(@RequestBody @Valid SiteVisitDoneRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", SiteVisitDateChangeRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.AM_ACTION_MEETING_HELD_INPUT_VALIDATION_FAILED);
            }

            SiteVisitDoneResponse response = ilepEvaluatioService.updateSiteVisitStatus(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
}
