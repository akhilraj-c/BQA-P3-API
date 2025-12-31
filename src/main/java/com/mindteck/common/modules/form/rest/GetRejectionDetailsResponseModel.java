package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetRejectionDetailsResponseModel {
    @ApiModelProperty(value = " 1 : success , 0 : failed", example = "1", allowableValues = "0,1", dataType = "Integer", position = 1)
    private Integer success;

    @ApiModelProperty(value = " rejectionReason", example = "reason", dataType = "String", position = 2)
    private String rejectionReason;
}
