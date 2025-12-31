package com.mindteck.common.modules.program_structure.service;

import com.mindteck.common.modules.program_structure.model.*;
import com.mindteck.common.modules.program_structure.service.impl.ProgramStructureServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProgramStructureService {

    GetProgramStructureResponse getProgramStructures(Long uniqueId);
    SaveProgramStructureResponse saveProgramStructure(SaveProgramStructureRequest request);
    ProgramStructureServiceImpl.ProgramStructureResult saveProgramStructures(Long formUniqueId, List<ProgramStructure> programStructureList, int deleteAll);
    SavePsChecklistResponseModel savePsChecklist(SavePsChecklistRequest savePsChecklistRequest);
    PsChecklistResponse getChecklist(Long formUniqueId,Integer slNo);
}
