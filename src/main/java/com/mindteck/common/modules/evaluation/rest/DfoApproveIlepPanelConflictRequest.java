package com.mindteck.common.modules.evaluation.rest;

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
public class DfoApproveIlepPanelConflictRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "Unique form id assigned for the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "approveStatus is required")
    @ApiModelProperty(name = "approveStatus", example = "1", value = "0 - rejected \n 1 - approved", dataType = "Long",
            position = 1, required = true)
    private Integer approveStatus;
}
