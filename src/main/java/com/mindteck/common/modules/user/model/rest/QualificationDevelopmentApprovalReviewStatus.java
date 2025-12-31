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
public class QualificationDevelopmentApprovalReviewStatus {
    /**
     * "justificationOfNeed": 1,
     * 		"qualificationDesign": 1,
     * 		"qualificationCompliance": 1,
     * 		"learningRecoursesAndLearnersSupport": 1,
     * 		"qualificationInternalApproval": 1,
     * 		"qualificationInternalAndExternalEvaluationReview": 0
     */
    @NotNull(message = "justificationOfNeed is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer justificationOfNeed;

    @NotBlank(message = "justificationOfNeedComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String justificationOfNeedComment;

    @NotNull(message = "qualificationDesign is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer qualificationDesign;

    @NotBlank(message = "qualificationDesignComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String qualificationDesignComment;

    @NotNull(message = "qualificationCompliance is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer qualificationCompliance;

    @NotBlank(message = "qualificationComplianceComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String qualificationComplianceComment;

    @NotNull(message = "learningRecoursesAndLearnersSupport is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer learningRecoursesAndLearnersSupport;

    @NotBlank(message = "learningRecoursesAndLearnersSupportComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String learningRecoursesAndLearnersSupportComment;

    @NotNull(message = "qualificationInternalApproval is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer qualificationInternalApproval;

    @NotBlank(message = "qualificationInternalApprovalComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String qualificationInternalApprovalComment;

    @NotNull(message = "qualificationInternalAndExternalEvaluationReview is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer qualificationInternalAndExternalEvaluationReview;

    @NotBlank(message = "qualificationInternalAndExternalEvaluationReviewComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String qualificationInternalAndExternalEvaluationReviewComment;
}
