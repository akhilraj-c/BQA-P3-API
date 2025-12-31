package com.mindteck.common.modules.form.rest.formdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardsDataRequest {

//    @ApiModelProperty(name = "standard1DataRequest"  , value = "Json of standard1DataRequest" , dataType = "standard1DataRequest" ,
//            position = 1,
//    example = "{\"accessAndAdmissionDesc\":\"description1\",\"accessAndAdmissionFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"creditAccumulationDesc\":\"description2\",\"creditAccumulationFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"internalAndExternalCreditTransferDesc\":\"descriptio3\",\"internalAndExternalCreditTransferFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"careerProgressionAndLearningPathwaysDesc\":\"description4\",\"careerProgressionAndLearningPathwaysFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"recognitionOfPrioLearningDesc\":\"description5\",\"recognitionOfPrioLearningFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"appealAgainstAccessAndTransferDesc\":\"description6\",\"appealAgainstAccessAndTransferFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\"}")
    private  Standard1DataRequest standard1DataRequest;
//    @ApiModelProperty(name = "Standard2DataRequest"  , value = "Json of Standard2DataRequest" , dataType = "Standard2DataRequest" ,
//            position = 2,
//    example = "{\"justificationOfNeedDesc\":\"description1\",\"justificationOfNeedFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"qualificationDesignDesc\":\"description2\",\"qualificationDesignFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"qualificationComplianceDesc\":\"description3\",\"qualificationComplianceFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"learningRecoursesAndLearnersSupportDesc\":\"description4\",\"learningRecoursesAndLearnersSupportFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"qualificationInternalApprovalDesc\":\"description5\",\"qualificationInternalApprovalFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"qualificationInternalAndExternalEvaluationAndReviewDesc\":\"description6\",\"qualificationInternalAndExternalEvaluationAndReviewFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\"}")
    private Standard2DataRequest standard2DataRequest;
//    @ApiModelProperty(name = "Standard3DataRequest"  , value = "Json of Standard3DataRequest" , dataType = "Standard3DataRequest" ,
//            position = 3,
//    example = "{\"assessmentDesignDesc\":\"description1\",\"assessmentDesignFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"internalAndExternalVerificationAndModerationOfAssessmentDesc\":\"description2\",\"internalAndExternalVerificationAndModerationOfAssessmentFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"markingCriteriaDesc\":\"description3\",\"markingCriteriaFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"measuringTheAchievementOfLearningOutcomesDesc\":\"description4\",\"measuringTheAchievementOfLearningOutcomesFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"feedbackToLearnersDesc\":\"description5\",\"feedbackToLearnersFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"approvalOfAssessmentResultsDesc\":\"description6\",\"approvalOfAssessmentResultsFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"appealAgainstAssessmentResultsDesc\":\"description7\",\"appealAgainstAssessmentResultsFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"integrityOfAssessmentDesc\":\"description8\",\"integrityOfAssessmentFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"securityOfAssessmentDocumentsAndRecordsDesc\":\"description9\",\"securityOfAssessmentDocumentsAndRecordsFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\"}")
    private Standard3DataRequest standard3DataRequest;
//    @ApiModelProperty(name = "Standard4DataRequest"  , value = "Json of Standard4DataRequest" , dataType = "Standard4DataRequest" ,
//            position = 4,
//    example = "{\"certificateIssuanceDesc\":\"description1\",\"certificateIssuanceFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"certificateAuthenticationDesc\":\"description2\",\"certificateAuthenticationFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"recordsOfCertificationDesc\":\"description3\",\"recordsOfCertificationFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\"}")
    private Standard4DataRequest  standard4DataRequest;
//    @ApiModelProperty(name = "Standard5DataRequest"  , value = "Json of Standard5DataRequest" , dataType = "Standard5DataRequest" ,
//            position = 5,
//    example = "{\"institutionQualityAssuranceSystemDesc\":\"description1\",\"institutionQualityAssuranceSystemFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"continuousImprovementOfInstitutionQualityAssuranceSystemDesc\":\"description2\",\"continuousImprovementOfInstitutionQualityAssuranceSystemFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"riskAndCrisisManagementDesc\":\"description3\",\"riskAndCrisisManagementFile\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\"}")
    private Standard5DataRequest standard5DataRequest;


}
