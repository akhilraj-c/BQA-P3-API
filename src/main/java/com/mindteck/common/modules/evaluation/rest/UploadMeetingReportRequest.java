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
public class UploadMeetingReportRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "formUniqueId", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @NotBlank(message = "meetingReportDocumentAndComments is required")
    @NotNull(message = "meetingReportDocumentAndComments is required")
    @ApiModelProperty(name = "meetingReportDocumentAndComments" , example = "\"[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]\"" , value = "Stringified JSON Array" , dataType = "String"
            , position = 2 ,allowEmptyValue = false )
    private String meetingReportDocumentAndComments;
    @NotBlank(message = "meetingQuestionAndComments is required")
    @NotNull(message = "meetingQuestionAndComments is required")
    @ApiModelProperty(name = "meetingQuestionAndComments" , example = "\"[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]\"" , value = "Stringified JSON Array" , dataType = "String"
            , position = 3 ,allowEmptyValue = false )
    private String meetingQuestionAndComments;
    @NotBlank(message = "meetingEvidenceAndComments is required")
    @NotNull(message = "meetingEvidenceAndComments is required")
    @ApiModelProperty(name = "meetingEvidenceAndComments" , example = "\"[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]\"" , value = "Stringified JSON Array" , dataType = "String"
            , position = 4 ,allowEmptyValue = false )
    private String meetingEvidenceAndComments;

    @NotNull(message = "meetingType is required")
    @ApiModelProperty(name = "meetingType" , example = "1" , value = "1 - first meeting  , 2 - second meeting" , dataType = "Integer" ,
            position = 5, required = true)
    private Integer meetingType;


}
