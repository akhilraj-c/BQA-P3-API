package com.mindteck.common.modules.program_structure_work_flow.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetProgramStructureFlowResponse extends AbstractView {

    private List<ProgramStructureWorkFlow> programStructureFlowList;
    private Long formUniqueId;
}