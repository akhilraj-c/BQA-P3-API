package com.mindteck.common.modules.evaluation.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetInstituteOwnConflictDetailsResponse extends AbstractView {
    private GetInstituteOwnConflictDetailsResponseModel data;
}
