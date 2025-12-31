package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "oldPassword is required")
    @ApiModelProperty(name = "oldPassword", example = "1234", value = "old login password", dataType = "String",
            position = 1, required = true)
    private String oldPassword;
    @NotBlank(message = "newPassword is required")
    @ApiModelProperty(name = "newPassword", example = "qwe@123", value = "new login password", dataType = "String",
            position = 2, required = true)
    private String newPassword;
}
