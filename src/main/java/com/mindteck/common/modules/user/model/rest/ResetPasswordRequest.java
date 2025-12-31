package com.mindteck.common.modules.user.model.rest;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResetPasswordRequest {

    @ApiModelProperty(example = "sample#gmail.com", required = true)
    @NotBlank(message = "emailId is required")
    private String emailId;

    @ApiModelProperty(example = "password", required = true)
    @NotBlank(message = "password is required")
    private String password;

    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "appId is required")
    private Long appId;

    @ApiModelProperty(example = "456789", required = true)
    @NotNull(message = "otpCode is required")
    private String otpCode;
}
