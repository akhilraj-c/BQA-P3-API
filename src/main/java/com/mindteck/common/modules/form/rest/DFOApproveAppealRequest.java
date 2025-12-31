package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DFOApproveAppealRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "approve" , example = "1" , value = " 1 - true \n 0 - false" , dataType = "Integer"
            , position = 2,allowEmptyValue = false , required = true)
    private Integer approve;


    @ApiModelProperty(name = "comment", example = "0", value = "comment",
            dataType = "String",
            position = 3)
    private String comment;


    @ApiModelProperty(name = "fileUrl", example = "0", value = "fileUrl",
            dataType = "String",
            position = 3)
    private String fileUrl;
}
