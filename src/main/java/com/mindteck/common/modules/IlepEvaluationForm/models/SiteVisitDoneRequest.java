package com.mindteck.common.modules.IlepEvaluationForm.models;

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
public class SiteVisitDoneRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(value = "reportFile", dataType = "String" ,example = "1- site visit done, 0- not done " , position = 4 , required = true)
    private Integer status;
}
