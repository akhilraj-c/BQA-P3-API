package com.mindteck.common.modules.form.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.models.rest.GetLogRequest;
import com.mindteck.common.models.rest.GetLogResponse;
import com.mindteck.common.modules.evaluation.rest.GetFQDetailsResponse;
import com.mindteck.common.modules.form.rest.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public interface FormService {

    BQAUpdateResponse updateRegistrationStatus(BQAUpdateRequest bqaUpdateRequest);


    GetFormListResponse getFormList(GetFormListRequest request) throws JsonProcessingException;

    UpdateMyFormResponse updateMyForm(UpdateMyFormRequest request) throws InvocationTargetException, IllegalAccessException;

    AddBranchResponse addBranch(AddBranchRequest request);

    GetBranchesResponse getInstitutionBranches(Long formUniqueId);

    ChangeApplicationStatusResponse changeApplicationStatus(ChangeApplicationStatusRequest request);

    DFOApproveAppealResponse dfoApproveAppeal(DFOApproveAppealRequest request);

    DFOChiefSignResponse dfoChiefSign(DFOChiefSignRequest request);

    SetAllApproveResponse setAllApproveHistory(SetAllApproveRequest request);

    GetAllApproveResponse getAllApproveHistory(Long formUniqueId);

    GetLogResponse getLogs(GetLogRequest request);

    ResetApplicationStatusResponse resetApplicationStatus(Long formUniqueId);

    ResetApplicationStatusResponse amRejectApplicationStatus(Long formUniqueId,String rejectionReason);

    ResetApplicationStatusResponse amRejectSiteVisitDocuments(RejectSiteVisitRequest request) throws JsonProcessingException;

    GetRejectionDetailsResponse getRejectionDetails(Long formUniqueId);

    PaymentApprovedResponse approvePayment(Long formUniqueId);

    SaveQpIdResponse saveQpId(SaveQpIdRequest request);

    GetQualificationsWithStatusResponse getQualificationWithStatusForBQA();

    GetFQDetailsResponse getFqDetails(Long formUniqueId);
}
