package com.mindteck.common.modules.evaluation.rest;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CreateILEPMemberResponseModel {

    @ApiModelProperty(value = "panelId" , example = "1" , dataType = "Long" , position = 1)
    private Long panelId;

    @ApiModelProperty(value = "message" , example = "panel created successfully" , dataType = "String" , position = 1)
    private String message;
}
