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
public class CreateAppealRequest extends AmApproveReportRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "inst_appeal is required")
    @ApiModelProperty(name = "inst_appeal", example = "0", value = "1: appeal, 0: no appeal",
            dataType = "Integer",
            position = 3)
    private Integer inst_appeal;
}
