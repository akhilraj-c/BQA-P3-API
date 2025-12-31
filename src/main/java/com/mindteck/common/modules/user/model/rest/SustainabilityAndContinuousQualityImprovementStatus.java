package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SustainabilityAndContinuousQualityImprovementStatus {
    /**
     * "institutionQualityAssuranceSystem": 1,
     * 		"continuousImprovementOfInstitutionQualityAssuranceSystem": 1,
     * 		"riskAndCrisisManagement": 1
     */
    @NotNull(message = "institutionQualityAssuranceSystem is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionQualityAssuranceSystem;

    @NotNull(message = "institutionQualityAssuranceSystemComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionQualityAssuranceSystemComment;

    @NotNull(message = "continuousImprovementOfInstitutionQualityAssuranceSystem is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer continuousImprovementOfInstitutionQualityAssuranceSystem;

    @NotNull(message = "institutionQualityAssuranceSystemComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String continuousImprovementOfInstitutionQualityAssuranceSystemComment;

    @NotNull(message = "riskAndCrisisManagement is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer riskAndCrisisManagement;

    @NotNull(message = "institutionQualityAssuranceSystemComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String riskAndCrisisManagementComment;
}
