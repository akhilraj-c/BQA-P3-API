package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AMPartiallyMetUpdateRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "partialMetDate is required")
    @ApiModelProperty(name = "partialMetDate", example = "123456789", value = "site visit date", dataType = "Long",
            position = 2, required = true)
    private Long partialMetDate;

    @NotNull(message = "partialMetComment is required")
    @NotBlank(message = "partialMetComment is required")
    @ApiModelProperty(value = "name of the institution name", dataType = "String" ,example = "University"  , position = 3 , required = true)
    private String partialMetComment;
}
