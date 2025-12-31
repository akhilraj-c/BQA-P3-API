package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ILEPSubmitSummaryRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "formUniqueId" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "overAllJudgement is required")
    @ApiModelProperty(name = "overAllJudgement" , example = "1" , value = "overAllJudgement" , dataType = "Integer" ,
            position = 2, required = true)
    private Integer overAllJudgement;

    @NotNull(message = "overAllJudgementHistory is required")
    @ApiModelProperty(name = "overAllJudgementHistory" , example = "1" , value = "overAllJudgement" , dataType = "String" ,
            position = 2, required = true)
    private String overAllJudgementHistory;

    @NotNull(message = "conditionFulfilmentDate is required")
    @ApiModelProperty(name = "conditionFulfilmentDate" , example = "1" , value = "conditionFulfilmentDate" , dataType = "Integer" ,
            position = 3, required = true)
    private Long conditionFulfilmentDate;

    private Integer submissionStatus;

    @ApiModelProperty(name = "panelId" , example = "123456789" , value = "panelId" , dataType = "Long" ,
            position = 1, required = true)
    private Long panelId;

    @ApiModelProperty(example = "any thing is string", value =  "any this string" , name = "dec1" ,dataType = "String", position = 10)
    private String dec1;
    @ApiModelProperty(example = "any thing is string", value =  "any thing is string" , name = "dec2" ,dataType = "String", position = 11)
    private String dec2;
    @ApiModelProperty(example = "any thing is string", value =  "any thing is string" , name = "dec3" ,dataType = "String", position = 12)
    private String dec3;
    @ApiModelProperty(example = "any thing is string", value =  "any thing is string" , name = "draft" ,dataType = "String", position = 13)
    private String draft;
    @ApiModelProperty(example = "1", value =  "Some thing in integer" , name = "condfil" ,dataType = "Integer", position = 14)
    private Integer condfil;

}
