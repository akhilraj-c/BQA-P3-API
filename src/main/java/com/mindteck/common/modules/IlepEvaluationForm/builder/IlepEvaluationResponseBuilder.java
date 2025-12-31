package com.mindteck.common.modules.IlepEvaluationForm.builder;

import com.mindteck.common.constants.Enum.BooleanEnum;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.IlepEvaluationForm.models.*;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.AmEvaluatedUploadedDocResponse;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IlepEvaluationResponseBuilder {

    public IlepEvaluateResponse buldIlepEvaluateResponse(String message) {
        return IlepEvaluateResponse.builder()
                .status(getStatus())
                .data(IlepEvaluateResponseModel.builder()
                        .message(message).build())
                .build();
    }

    public GetILEPEvaluationDetailsResponse buildGetILEPEvaluationDetailsResponse(GetILEPEvaluationDetailsResponseModel responseModel) {
        return GetILEPEvaluationDetailsResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public AmUpdateReportResponse amUpdateReportResponseBuild(Integer status, Long formUniqueId) {
        return AmUpdateReportResponse.builder()
                .status(getStatus())
                .data(AmUpdateReportResponseModel.builder()
                        .success(status)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public ILEPSubmitSummaryResponse buildILEPSubmitSummaryResponse(String message) {
        return ILEPSubmitSummaryResponse.builder()
                .status(getStatus())
                .data(ILEPSubmitSummaryResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public QACApproveReportResponse buildQACApproveReportResponse(Integer status) {
        return QACApproveReportResponse.builder()
                .status(getStatus())
                .data(QACApproveReportResponseModel.builder()
                        .success(status)
                        .build())
                .build();
    }

    public DfoApprovesReportResponse dfoApprovesReportResponseBuild(Integer status, Long formUniqueId) {
        return DfoApprovesReportResponse.builder()
                .status(getStatus())
                .data(DfoApprovesReportResponseModel.builder()
                        .success(status)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public NoConfidentialityAgreementResponse buildNoConfidentialityAgreementResponse(Integer status) {
        return NoConfidentialityAgreementResponse.builder()
                .status(getStatus())
                .data(NoConfidentialityAgreementResponseModel.builder()
                        .success(status)
                        .build())
                .build();
    }

    public GetSiteVisitDataResponse buildGetSiteVisitDataResponse(GetSiteVisitDataResponseModel responseModel) {
        return GetSiteVisitDataResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public CreateSiteVisitResponse createSiteVisitResponseBuild(Integer success, Long formUniqueId) {
        return CreateSiteVisitResponse.builder()
                .status(getStatus())
                .data(CreateSiteVisitResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public GetMeetingDetailsResponse buildGetMeetingDetailsResponse(GetMeetingDetailsResponseModel responseModel) {
        return GetMeetingDetailsResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public InstUpdateDocumentResponse instUpdateDocumentResponseBuild(Integer success, Long formUniqueId) {
        return InstUpdateDocumentResponse.builder()
                .status(getStatus())
                .data(InstitutionUpdateDocumentResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success).build())
                .build();
    }

    public InstitutionAcceptDateResponse institutionAcceptDateResponseBuild(Integer success, Long formUniqueId) {
        return InstitutionAcceptDateResponse.builder()
                .status(getStatus())
                .data(InstitutionAcceptDateResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success).build())
                .build();
    }

    public SiteVisitDateChangeResponse siteVisitDateChangeResponseBuild(Integer success, Long formUniqueId) {
        return SiteVisitDateChangeResponse.builder()
                .status(getStatus())
                .data(SiteVisitDateChangeResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public GetSiteVisitDateChangeResponse getSiteVisitDateChangeResponseBuild(GetSiteVisitDateChangeResponseModel data) {
        return GetSiteVisitDateChangeResponse.builder()
                .status(getStatus())
                .data(data)
                .build();
    }

    public MeetingHeldResponse buildMeetingHeldResponse(String message) {
        return MeetingHeldResponse.builder()
                .status(getStatus())
                .data(MeetingHeldResponseModel.builder().message(message).build())
                .build();
    }


    public AMUpdateDateRequestResponse buildAMUpdateDateRequestResponse() {
        return AMUpdateDateRequestResponse.builder()
                .status(getStatus())
                .data(AMUpdateDateRequestResponseModel.builder().success(BooleanEnum.TRUE.getCode()).build())
                .build();
    }

    public GDQUploadDocumentResponse gdqUploadDocumentResponseBuild(Integer success, Long formUniqueId) {
        return GDQUploadDocumentResponse.builder()
                .status(getStatus())
                .data(GDQUploadDocumentResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public GDQGetDocumentResponse gdqGetDocumentResponseBuild(GDQGetDocumentResponseModel data) {
        return GDQGetDocumentResponse.builder()
                .status(getStatus())
                .data(data)
                .build();

    }

    public AllowGrantAccessResponse buildAllowGrantAccessResponse(String message) {
        return AllowGrantAccessResponse.builder()
                .status(getStatus())
                .data(AllowGrantAccessResponseModel.builder().message(message).build())
                .build();
    }

    public AMUpdateSharedDocStatusResponse buildAMUpdateSharedDocStatusResponse(String message) {
        return AMUpdateSharedDocStatusResponse.builder()
                .status(getStatus())
                .data(AMUpdateSharedDocStatusResponseModel.builder().message(message).build())
                .build();
    }

    public GDQReviewCompletedResponse buildGDQReviewCompletedResponse(String message) {
        return GDQReviewCompletedResponse.builder()
                .status(getStatus())
                .data(GDQReviewCompletedResponseModel.builder().message(message).build())
                .build();
    }

    public AMPartiallyMetUpdateResponse amPartiallyMetUpdateResponseBuild(Integer success, Long formUniqueId) {
        return AMPartiallyMetUpdateResponse.builder()
                .status(getStatus())
                .data(AMPartiallyMetUpdateResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .build();
    }

    public AMChangeSiteVisitDateResponse buildAMChangeSiteVisitDateResponse(Integer success) {
        return AMChangeSiteVisitDateResponse.builder()
                .status(getStatus())
                .data(AMChangeSiteVisitDateResponseModel.builder()
                        .success(success)
                        .build())
                .build();
    }

    public AMEvaluateWithCommentResponse buildAMEvaluateWithCommentResponse(Integer success) {
        return AMEvaluateWithCommentResponse.builder()
                .status(getStatus())
                .data(AMEvaluateWithCommentResponseModel.builder()
                        .success(success)
                        .build())
                .build();
    }

    public EvaluationFeedbackUploadResponse evaluationFeedbackUploadResponseBuild(Long formUniqueId, Integer success) {
        return EvaluationFeedbackUploadResponse.builder()
                .status(getStatus())
                .data(EvaluationFeedbackUploadResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .build();
    }

    public FactualDeferedResponse factualDeferedResponseBuild(Long formUniqueId, Integer success) {
        return FactualDeferedResponse.builder()
                .status(getStatus())
                .data(FactualDeferedResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .build();
    }


    public SubmitAgendaFormResponse submitAgendaFormResponseBuild(Integer success, Long formUniqueId) {
        return SubmitAgendaFormResponse.builder()
                .status(getStatus())
                .data(SubmitAgendaFormResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public InstituteUploadAgendaResponse InstituteUploadAgendaResponseBuild(Integer success, Long formUniqueId) {
        return InstituteUploadAgendaResponse.builder()
                .status(getStatus())
                .data(InstituteUploadAgendaResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public AmEvaluatedUploadedDocResponse amEvaluatedUploadedDocResponseBuild(Integer success) {
        return AmEvaluatedUploadedDocResponse.builder()
                .status(getStatus())
                .data(AmEvaluatedUploadedDocResponseModel.builder()
                        .success(success)
                        .build())
                .build();
    }

    public IlepEvaluatedUploadedDocResponse ilepEvaluatedUploadedDocResponseBuild(Integer success) {
        return IlepEvaluatedUploadedDocResponse.builder()
                .status(getStatus())
                .data(IlepEvaluatedUploadedDocResponseModel.builder()
                        .success(success)
                        .build())
                .build();
    }

    public SiteVisitDoneResponse buildSiteVisitDoneResponse(String message) {
        return SiteVisitDoneResponse.builder()
                .status(getStatus())
                .data(SiteVisitDoneResponseModel.builder().message(message).build())
                .build();
    }

    public Status getStatus() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return status;
    }
}
