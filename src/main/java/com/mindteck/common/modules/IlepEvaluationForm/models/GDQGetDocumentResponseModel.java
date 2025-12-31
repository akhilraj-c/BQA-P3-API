package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GDQGetDocumentResponseModel {

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "1")
    private Long formUniqueId;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 2 , required = true)
    private String updatedForm;
}
