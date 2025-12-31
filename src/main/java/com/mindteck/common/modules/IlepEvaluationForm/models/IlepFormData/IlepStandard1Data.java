package com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "IlepStandard1Data")
public class IlepStandard1Data {
    @ApiModelProperty(value = "actualAndTangibleNeedComments", dataType = "String" ,example = "Sampl name")
    private String actualAndTangibleNeedComments;

    @ApiModelProperty(value = "stakeholdersFeedbackComments", dataType = "String" ,example = "Sampl name")
    private String stakeholdersFeedbackComments;

    @ApiModelProperty(value = "careerProgressionAndLearningPathwaysComments", dataType = "String" ,example = "Sampl name")
    private String careerProgressionAndLearningPathwaysComments;

    @ApiModelProperty(value = "conditions", dataType = "String" ,example = "Sampl name")
    private String conditions;

    @ApiModelProperty(value = "suggestions", dataType = "String" ,example = "Sampl name")
    private String suggestions;

    @ApiModelProperty(value = "judgement", dataType = "Integer" ,example = "1-Met ,2-Partially Met,Not Met")
    private Integer judgement;

    @ApiModelProperty(value = "judgementHistory", dataType = "String" ,example = "Json value odf1-Met ,2-Partially Met,Not Met")
    private String judgementHistory;
}
