package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DFOChiefSignRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "sign" , example = "1" , value = " 1 - true \n 0 - false" , dataType = "Integer"
            , position = 2,allowEmptyValue = false , required = true)
    private Integer sign;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 3 , required = true)
    private String docUrl;

    @ApiModelProperty(name = "comment", example = "0", value = "comment",
            dataType = "String",
            position = 3)
    private String comment;
}
