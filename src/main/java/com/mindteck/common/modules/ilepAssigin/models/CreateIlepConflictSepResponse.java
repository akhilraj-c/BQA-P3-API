package com.mindteck.common.modules.ilepAssigin.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CreateIlepConflictSepResponse extends AbstractView {

    private CreateIlepConflictSepResponseModel data;
}
