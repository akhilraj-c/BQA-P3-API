package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTransferProgressionStatus {
    /**
     * "accessAndAdmission": 1,
     * 		"creditAccumulation": 1,
     * 		"internalAndExternalCreditTransfer": 1,
     * 		"careerProgressionAndLearningPathways": 1,
     * 		"recognitionOfPriorLearning": 1,
     * 		"appealAgainstAccessAndTransfer": 0
     */

    @NotNull(message = "accessAndAdmission is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer accessAndAdmission;

    @NotBlank(message = "accessAndAdmissionComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String accessAndAdmissionComment;

    @NotNull(message = "creditAccumulation is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer creditAccumulation;

    @NotBlank(message = "creditAccumulationComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String creditAccumulationComment;

    @NotNull(message = "internalAndExternalCreditTransfer is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer internalAndExternalCreditTransfer;

    @NotBlank(message = "internalAndExternalCreditTransferComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String internalAndExternalCreditTransferComment;

    @NotNull(message = "careerProgressionAndLearningPathways is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer careerProgressionAndLearningPathways;

    @NotBlank(message = "careerProgressionAndLearningPathwaysComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String careerProgressionAndLearningPathwaysComment;

    @NotNull(message = "recognitionOfPriorLearning is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer recognitionOfPriorLearning;

    @NotBlank(message = "recognitionOfPriorLearningComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String recognitionOfPriorLearningComment;

    @NotNull(message = "appealAgainstAccessAndTransfer is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer appealAgainstAccessAndTransfer;

    @NotBlank(message = "accessAndAdmissionComment is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String appealAgainstAccessAndTransferComment;
}
