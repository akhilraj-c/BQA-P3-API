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
public class ApplicationManagerEvaluationRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty( required = true , value =  "formUniqueId" , dataType = "Long", position = 1)
    private Long formUniqueId;

    private InstitutionProfileStatus institutionProfileStatus;

    private QualityAssuranceSystemStatus qualityAssuranceSystemStatus;

    private AccessTransferProgressionStatus accessTransferProgressionStatus;

    private QualificationDevelopmentApprovalReviewStatus qualificationDevelopmentApprovalReviewStatus;

    private AssessmentDesignAndModerationStatus assessmentDesignAndModerationStatus;

    private CertificationStatus CertificationStatus;

    private SustainabilityAndContinuousQualityImprovementStatus sustainabilityAndContinuousQualityImprovementStatus;

    private Integer submissionStatus;

    @NotNull(message = "overAllEvaluationStatus is required")
    @ApiModelProperty( example = "1",required = true , value =  "overAllEvaluationStatus" , dataType = "Long", position = 1)
    private Integer overAllEvaluationStatus;

    @NotNull(message = "overAllEvaluationStatusComment is required")
    @ApiModelProperty( example = "sss" ,required = true , value =  "comment" , dataType = "String", position = 1)
    private String overAllEvaluationStatusComment;

    @ApiModelProperty(name = "evaluationDeadLine" , example = "1234567890123" , value = "resubmit date in epoc" ,dataType = "Long",position = 10)
    private Long evaluationDeadLine;

    @NotNull(message = "overAllVerificationStatus is required")
    @ApiModelProperty( example = "1",required = true , value =  "overAllVerificationStatus" , dataType = "Integer", position = 1)
    private Integer overAllVerificationStatus;

    @NotNull(message = "overAllVerificationStatusComment is required")
    @ApiModelProperty( example = "sss" ,required = true , value =  "overAllVerificationStatusComment" , dataType = "String", position = 1)
    private String overAllVerificationStatusComment;

    @ApiModelProperty(name = "verificationDeadLine" , example = "1234567890123" , value = "resubmit date in epoc" ,dataType = "Long",position = 10)
    private Long verificationDeadLine;

    @ApiModelProperty(name = "requestType" , example = "21" ,dataType = "Integer")
    private Integer requestType;

}
