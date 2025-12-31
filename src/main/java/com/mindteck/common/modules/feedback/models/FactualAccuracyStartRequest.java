package com.mindteck.common.modules.feedback.models;

import com.mindteck.common.modules.IlepEvaluationForm.models.AmApproveReportRequest;
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
public class FactualAccuracyStartRequest extends AmApproveReportRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "applicationStatus is required")
    @ApiModelProperty(name = "applicationStatus", example = "0", value = "1: not Listed, 2: Listed",
            dataType = "Integer",
            position = 3)
    private Integer applicationStatus;

    @NotNull(message = "instituteFactualAccuracyDeadLine is required")
    @ApiModelProperty(name = "instituteFactualAccuracyDeadLine" , example = "123123123" , value = "Dead line date and time in epoc" , dataType = "Long"
            , position = 4 ,allowEmptyValue = false )
    private Long instituteFactualAccuracyDeadLine;
}
