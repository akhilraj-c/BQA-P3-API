package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class AMUpdateDateRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "visitDate is required")
    @ApiModelProperty(name = "visitDate", example = "123456789", value = "site visit date", dataType = "Long",
            position = 2, required = true)
    private Long visitDate;


    @NotNull(message = "action is required")
    @ApiModelProperty(name = "action", example = "1", value = "action", dataType = "Long",
            position = 2, required = true)
    private Integer action;
}
