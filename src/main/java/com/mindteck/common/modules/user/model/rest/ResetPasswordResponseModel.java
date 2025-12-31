package com.mindteck.common.modules.user.model.rest;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "ResetPasswordResponseModel")
public class ResetPasswordResponseModel {


    @ApiModelProperty(value = "code", dataType = "Integer" ,example = "1")
    private Integer code;

    @ApiModelProperty(value = "message", dataType = "String" ,example = "password reset successfully")
    private String message;

}
