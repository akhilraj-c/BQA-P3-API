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
public class McoCommentRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "comment is required")
    @ApiModelProperty(name = "comment", example = "0", value = "comment",
            dataType = "String",
            position = 3)
    private String comment;

    @NotNull(message = "fileUrl is required")
    @ApiModelProperty(name = "fileUrl", example = "0", value = "fileUrl",
            dataType = "String",
            position = 3)
    private String fileUrl;
}
