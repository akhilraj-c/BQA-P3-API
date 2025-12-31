package com.mindteck.common.modules.IlepEvaluationForm.models;

import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetILEPEvaluationDetailsResponseModel {

    private IlepStandard1Data ilepStandard1Data;

    private IlepStandard2Data ilepStandard2Data;

    private IlepStandard3Data ilepStandard3Data;

    private IlepStandard4Data ilepStandard4Data;

    private IlepStandard5Data ilepStandard5Data;

    @ApiModelProperty(value = "overAllJudgement", dataType = "Integer" ,example = "1")
    private Integer overAllJudgement;

    @ApiModelProperty(value = "overAllJudgementHistory", dataType = "String" ,example = "1")
    private String overAllJudgementHistory;

    @ApiModelProperty(value = "conditionFulfilmentDate", dataType = "Long" ,example = "1")
    private Long conditionFulfilmentDate;

    @ApiModelProperty(value = "partialMetCount", dataType = "Integer" ,example = "1")
    private Integer partialMetCount;

    @ApiModelProperty(value = "partialMetDate", dataType = "Long" ,example = "1600000")
    private Long partialMetDate;

    @ApiModelProperty(value = "partialMetComment", dataType = "String" ,example = "Hello comme")
    private String partialMetComment;

    @ApiModelProperty(value = "partialMetStatus", dataType = "Integer" ,example = "1")
    private Integer partialMetStatus;

    @ApiModelProperty(value = "amFeedbackComment", dataType = "String" ,example = "comme")
    private String amFeedbackComment;

    @ApiModelProperty(value = "amFeedbackActionHistory", dataType = "String" ,example = "comme")
    private String amFeedbackActionHistory;

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
