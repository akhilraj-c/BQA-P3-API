package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IlepEvaluateResponseModel {
    @ApiModelProperty(value = "message", example = "ILEP evaluation updated", dataType = "String", position = 1)
    private String message;
}
