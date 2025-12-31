package com.mindteck.common.modules.program_structure.model;

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
public class GetProgramStructureResponse extends AbstractView {

    private List<ProgramStructure> programStructureList;
    private Long formUniqueId;
}