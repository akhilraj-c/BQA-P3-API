package com.mindteck.common.modules.standards.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SaveStandardWorkFlowResponse extends AbstractView {

    private Long formUniqueId;
    private Integer saved;
    private Integer totalRowsSaved;
}