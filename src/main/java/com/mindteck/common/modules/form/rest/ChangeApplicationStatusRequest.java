package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeApplicationStatusRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long formUniqueId;

    @NotNull(message = "applicationStatus is required")
    @ApiModelProperty(name = "applicationStatus" , example = "5" , value = "status of the application to update" , dataType = "Integer"
            , position = 2,allowEmptyValue = false , required = true)
    private Integer applicationStatus;
}
