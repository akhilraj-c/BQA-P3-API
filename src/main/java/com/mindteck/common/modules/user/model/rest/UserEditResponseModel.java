package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditResponseModel {
    @ApiModelProperty(value = " 1 : success , 0 : failed" , example = "1" , allowableValues = "0,1" ,dataType = "Integer" , position = 1)
    private Integer success;
    @ApiModelProperty(name = "userId" , example = "5" , value = "Id of the edited user" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long userId;
}
