package com.mindteck.common.modules.feedback.controller;

import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.feedback.models.*;
import com.mindteck.common.modules.feedback.service.FeedbackService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.IlepEvaluationForm.models.GDQReviewCompletedRequest;
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
@Api(tags = {SwaggerHeads.FEED_BACK})
@Slf4j
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(ApiUrls.QAC_SUBMIT_FEEDBACK)
    @ApiOperation(value = "To submit feedback along with file")
    public ResponseEntity<QACSubmitFeedbackResponse> qacSubmitFeedback(@RequestBody @Valid QACSubmitFeedbackRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(50);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.QAC_SUBMIT_FEEDBACK_INPUT_VALIDATION_FAILED
                );
            }

            QACSubmitFeedbackResponse response = feedbackService.qacSubmitFeedback(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DOC_SHARED_TO_QAC)
    @ApiOperation(value = "Api to update the status as doc shared to QAC")
    public ResponseEntity<SharedDocToQacStatusUpdateResponse> docSharedToQAC(@RequestBody @Valid SharedDocToQacStatusUpdateRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(58);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.AM_SHARED_DOC_TO_QAC_INPUT_VALIDATION_FAILED);
            }

            SharedDocToQacStatusUpdateResponse response = feedbackService.updateDocSharedToQac(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DOC_SHARED_TO_NAC)
    @ApiOperation(value = "Api to update the status as doc shared to NAC")
    public ResponseEntity<SharedDocToNacStatusUpdateResponse> docSharedToNAC(@RequestBody @Valid SharedDocToNacStatusUpdateRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            SharedDocToNacStatusUpdateResponse response = feedbackService.updateDocSharedToNac(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
    /**
     *commented due to flow change on Aug 19 2023
     */
/*    @PostMapping(ApiUrls.MCU_SCANNED_DOCUMENT_UPDATE)
    @ApiOperation(value = "Api to update the status as MCU scanned document")
    public ResponseEntity<McuScannedDocumentCompletedStatusUpdateResponse> mcuScannedDocument(@RequestBody @Valid McuScannedDocumentCompletedStatusUpdateRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            McuScannedDocumentCompletedStatusUpdateResponse response = feedbackService.updateMcuScannedDocumentUpdate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }*/


    @PostMapping(ApiUrls.DFO_APPROVED)
    @ApiOperation(value = "Api to update the status as DFO approved")
    public ResponseEntity<DfoApprovedStatusUpdateResponse> dfoApproved(@RequestBody @Valid DfoApprovedStatusUpdateRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            DfoApprovedStatusUpdateResponse response = feedbackService.updateDfoApprovedStatus(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.INSTITUTION_UPDATE_COMMENT)
    @ApiOperation(value = "Api to update comment by institution")
    public ResponseEntity<InstitutionUpdateCommentResponse> instituteUpdateComment(@RequestBody @Valid InstitutionUpdateCommentRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(62);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INSTITUTION_COMMENT_BACK_INPUT_VALIDATION_FAILED);
            }

            InstitutionUpdateCommentResponse response = feedbackService.institutionUpdateComment(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.NAC_DOC_APPROVE)
    @ApiOperation(value = "To approve doc from nac with comments/additional comments")
    public ResponseEntity<NACApproveDocResponse> nacApproveDoc(@RequestBody @Valid NACApproveDocRequest request, BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(51);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.NAC_DOC_APPROVE_INPUT_VALIDATION_FAILED
                );
            }

            NACApproveDocResponse response = feedbackService.nacApproveDoc(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_FEEDBACK_DOCUMENT_DETAIL)
    @ApiOperation(value = "Get overall status and feedback details submitted so far")
    public ResponseEntity<GetDocumentFeedbackResponse> getFeedbackDocumentDetails(@Valid @PathVariable("formUniqueId") Long formUniqueId) {

        Status status = WebUtils.getStatus();
        status.setApiId(40);
        try {
            if (formUniqueId == null) {
                LOGGER.error("Site visit  date change details request validation failed ");
                throw new ControllerException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_INPUT_VALIDATION_FAILED);
            }

            GetDocumentFeedbackResponse response = feedbackService.getFeedbackDocumentDetails(formUniqueId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    /**
     *commented due to flow change on Aug 19 2023
     */
    /*
    @PostMapping(ApiUrls.MCU_SAVE_SERIAL_NO)
    @ApiOperation(value = "save serail number from MCU user")
    public ResponseEntity<SaveSerialNumberResponse> saveSerialNumber(@RequestBody @Valid SaveSerialNumberRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(53);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.MCU_SAVE_SERIAL_NO_INPUT_VALIDATION_FAILED
                );
            }

            SaveSerialNumberResponse response = feedbackService.saveSerialNumber(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }
*/
    @PostMapping(ApiUrls.QAC_SUBMIT_REPORT)
    @ApiOperation(value = "Api to submit report by QAC")
    public ResponseEntity<QacSubmitReportResponse> qacSubmitReport(@RequestBody @Valid QacSubmitReportRequest request, BindingResult bindingResult) throws InvocationTargetException, IllegalAccessException {

        Status status = WebUtils.getStatus();
        status.setApiId(39);
        try {
            if (bindingResult.hasErrors()) {
                LOGGER.error("Site visit  date accept request validation failed : {}", GDQReviewCompletedRequest.class);
                throw new ControllerException(bindingResult, ErrorCode.INPUT_VALIDATION_FAILED);
            }

            QacSubmitReportResponse response = feedbackService.qacSubmitReport(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.NAC_SUBMIT_FEEDBACK)
    @ApiOperation(value = "To submit feedback along with file")
    public ResponseEntity<NACSubmitFeedbackResponse> nacSubmitFeedback(@RequestBody @Valid NACSubmitFeedbackRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(50);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INPUT_VALIDATION_FAILED
                );
            }

            NACSubmitFeedbackResponse response = feedbackService.nacSubmitFeedback(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.FEEDBACK_STATUS_UPDATE)
    @ApiOperation(value = "To update the feedback Status ")
    public ResponseEntity<FeedbackStatusUpdateResponse> updateFeedbackStatus(@RequestBody @Valid FeedbackStatusUpdateRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(50);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INPUT_VALIDATION_FAILED
                );
            }

            FeedbackStatusUpdateResponse response = feedbackService.feedbackStatusUpdate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.FACTUAL_ACCURACY_COMPLETED)
    @ApiOperation(value = "To update factual accuraccy status as listed or not listed with file upload")
    public ResponseEntity<FactualAccuracyReportResponse> updateFactualAccuracy(@RequestBody @Valid FactualAccuracyReportRequest request,
                                                                               BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(84);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.FACTUAL_ACCURACY_COMPLETED_INPUT_VALIDATION_FAILED
                );
            }

            FactualAccuracyReportResponse response = feedbackService.updateFactualAccuracy(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.FACTUAL_ACCURACY_STARTED)
    @ApiOperation(value = "To update the Status as Factual Accuracy Started ")
    public ResponseEntity<FactualAccuracyStartResponse> startFactualAccuracy(@RequestBody @Valid FactualAccuracyStartRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(50);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INPUT_VALIDATION_FAILED
                );
            }

            FactualAccuracyStartResponse response = feedbackService.startFactualAccuracy(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.DFO_CHIEF_SHARED_REPORT_CABINET)
    @ApiOperation(value = "To update status as shared report to cabinet ")
    public ResponseEntity<DFOSharedToCabinetResponse> sharedReportToCabinet(@RequestBody @Valid DFOSharedToCabinetRequest request,
                                                                            BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(86);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DFO_CHIEF_SHARED_REPORT_CABINET_INPUT_VALIDATION_FAILED
                );
            }

            DFOSharedToCabinetResponse response = feedbackService.sharedReportToCabinet(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.DFO_ADMIN_CABINET_APPROVED)
    @ApiOperation(value = "To update status as DFO Admin updated as cabinet approved")
    public ResponseEntity<DFOAdminCabinetApprovedResponse> dfoAdminCabinetApproved(@RequestBody @Valid DFOAdminCabinetApprovedRequest request,
                                                                                   BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(87);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.DFO_ADMIN_CABINET_APPROVED_INPUT_VALIDATION_FAILED
                );
            }

            DFOAdminCabinetApprovedResponse response = feedbackService.dfoAdminCabinetApproved(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.CREATE_APPEAL)
    @ApiOperation(value = "To create Appeal ")
    public ResponseEntity<CreateAppealResponse> createAppeal(@RequestBody @Valid CreateAppealRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(50);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INPUT_VALIDATION_FAILED
                );
            }

            CreateAppealResponse response = feedbackService.createAppeal(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
    /**
     *commented due to flow change on Aug 19 2023
     */

/*    @PostMapping(ApiUrls.MCO_COMMENT)
    @ApiOperation(value = "To Mco comment ")
    public ResponseEntity<McoCommentResponse> mcoUpdateComment(@RequestBody @Valid McoCommentRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(50);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.INPUT_VALIDATION_FAILED
                );
            }

            McoCommentResponse response = feedbackService.updateMcoComment(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }*/
}
