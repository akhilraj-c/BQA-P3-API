package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RevertConflictRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "meetingId", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;


    @ApiModelProperty(name = "ilepMemberId", example = "5", value = "userid of  conflict raised ilep member , need only in the case of ilep conflict", dataType = "Long",
            position = 2, required = true)
    private Long ilepMemberId;

    @ApiModelProperty(name = "type" , example = "1" ,  value = "1 - institute \n 2 - ilep"  , dataType = "Integer" ,
            position = 3 )
    private Integer type;
}
