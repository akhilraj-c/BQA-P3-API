package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrandAccessRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "Unique form id assigned for the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "userId is required")
    @ApiModelProperty(name = "userId", example = "15", value = "userId of the Ilep panel member", dataType = "Long",
            position = 2, required = true)
    private Long userId;


    @NotNull(message = "grandAccess is required")
    @Min(value = 0, message = "grandAccess should be 0 or 1")
    @Max(value = 1, message = "grandAccess should be 0 or 1")
    @ApiModelProperty(name = "grandAccess", example = "1", value = "0 - false \n 1 - true", dataType = "Integer",
            position = 3, required = true)
    private Integer grandAccess;
}
