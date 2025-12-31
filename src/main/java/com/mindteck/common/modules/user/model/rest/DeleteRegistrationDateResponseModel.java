package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DeleteRegistrationDateResponseModel  {


    @ApiModelProperty(value = " 1 : success , 0 : failed" , example = "1" , allowableValues = "0,1" ,dataType = "Integer" , position = 1)
    private Integer success;
}
