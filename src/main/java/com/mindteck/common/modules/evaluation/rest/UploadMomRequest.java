package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadMomRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "formUniqueId", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotBlank(message = "firstMeetingDocumentsAndComments is required")
    @NotNull(message = "firstMeetingDocumentsAndComments is required")
    @ApiModelProperty(name = "firstMeetingDocumentsAndComments" , example = "\"[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]\"" , value = "Stringified JSON Array" , dataType = "String"
            , position = 2 ,allowEmptyValue = false )
    private String firstMeetingDocumentsAndComments;

    @NotNull(message = "deadLine is required")
    @ApiModelProperty(name = "deadLine" , example = "123123123" , value = "date and time in epoc" , dataType = "Long"
            , position = 3 ,allowEmptyValue = false )
    private Long deadLine;
}
