package com.mindteck.common.modules.ilepAssigin.models;

import com.mindteck.common.models.rest.AbstractView;
import com.mindteck.common.modules.evaluation.rest.GetInstituteOwnConflictDetailsResponseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GetInstituteOwnConflictDetailsResponse extends AbstractView {

    private GetInstituteOwnConflictDetailsResponseModel data;
}
