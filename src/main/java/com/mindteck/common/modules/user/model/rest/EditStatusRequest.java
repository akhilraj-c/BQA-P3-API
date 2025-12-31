package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditStatusRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "statusId", example = "123456789", value = " id of the status", dataType = "Long",
            position = 1, required = true)
    private Long statusId;

    @NotBlank(message = "englishText is required")
    @ApiModelProperty(name = "englishText", example = "Hello", value = "status desc in eng", dataType = "String",
            position = 2, required = true)
    private String englishText;

    @NotBlank(message = "arabText is required")
    @ApiModelProperty(name = "arabText", example = "صباح الخير.", value = "status desc in arab", dataType = "String",
            position = 3, required = true)
    private String arabText;
}
