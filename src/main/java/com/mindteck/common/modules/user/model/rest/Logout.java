package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Logout {

    @ApiModelProperty(value = "Status message", example = "Success", dataType = "String", position = 1)
    private String message;
}
