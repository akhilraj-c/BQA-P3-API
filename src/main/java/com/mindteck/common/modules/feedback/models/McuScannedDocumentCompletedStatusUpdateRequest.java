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
public class McuScannedDocumentCompletedStatusUpdateRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "scannedDocument", example = "https///sasa", value = "url of document", dataType = "String",
            position = 2)
    private String scannedDocument;

    @ApiModelProperty(name = "comment", example = "test comment", value = "comment for upload", dataType = "String",
            position = 2)
    private String comment;
}
