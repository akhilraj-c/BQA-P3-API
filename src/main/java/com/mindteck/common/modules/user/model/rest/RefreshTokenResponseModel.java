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
@ApiModel(value = "RefreshTokenResponseModel")
public class RefreshTokenResponseModel {

    @ApiModelProperty(value = "token", dataType = "String" ,example = "1asdafae454aeg")
    private String token;

    @ApiModelProperty(value = "publicKey", dataType = "String" ,example = "publicKey")
    private String publicKey;
}
