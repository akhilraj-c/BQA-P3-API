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
@ApiModel(value = "forgotPasswordResponseModel")
public class ForgotPasswordResponseModel {

    @ApiModelProperty(value = "message", dataType = "String" ,example = "PLease check the mail")
    public String message;

    @ApiModelProperty(value = "otp", dataType = "String" ,example = "9846")
    public String otp;

}
