package com.mindteck.common.modules.ilepAssigin.models;

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
public class DFOApprovePanelSepRequest {    @NotNull(message = "formUniqueId is required")
@ApiModelProperty(name = "formUniqueId", example = "123456789", value = "Unique form id assigned for the form", dataType = "Long",
        position = 1, required = true)
private Long formUniqueId;

    @NotNull(message = "userId is required")
    @ApiModelProperty(name = "userId", example = "1", value = "userId of the panel member", dataType = "Long",
            position = 1, required = true)
    private Long userId;

    @NotNull(message = "approveStatus is required")
    @ApiModelProperty(name = "approveStatus", example = "1", value = "0 - rejected \n 1 - approved", dataType = "Long",
            position = 1, required = true)
    private Integer approveStatus;
}
