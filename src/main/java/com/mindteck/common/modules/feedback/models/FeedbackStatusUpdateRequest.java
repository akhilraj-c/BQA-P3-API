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
public class FeedbackStatusUpdateRequest extends AmApproveReportRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "userType is required")
    @ApiModelProperty(name = "userType", example = "0", value = "3: DFO Director updated the status as QAC approved," +
            " 4: DFO chief approved the report, 6: GDQ AC Committie approved the report",
            dataType = "Integer",
            position = 3)
    private Integer userType;
}
