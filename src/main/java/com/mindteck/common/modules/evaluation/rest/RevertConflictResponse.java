package com.mindteck.common.modules.evaluation.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class RevertConflictResponse extends AbstractView {

    private RevertConflictResponseModel data;
}
