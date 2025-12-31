package com.mindteck.common.modules.feedback.models;

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
public class NACSubmitFeedbackRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "feedback", example = "Hello descrpition", value = "qac feedback decription", dataType = "String",
            position = 2)
    private String feedback;

    @NotNull(message = "feedbackFile is required")
    @NotBlank(message = "feedbackFile is required")
    @ApiModelProperty(name = "feedbackFile", example = "https://..", value = "qac feedback file url", dataType = "String",
            position = 3)
    private String feedbackFile;
}
