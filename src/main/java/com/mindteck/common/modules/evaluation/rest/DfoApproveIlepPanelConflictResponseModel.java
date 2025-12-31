package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DfoApproveIlepPanelConflictResponseModel {
    @ApiModelProperty(value = " message" , example = "DFO updated the panel Status" ,dataType = "String" , position = 1)
    private String message;
}
