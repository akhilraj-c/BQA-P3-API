package com.mindteck.common.modules.revalidation.model;


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
public class EnforceRevalidationRequest {
    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "formUniqueId is required")
    private Long formUniqueId;

    private int isRevalidation = 1;
    private String plannedDate;
}
