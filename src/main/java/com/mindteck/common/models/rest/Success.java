package com.mindteck.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Success {

    @ApiModelProperty(value = "code" , example = "20001" , dataType = "Integer" , position = 1)
    private Integer code;
}
