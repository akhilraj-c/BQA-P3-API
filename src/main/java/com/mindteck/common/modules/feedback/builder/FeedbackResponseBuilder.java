package com.mindteck.common.modules.feedback.builder;

import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.feedback.models.*;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeedbackResponseBuilder {
    public SharedDocToQacStatusUpdateResponse buildSharedDocToQacStatusUpdateResponse(String message) {
        return SharedDocToQacStatusUpdateResponse.builder()
                .status(getStatus())
                .data(SharedDocToQacStatusUpdateResponseModel.builder().message(message).build())
                .build();
    }

    public SharedDocToNacStatusUpdateResponse buildSharedDocToNacStatusUpdateResponse(String message) {
        return SharedDocToNacStatusUpdateResponse.builder()
                .status(getStatus())
                .data(SharedDocToNacStatusUpdateResponseModel.builder().message(message).build())
                .build();
    }

    public McuScannedDocumentCompletedStatusUpdateResponse buildMcuScannedDocumentCompletedStatusUpdateResponse(String message) {
        return McuScannedDocumentCompletedStatusUpdateResponse.builder()
                .status(getStatus())
                .data(McuScannedDocumentCompletedStatusUpdateResponseModel.builder().message(message).build())
                .build();
    }

    public DfoApprovedStatusUpdateResponse buildDfoApprovedStatusUpdateResponse(String message) {
        return DfoApprovedStatusUpdateResponse.builder()
                .status(getStatus())
                .data(DfoApprovedStatusUpdateResponseModel.builder().message(message).build())
                .build();
    }

    public InstitutionUpdateCommentResponse buildInstitutionUpdateCommentResponse(String message) {
        return InstitutionUpdateCommentResponse.builder()
                .status(getStatus())
                .data(InstitutionUpdateCommentResponseModel.builder().message(message).build())
                .build();
    }


    public QACSubmitFeedbackResponse createQACSubmitFeedbackResponse(Integer success) {
        return QACSubmitFeedbackResponse.builder()
                .status(getStatus())
                .data(QACSubmitFeedbackResponseModel.builder()
                        .success(success).build())
                .build();
    }

    public NACApproveDocResponse createNACApproveDocResponse(Integer success) {
        return NACApproveDocResponse.builder()
                .status(getStatus())
                .data(NACApproveDocResponseModel.builder()
                        .success(success).build())
                .build();
    }


    public GetDocumentFeedbackResponse createGetDocumentFeedbackResponse(GetDocumentFeedbackResponseModel responseModel) {
        return GetDocumentFeedbackResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }


    public SaveSerialNumberResponse createSaveSerialNumberResponse(Integer success) {
        return SaveSerialNumberResponse.builder()
                .status(getStatus())
                .data(SaveSerialNumberResponseModel.builder().success(success).build())
                .build();
    }

    public QacSubmitReportResponse buildQacSubmitReportResponse(String message) {
        return QacSubmitReportResponse.builder()
                .status(getStatus())
                .data(QacSubmitReportResponseModel.builder().message(message).build())
                .build();
    }

    public NACSubmitFeedbackResponse createNACSubmitFeedbackResponse(Integer success) {
        return NACSubmitFeedbackResponse.builder()
                .status(getStatus())
                .data(NACSubmitFeedbackResponseModel.builder()
                        .success(success).build())
                .build();
    }

    public FeedbackStatusUpdateResponse buildFeedbackStatusUpdateResponse(String message) {
        return FeedbackStatusUpdateResponse.builder()
                .status(getStatus())
                .data(FeedbackStatusUpdateResponseModel.builder()
                        .message(message).build())
                .build();
    }

    public FactualAccuracyReportResponse buildFactualAccuracyReportResponse(Integer success) {
        return FactualAccuracyReportResponse.builder()
                .status(getStatus())
                .data(FactualAccuracyReportResponseModel.builder()
                        .success(success).build())
                .build();
    }

    public FactualAccuracyStartResponse buildFactualAccuracyStartResponse(String message) {
        return FactualAccuracyStartResponse.builder()
                .status(getStatus())
                .data(FactualAccuracyStartResponseModel.builder()
                        .message(message).build())
                .build();
    }

    public DFOSharedToCabinetResponse buildDFOSharedToCabinetResponse(Integer success) {
        return DFOSharedToCabinetResponse.builder()
                .status(getStatus())
                .data(DFOSharedToCabinetResponseModel.builder()
                        .success(success).build())
                .build();
    }

    public DFOAdminCabinetApprovedResponse buildDFOAdminCabinetApprovedResponse(Integer success) {
        return DFOAdminCabinetApprovedResponse.builder()
                .status(getStatus())
                .data(DFOAdminCabinetApprovedResponseModel.builder()
                        .success(success).build())
                .build();
    }

    public CreateAppealResponse buildCreateAppealResponse(String message) {
        return CreateAppealResponse.builder()
                .status(getStatus())
                .data(CreateAppealResponseModel.builder()
                        .message(message).build())
                .build();
    }

    public McoCommentResponse buildMcoCommentResponse(String message) {
        return McoCommentResponse.builder()
                .status(getStatus())
                .data(McoCommentResponseModel.builder()
                        .message(message).build())
                .build();
    }

    public Status getStatus() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return status;
    }
}
