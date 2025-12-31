package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateMyFormResponseModel {

    @ApiModelProperty(value = " 1 : success , 0 : failed" , example = "1" , allowableValues = "0,1" ,dataType = "Integer" , position = 1)
    private Integer success;
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;


    @ApiModelProperty(name = "formId" , example = "1345" , value = "formId" , dataType = "Long"
            , position = 3, allowEmptyValue = false , required = true)
    private Long formId;



}
