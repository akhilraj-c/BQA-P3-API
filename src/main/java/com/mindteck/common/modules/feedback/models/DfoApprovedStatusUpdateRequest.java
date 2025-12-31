package com.mindteck.common.modules.feedback.models;

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
public class DfoApprovedStatusUpdateRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;
}
