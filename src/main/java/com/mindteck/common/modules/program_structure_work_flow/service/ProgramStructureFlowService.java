package com.mindteck.common.modules.program_structure_work_flow.service;

import com.mindteck.common.modules.program_structure_work_flow.model.GetProgramStructureFlowResponse;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowRequest;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProgramStructureFlowService {

    GetProgramStructureFlowResponse getProgramStructureFlows(Long uniqueId);
    SaveProgramStructureFlowResponse saveProgramStructureFlow(SaveProgramStructureFlowRequest request);
    SaveProgramStructureFlowResponse verifyProgramStructureFlow(SaveProgramStructureFlowRequest request);

}
