package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UploadMomResponseModel {
    @ApiModelProperty(value = " message", example = "MOM uploaded successfully", dataType = "String", position = 1)
    private String message;
}
