package com.mindteck.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetLogRequest extends AbstractListRequest{

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "9 digit unique id" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long formUniqueId;
}
