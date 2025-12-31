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
@ApiModel(value = "IlepStandard3Data")
public class IlepStandard3Data {
    @ApiModelProperty(value = "qualificationTitleComments", dataType = "String", example = "Sampl name")
    private String qualificationTitleComments;

    @ApiModelProperty(value = "learningOutcomesComments", dataType = "String", example = "Sampl name")
    private String learningOutcomesComments;

    @ApiModelProperty(value = "qualificationAttendanceAndDeliveryModesComments", dataType = "String", example = "Sampl name")
    private String qualificationAttendanceAndDeliveryModesComments;

    @ApiModelProperty(value = "qualificationStructureAndDurationComments", dataType = "String", example = "Sampl name")
    public String qualificationStructureAndDurationComments;

    @ApiModelProperty(value = "qualificationContentComments", dataType = "String", example = "Sampl name")
    private String qualificationContentComments;

    @ApiModelProperty(value = "progressionAndFlowComments", dataType = "String", example = "Sampl name")
    private String progressionAndFlowComments;

    @ApiModelProperty(value = "unitInformationComments", dataType = "String", example = "Sampl name")
    private String unitInformationComments;

    @ApiModelProperty(value = "learningResourcesAndLearnerSupportComments", dataType = "String", example = "Sampl name")
    public String learningResourcesAndLearnerSupportComments;

    @ApiModelProperty(value = "learnersWithSpecialNeedsComments", dataType = "String", example = "Sampl name")
    private String learnersWithSpecialNeedsComments;

    @ApiModelProperty(value = "conditions", dataType = "String" ,example = "Sampl name")
    private String conditions;

    @ApiModelProperty(value = "suggestions", dataType = "String" ,example = "Sampl name")
    private String suggestions;

    @ApiModelProperty(value = "judgement", dataType = "Integer" ,example = "1-Met ,2-Partially Met,Not Met")
    private Integer judgement;

    @ApiModelProperty(value = "judgementHistory", dataType = "String" ,example = "Json value odf1-Met ,2-Partially Met,Not Met")
    private String judgementHistory;
}
