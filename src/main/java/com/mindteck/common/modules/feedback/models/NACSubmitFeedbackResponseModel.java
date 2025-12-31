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
public class NACSubmitFeedbackResponseModel {
    @ApiModelProperty(value = " 1 : success , 0 : failed", example = "1", allowableValues = "0,1", dataType = "Integer", position = 1)
    private Integer success;
}
