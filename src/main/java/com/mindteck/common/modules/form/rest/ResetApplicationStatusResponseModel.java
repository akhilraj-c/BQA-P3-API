package com.mindteck.common.modules.form.rest;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class ResetApplicationStatusResponseModel {

    @ApiModelProperty(value = " 1 : success , 0 : failed", example = "1", allowableValues = "0,1", dataType = "Integer", position = 1)
    private Integer success;
}
