package com.mindteck.common.modules.user.model.rest.formdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "Standard3Data")
public class Standard3Data {

    @ApiModelProperty(value = "AssessmentDesignDesc", dataType = "String" ,example = "Sampl name")
    private String AssessmentDesignDesc;

    @ApiModelProperty(value = "AssessmentDesignFile", dataType = "String" ,example = "Sampl name")
    private String AssessmentDesignFile;

    @ApiModelProperty(value = "AssessmentDesignStatus", dataType = "Integer" ,example = "1")
    private Integer AssessmentDesignStatus;

    @ApiModelProperty(value = "AssessmentDesignStatusComment", dataType = "String" ,example = "Sampl name")
    private String AssessmentDesignStatusComment;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentDesc", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationOfAssessmentDesc;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentFile", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationOfAssessmentFile;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentStatus", dataType = "Integer" ,example = "1")
    private Integer internalAndExternalVerificationAndModerationOfAssessmentStatus;

    @ApiModelProperty(value = "internalAndExternalVerificationAndModerationOfAssessmentStatusComment", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalVerificationAndModerationOfAssessmentStatusComment;

    @ApiModelProperty(value = "markingCriteriaDesc", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaDesc;

    @ApiModelProperty(value = "markingCriteriaFile", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaFile;

    @ApiModelProperty(value = "markingCriteriaStatus", dataType = "Integer" ,example = "1")
    private Integer markingCriteriaStatus;

    @ApiModelProperty(value = "markingCriteriaStatusComment", dataType = "String" ,example = "Sampl name")
    private String markingCriteriaStatusComment;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesDesc", dataType = "String" ,example = "Sampl name")
    public String measuringTheAchievementOfLearningOutcomesDesc;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesFile", dataType = "String" ,example = "Sampl name")
    public String measuringTheAchievementOfLearningOutcomesFile;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesStatus", dataType = "Integer" ,example = "1")
    private Integer measuringTheAchievementOfLearningOutcomesStatus;

    @ApiModelProperty(value = "measuringTheAchievementOfLearningOutcomesStatusComment", dataType = "String" ,example = "Sampl name")
    private String measuringTheAchievementOfLearningOutcomesStatusComment;

    @ApiModelProperty(value = "FeedbackToLearnersDesc", dataType = "String" ,example = "Sampl name")
    private String FeedbackToLearnersDesc;

    @ApiModelProperty(value = "FeedbackToLearnersFile", dataType = "String" ,example = "Sampl name")
    private String FeedbackToLearnersFile;

    @ApiModelProperty(value = "FeedbackToLearnersStatus", dataType = "Integer" ,example = "1")
    private Integer FeedbackToLearnersStatus;

    @ApiModelProperty(value = "FeedbackToLearnersStatusComment", dataType = "String" ,example = "Sampl name")
    private String FeedbackToLearnersStatusComment;

    @ApiModelProperty(value = "approvalOfAssessmentResultsDesc", dataType = "String" ,example = "Sampl name")
    private String approvalOfAssessmentResultsDesc;

    @ApiModelProperty(value = "approvalOfAssessmentResultsFile", dataType = "String" ,example = "Sampl name")
    private String approvalOfAssessmentResultsFile;

    @ApiModelProperty(value = "approvalOfAssessmentResultsStatus", dataType = "Integer" ,example = "1")
    private Integer approvalOfAssessmentResultsStatus;

    @ApiModelProperty(value = "approvalOfAssessmentResultsStatusComment", dataType = "String" ,example = "Sampl name")
    private String approvalOfAssessmentResultsStatusComment;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsDesc", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAssessmentResultsDesc;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsFile", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAssessmentResultsFile;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsStatus", dataType = "Integer" ,example = "1")
    public Integer appealAgainstAssessmentResultsStatus;

    @ApiModelProperty(value = "appealAgainstAssessmentResultsStatusComment", dataType = "String" ,example = "Sampl name")
    public String appealAgainstAssessmentResultsStatusComment;

    @ApiModelProperty(value = "integrityOfAssessmentDesc", dataType = "String" ,example = "Sampl name")
    public String integrityOfAssessmentDesc;

    @ApiModelProperty(value = "integrityOfAssessmentFile", dataType = "String" ,example = "Sampl name")
    private String integrityOfAssessmentFile;

    @ApiModelProperty(value = "integrityOfAssessmentStatus", dataType = "Integer" ,example = "1")
    public Integer integrityOfAssessmentStatus;

    @ApiModelProperty(value = "integrityOfAssessmentStatusComment", dataType = "String" ,example = "Sampl name")
    public String integrityOfAssessmentStatusComment;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsDesc", dataType = "String" ,example = "Sampl name")
    private String securityOfAssessmentDocumentsAndRecordsDesc;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsFile", dataType = "String" ,example = "Sampl name")
    private String securityOfAssessmentDocumentsAndRecordsFile;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsStatus", dataType = "Integer" ,example = "1")
    private Integer securityOfAssessmentDocumentsAndRecordsStatus;

    @ApiModelProperty(value = "securityOfAssessmentDocumentsAndRecordsStatusComment", dataType = "String" ,example = "Sampl name")
    private String securityOfAssessmentDocumentsAndRecordsStatusComment;
}
