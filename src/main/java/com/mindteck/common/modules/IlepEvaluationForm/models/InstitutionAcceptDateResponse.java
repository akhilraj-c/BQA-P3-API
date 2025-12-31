package com.mindteck.common.modules.IlepEvaluationForm.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InstitutionAcceptDateResponse extends AbstractView {

    private InstitutionAcceptDateResponseModel data;
}
