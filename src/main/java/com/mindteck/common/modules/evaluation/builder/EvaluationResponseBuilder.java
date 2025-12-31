package com.mindteck.common.modules.evaluation.builder;

import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.evaluation.rest.*;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EvaluationResponseBuilder {

    public CreateInstConflictResponse createInstConflictResponseBuilder(Integer success, Long formUniquieId) {
        return CreateInstConflictResponse.builder()
                .status(getStatus())
                .data(CreateInstConflictResponseModel.builder()
                        .success(success).formUniqueId(formUniquieId).build())
                .build();
    }

    public DfoApprovePanelResponse dfoApprovePanelResponseBuilder(String message) {
        return DfoApprovePanelResponse.builder()
                .status(getStatus())
                .data(DfoApprovePanelResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public UploadMomResponse uploadMomResponseBuilder(String message) {
        return UploadMomResponse.builder()
                .status(getStatus())
                .data(UploadMomResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public RemovePanelResponse removePanelResponseBuilder(String message) {
        return RemovePanelResponse.builder()
                .status(getStatus())
                .data(RemovePanelResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public CreateILepConflictResponse createCreateILepConflictResponse(Integer success, Long formUniquieId) {
        return CreateILepConflictResponse.builder()
                .status(getStatus())
                .data(CreateInstConflictResponseModel.builder()
                        .success(success).formUniqueId(formUniquieId).build())
                .build();
    }

    public Status getStatus() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return status;
    }

    public CreateILEPMemberResponse buildCreateILEPMemberResponse(Long panelUniqueId, String message) {
        return CreateILEPMemberResponse.builder()
                .status(getStatus())
                .data(CreateILEPMemberResponseModel.builder()
                        .panelId(panelUniqueId).message(message).build())
                .build();
    }

    public CreateMeetingResponse buildCreateMeetingResponse(Long meetingId, String message) {
        return CreateMeetingResponse.builder()
                .status(getStatus())
                .data(CreateMeetingResponseModel.builder()
                        .meetingId(meetingId).message(message).build())
                .build();
    }

    public GetAMConflictFormResponse buildGetAMConflictFormResponse(GetAMConflictFormResponseModel responseModel) {
        return GetAMConflictFormResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public GetInstituteOwnConflictDetailsResponse buildGetInstituteOwnConflictDetailsResponse(GetInstituteOwnConflictDetailsResponseModel responseModel) {
        return GetInstituteOwnConflictDetailsResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public GetILEPOwnConflictDetailsResponse buildGetILEPOwnConflictDetailsResponse(GetILEPOwnConflictDetailsResponseModel responseModel) {
        return GetILEPOwnConflictDetailsResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public GetFormIlepMemberResponse getFormIlepMemberResponseBuilder(List<GetFormIlepMemberResponseModel> list) {
        return GetFormIlepMemberResponse.builder()
                .status(getStatus())
                .data(list)
                .build();
    }

    public ConflictApproveResponse buildConflictApproveResponse(String message) {
        return ConflictApproveResponse.builder()
                .status(getStatus())
                .data(ConflictApproveResponseModel.builder()
                        .message(message).build())
                .build();
    }

    public DfoApproveIlepPanelConflictResponse dfoApproveIlepPanelConflictResponseBuilder(String message) {
        return DfoApproveIlepPanelConflictResponse.builder()
                .status(getStatus())
                .data(DfoApproveIlepPanelConflictResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public GrandAccessResponse grandAccessResponseBuilder(Integer success , Long formUniqueId) {
        return GrandAccessResponse.builder()
                .status(getStatus())
                .data(GrandAccessResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success).build())
                .build();
    }

    public RevertConflictResponse revertConflictResponseBuiler(Integer success , Long formUniqueId) {
        return RevertConflictResponse.builder()
                .status(getStatus())
                .data(RevertConflictResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success).build())
                .build();
    }

    public UploadMeetingReportResponse uploadMeetingReportResponseBuilder(Integer success , Long formUniqueId) {
        return UploadMeetingReportResponse.builder()
                .status(getStatus())
                .data(UploadMeetingReportResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

}
