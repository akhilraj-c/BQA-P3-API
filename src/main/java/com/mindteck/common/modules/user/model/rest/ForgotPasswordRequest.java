package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ForgotPasswordRequest {
    @ApiModelProperty(example = "test@gmail.com", required = true)
    @NotBlank(message = "mailId is required")
    private String mailId;

    @ApiModelProperty(example = "24", required = true)
    @NotNull(message = "appId is required")
    private Long appId;
}
