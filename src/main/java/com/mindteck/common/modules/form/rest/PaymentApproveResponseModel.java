package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentApproveResponseModel {
    @ApiModelProperty(value = "message" , example = "payment approved" , dataType = "String" , position = 1)
    private String message;
}
