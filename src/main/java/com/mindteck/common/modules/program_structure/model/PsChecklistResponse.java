package com.mindteck.common.modules.program_structure.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PsChecklistResponse extends AbstractView {
    private PsChecklistResponseModel data;
}
