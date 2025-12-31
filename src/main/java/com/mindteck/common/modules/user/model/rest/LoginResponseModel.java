package com.mindteck.common.modules.user.model.rest;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "LoginResponseModel")
public class LoginResponseModel {


    @ApiModelProperty(value = "code", dataType = "Integer" ,example = "1")
    private Integer code;

    @ApiModelProperty(value = "appId", dataType = "Long" ,example = "1")
    private Long appId;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "1")
    private Long formUniqueId;

    @ApiModelProperty(value = "userId", dataType = "Long" ,example = "1")
    private Long userId;

    @ApiModelProperty(value = "username", dataType = "String" ,example = "Hadad")
    private String username;

    @ApiModelProperty(value = "userType", dataType = "Integer" ,example = "1")
    private Integer userType;

    @ApiModelProperty(value = "subType", dataType = "Integer" ,example = "1")
    private Integer subType;

    @ApiModelProperty(value = "token", dataType = "String" ,example = "xasafaefaefe")
    private String token;

    @ApiModelProperty(value = "refreshToken", dataType = "String" ,example = "xasafaefaefe")
    private String refreshToken;

    // Private key
    @ApiModelProperty(value = "bqaap", dataType = "String" ,example = "xasafaefaefe")
    private String bqaap;

    //AES key
    @ApiModelProperty(value = "bqae", dataType = "String" ,example = "xasafaefaefe")
    private String bqae;

    @ApiModelProperty(value = "roleIds", dataType = "Set<Map<String, Object>> " ,example = "[1,8,3]")
    private Set<Map<String, Object>> roleDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String institutionName;
}
