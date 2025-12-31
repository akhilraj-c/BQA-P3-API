package com.mindteck.common.modules.feedback.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FactualAccuracyReportRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotBlank(message = "reportFile is required")
    @ApiModelProperty(name = "reportFile", example = "htpss.///", value = "reportFile", dataType = "String",
            position = 1, required = true)
    private String reportFile;

    @ApiModelProperty(name = "comment", example = "0", value = "comment",
            dataType = "String",
            position = 3)
    private String comment;

    @NotNull(message = "status is required")
    @ApiModelProperty(name = "status", example = "1 : Listed , 0 : Not listed", value = "factual accuracy status", dataType = "Integer",
            position = 3, required = true)
    private Integer status;
}
