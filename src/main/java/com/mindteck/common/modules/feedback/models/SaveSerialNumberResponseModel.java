package com.mindteck.common.modules.feedback.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaveSerialNumberResponseModel {

    @ApiModelProperty(value = " 1 : success , 0 : failed", example = "1", allowableValues = "0,1", dataType = "Integer", position = 1)
    private Integer success;
}
