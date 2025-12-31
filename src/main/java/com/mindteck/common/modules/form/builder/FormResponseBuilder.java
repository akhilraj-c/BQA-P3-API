package com.mindteck.common.modules.form.builder;

import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.models.StaticQualificationDetails;
import com.mindteck.common.models.rest.GetLogResponse;
import com.mindteck.common.models.rest.GetLogResponseModel;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.evaluation.rest.GetFQDetailsResponse;
import com.mindteck.common.modules.evaluation.rest.GetFQDetailsResponseModel;
import com.mindteck.common.modules.form.rest.*;
import com.mindteck.common.modules.form.rest.formdata.GetQualificationsWithStatusResponseModel;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FormResponseBuilder {

    public BQAUpdateResponse BQAUpdateResponseBuilder(Integer changedStatus) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return BQAUpdateResponse.builder()
                .data(BQAUpdateResponseModel.builder()
                        .changedStatus(changedStatus)
                        .build())
                .status(status)
                .build();
    }

    public PaymentApprovedResponse approvePaymentResponseBuilder() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return PaymentApprovedResponse.builder()
                .data(PaymentApproveResponseModel.builder()
                        .message("Payment Approved")
                        .build())
                .status(status)
                .build();
    }

    public DateExtensionResponse dateExtensionResponseBuilder(Integer success, Long formUniqueId) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return DateExtensionResponse.builder()
                .data(DateExtensionResponseModel.builder()
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .status(status)
                .build();
    }

    public GetFormListResponse getFormListResponseBuilder(PagedData<GetFormListResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetFormListResponse.builder()
                .status(status)
                .data(data)
                .build();
    }

    public UpdateMyFormResponse updateMyFormResponseBuilder(Integer success, Long formUniqueId, Long formId) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());

        return UpdateMyFormResponse.builder()
                .status(status)
                .data(UpdateMyFormResponseModel.builder()
                        .formId(formId)
                        .formUniqueId(formUniqueId)
                        .success(success)
                        .build())
                .build();

    }

    public SaveQpIdResponse SaveQpIdResponseBuilder(SaveQpIdResponseModel model) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());

        return SaveQpIdResponse.builder()
                .status(status)
                .data(model)
                .build();
    }

    public AddBranchResponse addBranchResponseBuilder(Integer success, Long formUniqueId) {
        return AddBranchResponse.builder()
                .status(getStatus())
                .data(AddBranchResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();

    }

    public GetBranchesResponse getBranchesResponseBuilder(List<GetBranchesResponseModel> list) {
        return GetBranchesResponse.builder()
                .status(getStatus())
                .data(list)
                .build();
    }

    public ChangeApplicationStatusResponse changeApplicationStatusResponseBuilder(Integer success, Long formUniqueId) {
        return ChangeApplicationStatusResponse.builder()
                .status(getStatus())
                .data(ChangeApplicationStatusResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public DFOApproveAppealResponse dfoApproveAppealResponseBuilder(Integer success, Long formUniqueId) {
        return DFOApproveAppealResponse.builder()
                .status(getStatus())
                .data(DFOApproveAppealResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();


    }

    public DFOChiefSignResponse dfoChiefSignResponseBuild(Integer success, Long formUniqueId) {
        return DFOChiefSignResponse.builder()
                .status(getStatus())
                .data(DFOChiefSignResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public SetAllApproveResponse setAllApproveResponseBuild(Integer success, Long formUniqueId) {
        return SetAllApproveResponse.builder()
                .status(getStatus())
                .data(SetAllApproveResponseModel.builder()
                        .success(success)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public GetAllApproveResponse getAllApproveResponseBuild(String approveHistory, Long formUniqueId) {
        return GetAllApproveResponse.builder()
                .status(getStatus())
                .data(GetAllApproveResponseModel.builder()
                        .approveHistory(approveHistory)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public GetLogResponse getLogResponseBuild(PagedData<GetLogResponseModel> logResponseModelPagedData) {
        return GetLogResponse.builder()
                .status(getStatus())
                .data(logResponseModelPagedData)
                .build();
    }

    public Status getStatus() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return status;

    }

    public ResetApplicationStatusResponse buildResetApplicationStatusResponse(Integer status) {
        return ResetApplicationStatusResponse.builder()
                .status(getStatus())
                .data(ResetApplicationStatusResponseModel.builder().success(status).build())
                .build();
    }

    public GetQualificationsWithStatusResponse getQualificationsWithStatusForBQABuilder(List<GetQualificationsWithStatusResponseModel> data, List<StaticQualificationDetails> staticQualificationDetails) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());

        return GetQualificationsWithStatusResponse.builder()
//                .status(status)
                .qp(data)
                .qpMasters(staticQualificationDetails)
                .build();
    }

    public GetRejectionDetailsResponse buildGetRejectionDetailsResponse(Integer status, String rejectionReason) {
        return GetRejectionDetailsResponse.builder()
                .status(getStatus())
                .data(GetRejectionDetailsResponseModel.builder()
                        .success(status)
                        .rejectionReason(rejectionReason)
                        .build())
                .build();
    }

    public GetFQDetailsResponse getFqDetailsBuilder(GetFQDetailsResponseModel model) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());

        return GetFQDetailsResponse.builder()
                .status(status)
                .data(model)
                .build();
    }
}
