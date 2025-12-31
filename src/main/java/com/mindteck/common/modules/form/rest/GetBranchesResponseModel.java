package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBranchesResponseModel {


    @ApiModelProperty(name = "building" , example = "Raj tower" , value = "Building name of the branch"
            , dataType = "String", position = 1, required = true)
    private String building;

    @ApiModelProperty(name = "road" , example = "Kollam Bypass" , value = "road name to the branch"
            , dataType = "String", position = 2, required = true)
    private String road;

    @ApiModelProperty(name = "block" , example = "Block No 10" , value = "Block number of the branch"
            , dataType = "String", position = 3, required = true)
    private String block;
}
