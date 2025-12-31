package com.mindteck.common.modules.feedback.models;

import com.mindteck.common.modules.IlepEvaluationForm.models.AmApproveReportRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DFOAdminCabinetApprovedRequest extends AmApproveReportRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "status is required")
    @ApiModelProperty(name = "status", example = "1 : Shared , 0 : Not shared", value = "shared status", dataType = "Integer",
            position = 2, required = true)
    private Integer status;




}
