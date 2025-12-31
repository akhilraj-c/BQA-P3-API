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
public class InstitutionUpdateCommentRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "comment", example = "Commented", value = "comments", dataType = "String",
            position = 2, required = true)
    private String comment;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 3 , required = true)
    private String docUrl;

    @ApiModelProperty(name = "additionalInfoStatus", example = "0", value = "0: not needed, 1: additional info required",
            dataType = "Integer",
            position = 4)
    private Integer additionalInfoStatus;

    @ApiModelProperty(name = "factualAccuracyStatus", example = "0", value = "0: not an error, 1: have error",
            dataType = "Integer",
            position = 5)
    private Integer factualAccuracyStatus;
}
