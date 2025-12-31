package com.mindteck.common.modules.IlepEvaluationForm.models;

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
public class InstitutionAcceptDateRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(value = "Accepted date for site visit", dataType = "Long" ,example = "1000145674123" , position = 1 , required = true)
    private Long acceptedDate;

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "unique id of the form" , dataType = "Long" ,
            position = 2, required = true)
    private Long formUniqueId;
}
