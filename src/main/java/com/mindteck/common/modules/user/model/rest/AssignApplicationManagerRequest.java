package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AssignApplicationManagerRequest {

    @ApiModelProperty(example = "12", required = true, value = "amUserId")
    @NotNull(message = "amUserId is required")
    private Long amUserId;

    @ApiModelProperty(example = "1", required = true, value = "formUniqueId")
    @NotNull(message = "formUniqueId is required")
    private Long formUniqueId;

    @ApiModelProperty(example = "1", required = true, value = "appId")
    @NotNull(message = "appId is required")
    private Long appId;
}
