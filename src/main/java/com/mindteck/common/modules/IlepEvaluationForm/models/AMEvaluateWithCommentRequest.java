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
public class AMEvaluateWithCommentRequest extends AmApproveReportRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;


    @ApiModelProperty(name = "action", example = "Text ..", value = "action to set status", dataType = "Boolean",
            position = 3, required = true)
    private Boolean action;

    @ApiModelProperty(name = "actionHistory", example = "{}", value = "json value of the status", dataType = "String",
            position = 3, required = true)
    private String actionHistory;
}
