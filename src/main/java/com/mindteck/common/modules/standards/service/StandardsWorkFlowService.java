package com.mindteck.common.modules.standards.service;

import com.mindteck.common.modules.standards.model.SaveStandardWorkFlowResponse;
import com.mindteck.common.modules.standards.service.impl.StandardsWorkFlowServiceImpl;
import com.mindteck.common.modules.standards.controller.StandardsApiType;
import com.mindteck.common.modules.standards.model.GetStandardWorkFlowResponse;
import com.mindteck.common.modules.standards.model.SaveStandardsRequest;
import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StandardsWorkFlowService {

    GetStandardWorkFlowResponse getStandards(Long uniqueId, StandardsApiType apiType);
    List<StandardWorkFlow> getStandardWorkFlows(Long uniqueId, StandardsApiType apiType);
    SaveStandardWorkFlowResponse saveStandards(SaveStandardsRequest request, StandardsApiType apiType);
    SaveStandardWorkFlowResponse evaluate(SaveStandardsRequest request);
    StandardsWorkFlowServiceImpl.StandardWorkFlowSaveResult saveStandardWorkflowList(List<StandardWorkFlow> stds, Long formUniqueId, StandardsApiType apiType, int saveCount, int rowCount) ;

}
