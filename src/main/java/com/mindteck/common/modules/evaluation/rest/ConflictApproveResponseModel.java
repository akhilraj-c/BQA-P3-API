package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ConflictApproveResponseModel {

    @ApiModelProperty(value = "message" , example = "conflict status updated" ,dataType = "String" , position = 1)
    private String message;
}
