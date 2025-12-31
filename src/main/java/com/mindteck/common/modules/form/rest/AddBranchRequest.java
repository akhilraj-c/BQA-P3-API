package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddBranchRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "9 digit unique id" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long formUniqueId;

    @NotBlank(message = "building is required")
    @NotNull(message = "building is required")
    @ApiModelProperty(name = "building" , example = "Raj tower" , value = "Building name of the branch"
            , dataType = "String", position = 2, required = true)
    private String building;
    @NotBlank(message = "road is required")
    @NotNull(message = "road is required")
    @ApiModelProperty(name = "road" , example = "Kollam Bypass" , value = "road name to the branch"
            , dataType = "String", position = 3, required = true)
    private String road;
    @NotBlank(message = "block is required")
    @NotNull(message = "block is required")
    @ApiModelProperty(name = "block" , example = "Block No 10" , value = "Block number of the branch"
            , dataType = "String", position = 4, required = true)
    private String block;
}
