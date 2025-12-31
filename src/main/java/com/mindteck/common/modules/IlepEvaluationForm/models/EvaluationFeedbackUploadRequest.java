package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EvaluationFeedbackUploadRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "unique id of the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;
    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 2 , required = true)
    private String docUrl;
    @ApiModelProperty(name = "feedbackComment" , example = "comment with version support" , value = "Comment added for feedback" , dataType = "Long" ,
            position = 3, required = true)
    private String feedbackComment;
    @NotNull(message = "type is required")
    @Min(1)
    @Max(3)
    @ApiModelProperty(name ="type" , example = "1",value = "1 - DFO Chief \n 2 - GDQ AC \n 3 - QAC" , required = true, position = 4 , allowableValues = "1,2,3")
    private Integer type;
}
