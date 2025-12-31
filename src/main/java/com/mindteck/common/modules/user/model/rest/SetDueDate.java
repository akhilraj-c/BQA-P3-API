package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetDueDate {
    @NotNull(message = "action is required")
    @ApiModelProperty(name = "action" , example = "2" , value = "Type of the action  performed by the user", dataType = "Integer" ,required = true)
    private Integer action;
    @NotNull(message = "type is required")
    @ApiModelProperty(name = "type" , example = "10" , value = "Type of users ", dataType = "Integer" ,required = true)

    private Integer type;
    @NotNull(message = "noOfDays is required")
    @ApiModelProperty(name = "noOfDays" , example = "10" , value = "Number of days to do the action", dataType = "Integer" ,required = true)
    private Integer noOfDays;
}
