package com.mindteck.common.modules.ilepAssigin.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Setter
@Getter
@SuperBuilder
public class DFOApproveConflictSepResponse extends AbstractView {

    private DFOApproveConflictSepResponseModel data;
}
