package com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData;

import com.mindteck.common.models.rest.AbstractView;
import com.mindteck.common.modules.IlepEvaluationForm.models.AmEvaluatedUploadedDocResponseModel;
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
public class AmEvaluatedUploadedDocResponse extends AbstractView {
    private AmEvaluatedUploadedDocResponseModel data;
}
