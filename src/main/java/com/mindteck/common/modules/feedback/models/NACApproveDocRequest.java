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
public class NACApproveDocRequest extends AmApproveReportRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;


    @NotNull(message = "moreInfoStatus is required")
    @ApiModelProperty(name = "moreInfoStatus", example = "1", value = "0: not needed, 1: additional info required",
            dataType = "Integer",
            position = 4)
    private Integer moreInfoStatus;
}
