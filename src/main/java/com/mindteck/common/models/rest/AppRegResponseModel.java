package com.mindteck.common.models.rest;

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
@ApiModel(value = "AppReg")
public class AppRegResponseModel {

    @ApiModelProperty(value = "1", dataType = "Integer" ,example = "1")
    public Long appId;

}
