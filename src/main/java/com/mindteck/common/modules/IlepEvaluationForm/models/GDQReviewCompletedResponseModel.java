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
public class GDQReviewCompletedResponseModel {
    @ApiModelProperty(value = "message", example = "GDQ review Completed", dataType = "String", position = 1)
    private String message;
}
