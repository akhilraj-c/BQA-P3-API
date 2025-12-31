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
public class LoginRequest {

    @ApiModelProperty(example = "testuser@gmail.com", required = true)
    @NotBlank(message = "username is required")
    private String username;

    @ApiModelProperty(example = "1234", required = true)
    @NotBlank(message = "password is required")
    private String password;

    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "appId is required")
    private Long appId;
    @ApiModelProperty(example = "zjdalfakdfalfeirjovcvsaeew135skdfala" ,name = "publicKey",
            value = "publicKey for encrypting AES key")
    private String publicKey;
}
