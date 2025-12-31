package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class InstituteUploadAgendaRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "unique id of the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 2 , required = true)
    private String agendaDocUrl;

    @ApiModelProperty(value = "comment", dataType = "String" ,example = "sample comment" , position = 2 , required = true)
    private String comment;
}
