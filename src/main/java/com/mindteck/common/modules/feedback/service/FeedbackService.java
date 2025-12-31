package com.mindteck.common.modules.feedback.service;

import com.mindteck.common.modules.feedback.models.*;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    QACSubmitFeedbackResponse qacSubmitFeedback(QACSubmitFeedbackRequest request);

    SharedDocToQacStatusUpdateResponse updateDocSharedToQac(SharedDocToQacStatusUpdateRequest request);

    SharedDocToNacStatusUpdateResponse updateDocSharedToNac(SharedDocToNacStatusUpdateRequest request);

    McuScannedDocumentCompletedStatusUpdateResponse updateMcuScannedDocumentUpdate(McuScannedDocumentCompletedStatusUpdateRequest request);

    DfoApprovedStatusUpdateResponse updateDfoApprovedStatus(DfoApprovedStatusUpdateRequest request);

    InstitutionUpdateCommentResponse institutionUpdateComment(InstitutionUpdateCommentRequest request);

    NACApproveDocResponse nacApproveDoc(NACApproveDocRequest request);

    GetDocumentFeedbackResponse getFeedbackDocumentDetails(Long formUniqueId);

    SaveSerialNumberResponse saveSerialNumber(SaveSerialNumberRequest request);

    QacSubmitReportResponse qacSubmitReport(QacSubmitReportRequest request);

    NACSubmitFeedbackResponse nacSubmitFeedback(NACSubmitFeedbackRequest request);

    FeedbackStatusUpdateResponse feedbackStatusUpdate(FeedbackStatusUpdateRequest request);

    FactualAccuracyReportResponse updateFactualAccuracy(FactualAccuracyReportRequest request);

    FactualAccuracyStartResponse startFactualAccuracy(FactualAccuracyStartRequest request);

    DFOSharedToCabinetResponse sharedReportToCabinet(DFOSharedToCabinetRequest request);

    DFOAdminCabinetApprovedResponse dfoAdminCabinetApproved(DFOAdminCabinetApprovedRequest request);

    CreateAppealResponse createAppeal(CreateAppealRequest request);
    McoCommentResponse updateMcoComment(McoCommentRequest request);
}
