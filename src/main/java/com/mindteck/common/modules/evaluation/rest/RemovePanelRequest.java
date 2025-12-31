package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemovePanelRequest {
    @NotNull(message = "panelId is required")
    @ApiModelProperty(name = "panelId", example = "123456789", value = "meetingId", dataType = "Long",
            position = 1, required = true)
    private Long panelId;

    @NotNull(message = "userId is required")
    @ApiModelProperty(name = "userId", example = "[10,15]", value = "List of userIds of  conflict raised ilep members", dataType = "Long",
            position = 2, required = true)
    private List<Long> userId;

    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "formUniqueId", dataType = "Long",
            position = 3, required = true)
    private Long formUniqueId;
}
