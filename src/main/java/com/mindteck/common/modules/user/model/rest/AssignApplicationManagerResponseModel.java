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
@ApiModel(value = "AssignApplicationManagerResponseModel")
public class AssignApplicationManagerResponseModel {

    @ApiModelProperty(value = "message", dataType = "String" ,example = "Successfully assigned AM")
    public String message;
}
