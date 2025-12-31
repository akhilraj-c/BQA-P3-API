package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForceChangeStatusRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty( required = true , value =  "formUniqueId" , dataType = "Long", position = 1)
    private Long formUniqueId;

    @NotNull(message = "status is required")
    @ApiModelProperty( required = true , value =  "status" , dataType = "Long", position = 1)
    private Integer status;

    @ApiModelProperty( required = true , value =  "status" , dataType = "Long", position = 1)
    private Integer subStatus;
}
