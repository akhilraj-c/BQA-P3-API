package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RegistrationResponseModel {

    @ApiModelProperty(value = "message", example = "Successfully registered")
    private String message;

    @ApiModelProperty(value = "code", example = "2001")
    private Integer code;

    @ApiModelProperty(value = "formUniqueId", example = "99556595959")
    private Long formUniqueId;

    @ApiModelProperty(name = "formId", example = "1" , value="Primary key of the application form")
    private Long formId;
}
