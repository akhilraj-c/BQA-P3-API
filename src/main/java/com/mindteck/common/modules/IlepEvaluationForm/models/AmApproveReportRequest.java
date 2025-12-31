package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmApproveReportRequest {

    @ApiModelProperty(name = "comment", example = "comment", value = "comment",
            dataType = "String",
            position = 3)
    private String comment;


    @ApiModelProperty(name = "fileUrl" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 2, required = true)
    private String fileUrl;
}
