package com.mindteck.common.modules.ilepAssigin.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class RemoveIlepSepRequest {
    @NotNull(message = "userId is required")
    @ApiModelProperty(name = "userId", example = "15", value = "List of userIds of  conflict raised ilep members", dataType = "Long",
            position = 1, required = true)
    private List<Long> userId;

    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "formUniqueId", dataType = "Long",
            position = 2, required = true)
    private Long formUniqueId;
}
