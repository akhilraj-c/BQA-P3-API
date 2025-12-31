package com.mindteck.common.modules.ilepAssigin.builder;

import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.ilepAssigin.models.*;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IlepAssignResponseBuilder {

    public Status getStatus() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return status;
    }

    public CreateIlepMemberSepResponse createIlepMemberSepResponseBuild(Long panelId , String message) {
        return CreateIlepMemberSepResponse.builder()
                .status(getStatus())
                .data(CreateIlepMemberSepResponseModel.builder()
                        .message(message)
                        .panelId(panelId)
                        .build())
                .build();
    }

    public DFOApproveIlepSepResponse dfoApproveIlepSepResponseBuild(String message) {
        return DFOApproveIlepSepResponse.builder()
                .status(getStatus())
                .data(DFOApproveIlepSepResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public IlepGetSepResponse ilepGetSepResponseBuild(PagedData<IlepGetSepResponseModel> ilepGetSepResponseModelPagedData) {
        return IlepGetSepResponse.builder()
                .status(getStatus())
                .data(ilepGetSepResponseModelPagedData)
                .build();
    }

    public CreateInstConflictSepResponse createInstConflictSepResponseBuild(Integer success , Long formUniqueId) {
        return CreateInstConflictSepResponse.builder()
                .status(getStatus())
                .data(CreateInstConflictSepResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public AmConflictApproveSepResponse amConflictApproveSepResponseBuild(String message) {
        return AmConflictApproveSepResponse.builder()
                .status(getStatus())
                .data(AmConflictApproveSepResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public DFOApproveConflictSepResponse dfoApproveConflictSepResponseBuild(String message) {
        return DFOApproveConflictSepResponse.builder()
                .status(getStatus())
                .data(DFOApproveConflictSepResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public CreateIlepConflictSepResponse createIlepConflictSepResponseBuild(Long formUniqueId , Integer success) {
        return CreateIlepConflictSepResponse.builder()
                .status(getStatus())
                .data(CreateIlepConflictSepResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .build();
    }

    public RemoveIlepSepResponse removeIlepSep(String message) {
        return RemoveIlepSepResponse.builder()
                .status(getStatus())
                .data(RemoveIlepSepResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public GrandAccessSepResponse grandAccessSepResponseBuild(Long formUniqueId , Integer success) {
        return GrandAccessSepResponse.builder()
                .status(getStatus())
                .data(GrandAccessSepResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .build();
    }
}
