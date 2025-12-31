package com.mindteck.common.modules.ilepAssigin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.modules.evaluation.rest.CreateILEPMemberRequest;
import com.mindteck.common.modules.ilepAssigin.models.*;

import java.lang.reflect.InvocationTargetException;

public interface IlepAssignService {

    CreateIlepMemberSepResponse createILEPMemberSep(CreateILEPMemberRequest request);

    DFOApproveIlepSepResponse approvePanelSep(DFOApprovePanelSepRequest request);

    IlepGetSepResponse getIlepMemberSep(Long formUniqueId) throws InvocationTargetException, IllegalAccessException, JsonProcessingException;

    CreateInstConflictSepResponse createInstConflictSep(CreateInstConflictSepRequest request) throws JsonProcessingException;

    AmConflictApproveSepResponse amConflictApproveSep(AmApproveConflictSepRequest request);

    DFOApproveConflictSepResponse dfoConflictApproveSep(DFOApproveConflictSepRequest request);

    CreateIlepConflictSepResponse createIlepConflict(CreateIlepConflictSepRequest request) throws JsonProcessingException;

    RemoveIlepSepResponse removeIlep(RemoveIlepSepRequest request);

    GrandAccessSepResponse grandAccessSep(GrandAccessSepRequest request) throws JsonProcessingException;





}
