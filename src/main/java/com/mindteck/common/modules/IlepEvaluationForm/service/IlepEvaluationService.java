package com.mindteck.common.modules.IlepEvaluationForm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.modules.IlepEvaluationForm.models.*;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.AmEvaluatedUploadedDocResponse;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public interface IlepEvaluationService {

    IlepEvaluateResponse ilepEvaluate(IlepEvaluateRequest request);

    GetILEPEvaluationDetailsResponse getILEPEvaluationDetails(Long formUniqueId);

    AmUpdateReportResponse amUpdateReportStatus(Long formUniqueId, AmApproveReportRequest request);

    ILEPSubmitSummaryResponse submitILEPSummary(ILEPSubmitSummaryRequest request) throws JsonProcessingException;

    QACApproveReportResponse qacApprovesReport(Long formUniqueId);

    DfoApprovesReportResponse dfoUpdateReportStatus(Long formUniqueId);

    IlepEvaluateResponse ilepUpdateEvaluation(IlepUpdateEvaluationRequest request);

    NoConfidentialityAgreementResponse signNoConfidentiality(Long formUniqueId, Integer status) throws JsonProcessingException;

    GetSiteVisitDataResponse getSiteVisitDetails(Long formUniqueId) throws InvocationTargetException, IllegalAccessException;

    CreateSiteVisitResponse createSiteVisit(CreateSiteVisitRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException;

    GetMeetingDetailsResponse getMeetingDetails(Long formUniqueId) throws InvocationTargetException, IllegalAccessException;

    InstUpdateDocumentResponse institutionUpdateDocument(InstituteUpdateDocumentRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException;

    InstitutionAcceptDateResponse instituteAcceptSiteVisitDate(InstitutionAcceptDateRequest request) throws JsonProcessingException;

    SiteVisitDateChangeResponse instituteRequestSiteVisitDateChange(SiteVisitDateChangeRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException;

    GetSiteVisitDateChangeResponse getSiteVisitDateChangeDetails(Long formUniqueId) throws InvocationTargetException, IllegalAccessException;

    MeetingHeldResponse updateMeetingHeldStatus(MeetingHeldRequest request) throws JsonProcessingException;

    AMUpdateDateRequestResponse amUpdateDateRequest(AMUpdateDateRequest request) throws JsonProcessingException;

    GDQUploadDocumentResponse gdqUploadDocuments(GDQUploadDocumentRequest request) throws InvocationTargetException, IllegalAccessException;

    GDQGetDocumentResponse getGdqUploadedDocuments(Long formUniqueId) throws InvocationTargetException, IllegalAccessException;

    AllowGrantAccessResponse allowGrantAccess(AllowGrantAccessRequest request);

    AMUpdateSharedDocStatusResponse updateAmDocSharedStatus(AMUpdateSharedDocStatusRequest request);

    GDQReviewCompletedResponse updateGdqReviewCompleted(GDQReviewCompletedRequest request);

    AMPartiallyMetUpdateResponse amUpdatePartiallyMetStatus(AMPartiallyMetUpdateRequest request) throws InvocationTargetException, IllegalAccessException;

    AMChangeSiteVisitDateResponse changeSiteVisitDate(AMChangeSiteVisitDateRequest request) throws JsonProcessingException;

    AMEvaluateWithCommentResponse evaluateWithRevertComment(AMEvaluateWithCommentRequest request);

    EvaluationFeedbackUploadResponse evaluationFeedbackUloadFile(EvaluationFeedbackUploadRequest request);

    FactualDeferedResponse factualDeferedStarted(FactualDeferedRequest request);

    SubmitAgendaFormResponse uploadAgendaForm(SubmitAgendaFormRequest request) throws JsonProcessingException;

    InstituteUploadAgendaResponse instUploadAgendaForm(InstituteUploadAgendaRequest request) throws JsonProcessingException;

    AmEvaluatedUploadedDocResponse uploadAmEvaluatedDoc (AmEvaluatedUploadedDocRequest request) throws JsonProcessingException;

    IlepEvaluatedUploadedDocResponse uploadIlepEvaluatedDoc (IlepEvaluatedUploadedDocRequest request) throws JsonProcessingException;

    SiteVisitDoneResponse updateSiteVisitStatus(SiteVisitDoneRequest request) throws JsonProcessingException;

    GetILEPEvaluationDetailsResponse getIlepEvaluationCopy(Long formUniqueId) throws JsonProcessingException;

}
