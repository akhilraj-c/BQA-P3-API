package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordResponseModel {

    @ApiModelProperty(value = " 1 : success , 0 : failed" , example = "1" , allowableValues = "0,1" ,dataType = "Integer" , position = 1)
    private Integer success;
}
