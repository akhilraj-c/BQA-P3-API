package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetApplicationDetailsRequest {

    @ApiModelProperty(example = "12", required = true)
    @NotNull(message = "formUniqueId is required")
    private Long formUniqueId;

    @ApiModelProperty(example = "1")
    @NotNull(message = "requestType is required")
    private Integer requestType;
}
