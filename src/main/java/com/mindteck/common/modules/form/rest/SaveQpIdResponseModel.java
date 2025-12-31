package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaveQpIdResponseModel {
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;
    @ApiModelProperty(name = "qpId" , example = "1345" , value = "qpId" , dataType = "String"
            , position = 3, allowEmptyValue = false , required = true)
    private String qpId;
}
