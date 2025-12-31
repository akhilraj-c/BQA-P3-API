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
@ApiModel(value = "applicationManagerEvaluationResponseModel")
public class ApplicationManagerEvaluationResponseModel {
    @ApiModelProperty(value = "message", dataType = "String" ,example = "Application Updated Successfully")
    public String message;

    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;

}
