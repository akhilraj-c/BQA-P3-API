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
public class AssessmentDesignAndModerationStatus {
    /**
     * "Assessment Design": 1,
     * 		"internalAndExternalVerificationAndModerationAssessment": 1,
     * 		"markingCriteria": 1,
     * 		"measuringTheAchievementOfLearningOutcomes": 1,
     * 		"feedbackToLearners": 1,
     * 		"approvalPOfAssessmentResults": 0,
     * 		"appealAgainstAssessmentResults": 1,
     * 		"integrityOfAssessment": 1,
     * 		"securityOfAssessmentDocumentsAndRecords": 0
     */
    @NotNull(message = "assessmentDesign is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer assessmentDesign;

    @NotBlank(message = "assessmentDesignComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String assessmentDesignComment;

    @NotNull(message = "internalAndExternalVerificationAndModerationAssessment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer internalAndExternalVerificationAndModerationAssessment;

    @NotBlank(message = "internalAndExternalVerificationAndModerationAssessmentComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String internalAndExternalVerificationAndModerationAssessmentComment;

    @NotNull(message = "markingCriteria is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer markingCriteria;

    @NotBlank(message = "markingCriteriaComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String markingCriteriaComment;

    @NotNull(message = "measuringTheAchievementOfLearningOutcomes is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer measuringTheAchievementOfLearningOutcomes;

    @NotBlank(message = "measuringTheAchievementOfLearningOutcomesComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String measuringTheAchievementOfLearningOutcomesComment;

    @NotNull(message = "feedbackToLearners is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer feedbackToLearners;

    @NotBlank(message = "feedbackToLearnersComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String feedbackToLearnersComment;

    @NotNull(message = "approvalPOfAssessmentResults is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer approvalOfAssessmentResults;

    @NotBlank(message = "approvalOfAssessmentResultsComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String approvalOfAssessmentResultsComment;

    @NotNull(message = "appealAgainstAssessmentResults is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer appealAgainstAssessmentResults;

    @NotBlank(message = "appealAgainstAssessmentResultsComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String appealAgainstAssessmentResultsComment;

    @NotNull(message = "integrityOfAssessment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer integrityOfAssessment;

    @NotBlank(message = "integrityOfAssessmentComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String integrityOfAssessmentComment;

    @NotNull(message = "securityOfAssessmentDocumentsAndRecords is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer securityOfAssessmentDocumentsAndRecords;

    @NotBlank(message = "assessmentDesignComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String securityOfAssessmentDocumentsAndRecordsComment;
}
