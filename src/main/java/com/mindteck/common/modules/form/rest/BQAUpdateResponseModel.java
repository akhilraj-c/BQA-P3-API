package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BQAUpdateResponseModel {

    @ApiModelProperty(value = "changedStatus" , example = "5" , dataType = "Integer" , position = 1)
    private Integer changedStatus;
}
