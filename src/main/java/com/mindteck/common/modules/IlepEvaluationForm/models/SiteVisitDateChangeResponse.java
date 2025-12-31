package com.mindteck.common.modules.IlepEvaluationForm.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SiteVisitDateChangeResponse extends AbstractView {

    private SiteVisitDateChangeResponseModel data;
}
