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
@ApiModel(value = "CreateUserResponseModel")
public class CreateUserResponseModel {

    @ApiModelProperty(value = "userId", example = "1", position = 1)
    private Long userId;

    @ApiModelProperty(value = "message", example = "User created successfully", position = 2)
    private String message;
}
