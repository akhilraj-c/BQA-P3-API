package com.mindteck.common.modules.feedback.models;

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
public class FactualAccuracyStartResponseModel {
    @ApiModelProperty(value = "message", example = "Factual accuracy Started", dataType = "String", position = 1)
    private String message;
}
