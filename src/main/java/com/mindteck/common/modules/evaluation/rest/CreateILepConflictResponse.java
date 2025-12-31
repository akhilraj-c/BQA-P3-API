package com.mindteck.common.modules.evaluation.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CreateILepConflictResponse extends AbstractView {

    CreateInstConflictResponseModel data;
}
