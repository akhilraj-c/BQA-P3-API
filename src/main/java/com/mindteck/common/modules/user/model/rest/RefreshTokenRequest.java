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
public class RefreshTokenRequest {

    @ApiModelProperty(example = "asafs465asa", required = true, value = "token")
    @NotBlank(message = "token is required")
    private String token;

    @ApiModelProperty(example = "asafs465asa", required = true, value = "appId")
    @NotNull(message = "token is required")
    private Long appId;
}
