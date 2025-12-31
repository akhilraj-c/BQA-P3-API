package com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "IlepStandard4Data")
public class IlepStandard4Data {
    @ApiModelProperty(value = "assessmentDesignComments", dataType = "String" ,example = "Sampl name")
    private String assessmentDesignComments;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationofAssessmentComments", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationofAssessmentComments;

    @ApiModelProperty(value = "markingCriteriaComments", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaComments;


    @ApiModelProperty(value = "measuringTheAchievementofLearningOutcomesComments", dataType = "String" ,example = "Sampl name")
    private String measuringTheAchievementofLearningOutcomesComments;


    @ApiModelProperty(value = "feedbackToLearnersComments", dataType = "String" ,example = "Sampl name")
    private String feedbackToLearnersComments;


    @ApiModelProperty(value = "appealAgainstAssessmentResultComments", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAssessmentResultComments;


    @ApiModelProperty(value = "theIntegrityofAssessmentComments", dataType = "String" ,example = "Sampl name")
    private String theIntegrityofAssessmentComments;


    @ApiModelProperty(value = "conditions", dataType = "String" ,example = "Sampl name")
    private String conditions;

    @ApiModelProperty(value = "suggestions", dataType = "String" ,example = "Sampl name")
    private String suggestions;

    @ApiModelProperty(value = "judgement", dataType = "Integer" ,example = "1-Met ,2-Partially Met,Not Met")
    private Integer judgement;

    @ApiModelProperty(value = "judgementHistory", dataType = "String" ,example = "Json value odf1-Met ,2-Partially Met,Not Met")
    private String judgementHistory;
}
