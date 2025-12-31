package com.mindteck.common.modules.IlepEvaluationForm.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FactualDeferedResponse extends AbstractView {

    private FactualDeferedResponseModel data;
}
