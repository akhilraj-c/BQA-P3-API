package com.mindteck.common.modules.evaluation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.modules.evaluation.rest.*;
import org.springframework.stereotype.Service;

@Service
public interface EvaluationService {

    CreateInstConflictResponse createInstConflict(CreateInstConflictRequest request) throws JsonProcessingException;

    CreateILEPMemberResponse createILEPMember(CreateILEPMemberRequest request);

    DfoApprovePanelResponse approvePanel(DfoApprovePanelRequest request);

    UploadMomResponse uploadMom(UploadMomRequest request) throws JsonProcessingException;

    RemovePanelResponse removePanel(RemovePanelRequest request) throws JsonProcessingException;

    CreateMeetingResponse createMeeting(CreateMeetingRequest request) throws JsonProcessingException;

    GetAMConflictFormResponse getAMConflictFormDetails(Long formUniqueId) throws JsonProcessingException;

    CreateILepConflictResponse createIlepConflict(CreateILepConflictRequest request) throws JsonProcessingException;

    GetInstituteOwnConflictDetailsResponse getInstitutionOwnConflictDetails(Long formUniqueId) throws JsonProcessingException;

    GetILEPOwnConflictDetailsResponse getGetILEPOwnConflictDetails(Long formUniqueId) throws JsonProcessingException;

    GetFormIlepMemberResponse getFormIlepMembers(Long formUniqueId);

    ConflictApproveResponse approveConflict(ConflictApproveRequest request) throws JsonProcessingException;

    DfoApproveIlepPanelConflictResponse approvePanel(DfoApproveIlepPanelConflictRequest request);

    GrandAccessResponse amGrandAccessToIlepUser(GrandAccessRequest request) throws JsonProcessingException;

    RevertConflictResponse revertConflict(RevertConflictRequest request) throws JsonProcessingException;

    UploadMeetingReportResponse meetingReportUpload(UploadMeetingReportRequest request) throws JsonProcessingException;
}
