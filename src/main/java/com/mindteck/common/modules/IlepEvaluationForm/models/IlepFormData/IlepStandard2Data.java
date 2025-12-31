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
@ApiModel(value = "IlepStandard2Data")
public class IlepStandard2Data {

    @ApiModelProperty(value = "qualificationlicenseAndApproval", dataType = "String" ,example = "Sampl name")
    private String qualificationlicenseAndApproval;

    @ApiModelProperty(value = "qualificationAccessAndTransferComments", dataType = "String" ,example = "Sampl name")
    private String qualificationAccessAndTransferComments;

    @ApiModelProperty(value = "qualificationGraduationRequirementsComments", dataType = "String" ,example = "Sampl name")
    private String qualificationGraduationRequirementsComments;

    @ApiModelProperty(value = "qualificationAlignmentAndBenchmarkingComments", dataType = "String" ,example = "Sampl name")
    private String qualificationAlignmentAndBenchmarkingComments;

    @ApiModelProperty(value = "qualificationInternalAndExternalEvaluationAndReviewComments", dataType = "String" ,example = "Sampl name")
    private String qualificationInternalAndExternalEvaluationAndReviewComments;

    @ApiModelProperty(value = "mappingAndConfirmationProcessesComments", dataType = "String" ,example = "Sampl name")
    private String mappingAndConfirmationProcessesComments;

    @ApiModelProperty(value = "programmeAccreditationComments", dataType = "String" ,example = "Sampl name")
    private String programmeAccreditationComments;

    @ApiModelProperty(value = "conditions", dataType = "String" ,example = "Sampl name")
    private String conditions;

    @ApiModelProperty(value = "suggestions", dataType = "String" ,example = "Sampl name")
    private String suggestions;

    @ApiModelProperty(value = "judgement", dataType = "Integer" ,example = "1-Met ,2-Partially Met,Not Met")
    private Integer judgement;

    @ApiModelProperty(value = "judgementHistory", dataType = "String" ,example = "Json value odf1-Met ,2-Partially Met,Not Met")
    private String judgementHistory;
}
