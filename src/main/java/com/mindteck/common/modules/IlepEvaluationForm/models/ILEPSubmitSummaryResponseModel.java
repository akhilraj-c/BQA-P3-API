package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ILEPSubmitSummaryResponseModel {

    @ApiModelProperty(value = "message", example = "Summary updated successfully", dataType = "String", position = 1)
    private String message;
}
