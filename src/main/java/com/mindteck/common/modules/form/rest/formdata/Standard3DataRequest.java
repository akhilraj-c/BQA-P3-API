package com.mindteck.common.modules.form.rest.formdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "Standard3Data")
public class Standard3DataRequest {
/*
    @ApiModelProperty(value = "AssessmentDesignDesc", dataType = "String" ,example = "Sampl name")
    private String assessmentDesignDesc;

    @ApiModelProperty(value = "AssessmentDesignFile", dataType = "String" ,example = "Sampl name")
    private String assessmentDesignFile;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentDesc", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationOfAssessmentDesc;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentFile", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationOfAssessmentFile;

    @ApiModelProperty(value = "markingCriteriaDesc", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaDesc;

    @ApiModelProperty(value = "markingCriteriaFile", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaFile;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesDesc", dataType = "String" ,example = "Sampl name")
    public String measuringTheAchievementOfLearningOutcomesDesc;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesFile", dataType = "String" ,example = "Sampl name")
    public String measuringTheAchievementOfLearningOutcomesFile;

    @ApiModelProperty(value = "FeedbackToLearnersDesc", dataType = "String" ,example = "Sampl name")
    private String feedbackToLearnersDesc;

    @ApiModelProperty(value = "FeedbackToLearnersFile", dataType = "String" ,example = "Sampl name")
    private String feedbackToLearnersFile;

    @ApiModelProperty(value = "approvalOfAssessmentResultsDesc", dataType = "String" ,example = "Sampl name")
    private String approvalOfAssessmentResultsDesc;

    @ApiModelProperty(value = "approvalOfAssessmentResultsFile", dataType = "String" ,example = "Sampl name")
    private String approvalOfAssessmentResultsFile;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsDesc", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAssessmentResultsDesc;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsFile", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAssessmentResultsFile;

    @ApiModelProperty(value = "integrityOfAssessmentDesc", dataType = "String" ,example = "Sampl name")
    public String integrityOfAssessmentDesc;

    @ApiModelProperty(value = "integrityOfAssessmentFile", dataType = "String" ,example = "Sampl name")
    private String integrityOfAssessmentFile;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsDesc", dataType = "String" ,example = "Sampl name")
    private String securityOfAssessmentDocumentsAndRecordsDesc;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsFile", dataType = "String" ,example = "Sampl name")
    private String securityOfAssessmentDocumentsAndRecordsFile;

 */

    @ApiModelProperty(value = "AssessmentDesignStatusComment", dataType = "String" ,example = "Sampl name")
    private String AssessmentDesignStatusComment;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentStatusComment", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationOfAssessmentStatusComment;

    @ApiModelProperty(value = "markingCriteriaStatusComment", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaStatusComment;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesStatusComment", dataType = "String" ,example = "Sampl name")
    private String measuringTheAchievementOfLearningOutcomesStatusComment;

    @ApiModelProperty(value = "FeedbackToLearnersStatusComment", dataType = "String" ,example = "Sampl name")
    private String FeedbackToLearnersStatusComment;

    @ApiModelProperty(value = "approvalOfAssessmentResultsStatusComment", dataType = "String" ,example = "Sampl name")
    private String approvalOfAssessmentResultsStatusComment;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsStatusComment", dataType = "String" ,example = "Sampl name")
    public String appealAgainstAssessmentResultsStatusComment;

    @ApiModelProperty(value = "integrityOfAssessmentStatusComment", dataType = "String" ,example = "Sampl name")
    public String integrityOfAssessmentStatusComment;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsStatusComment", dataType = "String" ,example = "Sampl name")
    private String securityOfAssessmentDocumentsAndRecordsStatusComment;

}
