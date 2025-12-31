package com.mindteck.common.modules.IlepEvaluationForm.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AMChangeSiteVisitDateResponse extends AbstractView {

    private AMChangeSiteVisitDateResponseModel data;
}
