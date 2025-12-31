package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddRegistrationDateRequest {

    @NotNull(message = "submissionDate is required")
    @ApiModelProperty(name = "submissionDate" , example = "1231231231231" , value = "submission date in epoch" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long submissionDate;

    @NotNull(message = "submissionEndDate is required")
    @ApiModelProperty(name = "submissionEndDate" , example = "1231231231231" , value = "submission end date in epoch" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long submissionEndDate;
}
