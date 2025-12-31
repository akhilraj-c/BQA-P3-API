package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_form_appln_manager")
@NoArgsConstructor
@AllArgsConstructor
public class FormApplicationManager extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @Column(name = "ip1")
    private String institutionNameEnglish;

    @Column(name = "ip1_status")
    private Integer institutionNameEnglishStatus;

    @Column(name = "ip1_status_comment")
    private String institutionNameEnglishStatusComment;

    @Column(name = "ip2")
    private String institutionNameArabic;

    @Column(name = "ip2_status")
    private Integer institutionNameArabicStatus;

    @Column(name = "ip2_status_comment")
    private String institutionNameArabicStatusComment;

    @Column(name = "ip3")
    private Integer institutionDetailRegulatedBy;

    @Column(name = "ip3_status")
    private Integer institutionDetailRegulatedByStatus;

    @Column(name = "ip3_status_comment")
    private String institutionDetailRegulatedByStatusComment;

    @Column(name = "ip4")
    private Integer institutionDetailLicensedBy;

    /**
     * Others data added
     */
    @Column(name = "others_data")
    private String othersData;

    @Column(name = "regulatory_other_data")
    private String regulatoryOthersData;
    @Column(name = "licenced_by_other_data")
    private String licencedByOthersData;
    @Column(name = "institution_type_other_data")
    private String institutionTypeOtherData;
    @Column(name = "field_other_data")
    private String fieldOtherData;

    @Column(name = "not_applicable")
    private String notApplicable;

    @Column(name = "ip4_status")
    private Integer institutionDetailLicensedByStatus;

    @Column(name = "ip4_status_comment")
    private String institutionDetailLicensedByStatusComment;

    @Column(name = "ip5_sub1")
    private String institutionApprovalNumber;

    @Column(name = "ip5_sub2")
    private Long institutionIssueDate;

    @Column(name = "ip5_sub3")
    private Long institutionExpiryDate;

    @Column(name = "ip5_status")
    private Integer institutionApprovalNumberStatus;

    @Column(name = "ip5_status_comment")
    private String institutionApprovalNumberStatusComment;

    @Column(name = "ip6")
    private Integer institutionDetailType;

    @Column(name = "ip6_status")
    private Integer institutionDetailTypeStatus;

    @Column(name = "ip6_status_comment")
    private String institutionDetailTypeStatusComment;

    @Column(name = "ip7")
    private Integer institutionDetailDomain;

    @Column(name = "ip7_status")
    private Integer institutionDetailDomainStatus;

    @Column(name = "ip7_status_comment")
    private String institutionDetailDomainStatusComment;

    @Column(name = "ip8_sub1")
    private Integer institutionDetailField;

    @Column(name = "ip8_sub2")
    private String institutionDetailDoc;

    @Column(name = "ip8_status")
    private Integer institutionDetailFieldStatus;

    @Column(name = "ip8_status_comment")
    private String institutionDetailFieldStatusComment;

    @Column(name = "ip9_sub_1")
    private String addressDetailBuilding;

    @Column(name = "ip9_sub_2")
    private String addressDetailRoad;

    @Column(name = "ip9_sub_3")
    private String addressDetailBlock;

    @Column(name = "ip9_status")
    private Integer addressDetailStatus;

    @Column(name = "ip9_status_comment")
    private String addressDetailStatusComment;

    @Column(name = "ip10_sub_1")
    private String contactDetailNumber;

    @Column(name = "ip10_sub_2")
    private String contactDetailEmailId;

    @Column(name = "ip10_sub_3")
    private String contactDetailWebSite;

    @Column(name = "ip10_status")
    private Integer contactDetailStatus;

    @Column(name = "ip10_status_comment")
    private String contactDetailStatusComment;

    @Column(name = "ip11_sub_1")
    private String headOfInstitutionName;

    @Column(name = "ip11_sub_2")
    private String headOfInstitutionPosition;

    @Column(name = "ip11_sub_3")
    private String headOfInstitutionContactNumber;

    @Column(name = "ip11_sub_4")
    private String headOfInstitutionEmailId;

    @Column(name = "ip11_status")
    private Integer headOfInstitutionStatus;

    @Column(name = "ip11_status_comment")
    private String headOfInstitutionStatusComment;

    @Column(name = "ip12_sub_1")
    private String headOfQualityName;

    @Column(name = "ip12_sub_2")
    private String headOfQualityPosition;

    @Column(name = "ip12_sub_3")
    private String headOfQualityContactNumber;

    @Column(name = "ip12_sub_4")
    private String headOfQualityEmailId;

    @Column(name = "ip12_status")
    private Integer headOfQualityStatus;

    @Column(name = "ip12_status_comment")
    private String headOfQualityStatusComment;

    @Column(name = "qa_1")
    private String qualityAssuranceDescription;

    @Column(name = "qa_1_status")
    private Integer qualityAssuranceDescriptionStatus;

    @Column(name = "qa_1_status_comment")
    private String qualityAssuranceDescriptionStatusComment;

    @Column(name = "qa_2")
    private String qualityDepartmentDoc;

    @Column(name = "qa_2_status")
    private Integer qualityAssuranceSystemOverviewStatus;

    @Column(name = "qa_2_status_comment")
    private String qualityAssuranceSystemOverviewStatusComment;

    @Column(name = "qa_3")
    private Long qualityReviewDate;

    @Column(name = "qa_3_status")
    private Integer qualityReviewDateStatus;

    @Column(name = "qa_3_status_comment")
    private String qualityReviewDateStatusComment;

    @Column(name = "qa_4")
    private String qualityAssuranceReport;

    @Column(name = "qa_4_status")
    private Integer lastInstitutionQualityAssuranceReviewStatus;

    @Column(name = "qa_4_status_comment")
    private String lastInstitutionQualityAssuranceReviewStatusComment;


    /**
     * access and admission
     **/
    @Column(name = "s1_atp_1_sub_1")
    private String accessAndAdmissionDesc;
    @Column(name = "s1_atp_1_sub_2")
    private String accessAndAdmissionFile;
    @Column(name = "s1_atp_1_status")
    private Integer accessAndAdmissionStatus;
    @Column(name = "s1_atp_1_status_comment")
    private String accessAndAdmissionStatusComment;


    /**
     * credit accumulation
     **/
    @Column(name = "s1_atp_2_sub_1")
    private String creditAccumulationDesc;
    @Column(name = "s1_atp_2_sub_2")
    private String creditAccumulationFile;
    @Column(name = "s1_atp_2_status")
    private Integer creditAccumulationStatus;
    @Column(name = "s1_atp_2_status_comment")
    private String creditAccumulationStatusComment;


    /**
     * Internal and external credit transfer
     ***/

    @Column(name = "s1_atp_3_sub_1")
    private String internalAndExternalCreditTransferDesc;
    @Column(name = "s1_atp_3_sub_2")
    private String internalAndExternalCreditTransferFile;
    @Column(name = "s1_atp_3_status")
    private Integer internalAndExternalCreditTransferStatus;
    @Column(name = "s1_atp_3_status_comment")
    private String internalAndExternalCreditTransferStatusComment;

    /**
     * Career progression and learning pathways
     ***/

    @Column(name = "s1_atp_4_sub_1")
    private String careerProgressionAndLearningPathwaysDesc;
    @Column(name = "s1_atp_4_sub_2")
    private String careerProgressionAndLearningPathwaysFile;
    @Column(name = "s1_atp_4_status")
    private Integer careerProgressionAndLearningPathwaysStatus;
    @Column(name = "s1_atp_4_status_comment")
    private String careerProgressionAndLearningPathwaysStatusComment;

    /**
     * Recognition of Prio Learning (RPL)
     ***/

    @Column(name = "s1_atp_5_sub_1")
    private String RecognitionOfPrioLearningDesc;
    @Column(name = "s1_atp_5_sub_2")
    private String RecognitionOfPrioLearningFile;
    @Column(name = "s1_atp_5_status")
    private Integer RecognitionOfPrioLearningStatus;
    @Column(name = "s1_atp_5_status_comment")
    private String RecognitionOfPrioLearningStatusComment;

    /**
     * Appeal Against Access and Transfer
     ***/

    @Column(name = "s1_atp_6_sub_1")
    private String appealAgainstAccessAndTransferDesc;
    @Column(name = "s1_atp_6_sub_2")
    private String appealAgainstAccessAndTransferFile;
    @Column(name = "s1_atp_6_status")
    private Integer appealAgainstAccessAndTransferStatus;
    @Column(name = "s1_atp_6_status_comment")
    private String appealAgainstAccessAndTransferStatusComment;

    /**  Qualification Development, Approval and Review **/


    /**
     * Justification of Need
     ***/
    @Column(name = "s2_qdar_1_sub_1")
    private String justificationOfNeedDesc;
    @Column(name = "s2_qdar_1_sub_2")
    private String justificationOfNeedFile;
    @Column(name = "s2_qdar_1_status")
    private Integer justificationOfNeedStatus;
    @Column(name = "s2_qdar_1_status_comment")
    private String justificationOfNeedStatusComment;


    /**
     * Qualification Design
     ***/
    @Column(name = "s2_qdar_2_sub_1")
    private String qualificationDesignDesc;
    @Column(name = "s2_qdar_2_sub_2")
    private String qualificationDesignFile;
    @Column(name = "s2_qdar_2_status")
    private Integer qualificationDesignStatus;
    @Column(name = "s2_qdar_2_status_comment")
    private String qualificationDesignStatusComment;


    /**
     * Qualification Compliance
     ***/
    @Column(name = "s2_qdar_3_sub_1")
    private String qualificationComplianceDesc;
    @Column(name = "s2_qdar_3_sub_2")
    private String qualificationComplianceFile;
    @Column(name = "s2_qdar_3_status")
    private Integer qualificationComplianceStatus;
    @Column(name = "s2_qdar_3_status_comment")
    private String qualificationComplianceStatusComment;


    /**
     * Learning Recourses and Learners’ Support:
     ***/
    @Column(name = "s2_qdar_4_sub_1")
    private String learningRecoursesAndLearnersSupportDesc;
    @Column(name = "s2_qdar_4_sub_2")
    private String learningRecoursesAndLearnersSupportFile;
    @Column(name = "s2_qdar_4_status")
    private Integer learningRecoursesAndLearnersSupportStatus;
    @Column(name = "s2_qdar_4_status_comment")
    private String learningRecoursesAndLearnersSupportStatusComment;


    /**
     * Qualification Internal Approval
     ***/
    @Column(name = "s2_qdar_5_sub_1")
    private String qualificationInternalApprovalDesc;
    @Column(name = "s2_qdar_5_sub_2")
    private String qualificationInternalApprovalFile;
    @Column(name = "s2_qdar_5_status")
    private Integer qualificationInternalApprovalStatus;
    @Column(name = "s2_qdar_5_status_comment")
    private String qualificationInternalApprovalStatusComment;


    /**
     * Qualification Internal and External Evaluation and Review
     ***/
    @Column(name = "s2_qdar_6_sub_1")
    private String qualificationInternalAndExternalEvaluationAndReviewDesc;
    @Column(name = "s2_qdar_6_sub_2")
    private String qualificationInternalAndExternalEvaluationAndReviewFile;
    @Column(name = "s2_qdar_6_status")
    private Integer qualificationInternalAndExternalEvaluationAndReviewStatus;
    @Column(name = "s2_qdar_6_status_comment")
    private String qualificationInternalAndExternalEvaluationAndReviewStatusComment;

    /**
     * Assessment Design
     **/
    @Column(name = "s3_adm_1_sub_1", nullable = false)
    private String AssessmentDesignDesc;

    @Column(name = "s3_adm_1_sub_2")
    private String AssessmentDesignFile;

    @Column(name = "s3_adm_1_status")
    private Integer AssessmentDesignStatus;

    @Column(name = "s3_adm_1_status_comment")
    private String AssessmentDesignStatusComment;

    /**
     * Internal and External Verification and Moderation of Assessment
     **/

    @Column(name = "s3_adm_2_sub_1")
    private String internalAndExternalVerificationAndModerationOfAssessmentDesc;

    @Column(name = "s3_adm_2_sub_2")
    private String internalAndExternalVerificationAndModerationOfAssessmentFile;

    @Column(name = "s3_adm_2_status")
    private Integer internalAndExternalVerificationAndModerationOfAssessmentStatus;

    @Column(name = "s3_adm_2_status_comment")
    private String internalAndExternalVerificationAndModerationOfAssessmentStatusComment;

    /**
     * Marking Criteria
     **/

    @Column(name = "s3_adm_3_sub_1")
    private String markingCriteriaDesc;

    @Column(name = "s3_adm_3_sub_2")
    private String markingCriteriaFile;

    @Column(name = "s3_adm_3_status")
    private Integer markingCriteriaStatus;

    @Column(name = "s3_adm_3_status_comment")
    private String markingCriteriaStatusComment;

    /**
     * Measuring the Achievement of Learning Outcomes
     **/

    @Column(name = "s3_adm_4_sub_1")
    public String measuringTheAchievementOfLearningOutcomesDesc;

    @Column(name = "s3_adm_4_sub_2")
    public String measuringTheAchievementOfLearningOutcomesFile;

    @Column(name = "s3_adm_4_status", nullable = false)
    private Integer measuringTheAchievementOfLearningOutcomesStatus;

    @Column(name = "s3_adm_4_status_comment", nullable = false)
    private String measuringTheAchievementOfLearningOutcomesStatusComment;

    /**
     * Feedback to Learners
     **/

    @Column(name = "s3_adm_5_sub_1")
    private String FeedbackToLearnersDesc;

    @Column(name = "s3_adm_5_sub_2")
    private String FeedbackToLearnersFile;

    @Column(name = "s3_adm_5_status")
    private Integer FeedbackToLearnersStatus;

    @Column(name = "s3_adm_5_status_comment")
    private String FeedbackToLearnersStatusComment;

    /**
     * Approval of Assessment Results
     **/
    @Column(name = "s3_adm_6_sub_1")
    private String approvalOfAssessmentResultsDesc;

    @Column(name = "s3_adm_6_sub_2")
    private String approvalOfAssessmentResultsFile;

    @Column(name = "s3_adm_6_status")
    private Integer approvalOfAssessmentResultsStatus;

    @Column(name = "s3_adm_6_status_comment")
    private String approvalOfAssessmentResultsStatusComment;

    /**
     * Appeal Against Assessment Results
     **/

    @Column(name = "s3_adm_7_sub_1")
    private String appealAgainstAssessmentResultsDesc;

    @Column(name = "s3_adm_7_sub_2")
    private String appealAgainstAssessmentResultsFile;

    @Column(name = "s3_adm_7_status")
    public Integer appealAgainstAssessmentResultsStatus;

    @Column(name = "s3_adm_7_status_comment")
    public String appealAgainstAssessmentResultsStatusComment;

    /**
     * Integrity of Assessment
     **/

    @Column(name = "s3_adm_8_sub_1")
    public String integrityOfAssessmentDesc;

    @Column(name = "s3_adm_8_sub_2", nullable = false)
    private String integrityOfAssessmentFile;

    @Column(name = "s3_adm_8_status")
    public Integer integrityOfAssessmentStatus;

    @Column(name = "s3_adm_8_status_comment")
    public String integrityOfAssessmentStatusComment;

    /**
     * Security of Assessment Documents and Records
     **/

    @Column(name = "s3_adm_9_sub_1")
    private String securityOfAssessmentDocumentsAndRecordsDesc;

    @Column(name = "s3_adm_9_sub_2")
    private String securityOfAssessmentDocumentsAndRecordsFile;

    @Column(name = "s3_adm_9_status")
    private Integer securityOfAssessmentDocumentsAndRecordsStatus;

    @Column(name = "s3_adm_9_status_comment")
    private String securityOfAssessmentDocumentsAndRecordsStatusComment;

    /**
     * Certificate Issuance
     **/

    @Column(name = "s4_c_1_sub_1")
    private String certificateIssuanceDesc;

    @Column(name = "s4_c_1_sub_2")
    private String certificateIssuanceFile;

    @Column(name = "s4_c_1_status")
    private Integer certificateIssuanceStatus;

    @Column(name = "s4_c_1_status_comment")
    private String certificateIssuanceStatusComment;

    /**
     * Certificate Authentication
     **/

    @Column(name = "s4_c_2_sub_1")
    private String certificateAuthenticationDesc;

    @Column(name = "s4_c_2_sub_2")
    private String certificateAuthenticationFile;

    @Column(name = "s4_c_2_status")
    private Integer certificateAuthenticationStatus;

    @Column(name = "s4_c_2_status_comment")
    private String certificateAuthenticationStatusComment;

    /**
     * Records of Certification
     **/

    @Column(name = "s4_c_3_sub_1")
    private String recordsOfCertificationDesc;

    @Column(name = "s4_c_3_sub_2")
    private String recordsOfCertificationFile;

    @Column(name = "s4_c_3_status")
    private Integer recordsOfCertificationStatus;

    @Column(name = "s4_c_3_status_comment")
    private String recordsOfCertificationStatusComment;

    /**
     * Institution Quality Assurance System
     **/

    @Column(name = "s5_scqi_1_sub_1")
    private String institutionQualityAssuranceSystemDesc;

    @Column(name = "s5_scqi_1_sub_2")
    private String institutionQualityAssuranceSystemFile;

    @Column(name = "s5_scqi_1_status")
    private Integer institutionQualityAssuranceSystemStatus;

    @Column(name = "s5_scqi_1_status_comment")
    private String institutionQualityAssuranceSystemStatusComment;

    /**
     * Continuous Improvement of Institution Quality Assurance System
     **/

    @Column(name = "s5_scqi_2_sub_1")
    private String continuousImprovementOfInstitutionQualityAssuranceSystemDesc;

    @Column(name = "s5_scqi_2_sub_2")
    private String continuousImprovementOfInstitutionQualityAssuranceSystemFile;

    @Column(name = "s5_scqi_2_status")
    private Integer continuousImprovementOfInstitutionQualityAssuranceSystemStatus;

    @Column(name = "s5_scqi_2_status_comment")
    private String continuousImprovementOfInstitutionQualityAssuranceSystemStatusComment;

    /**
     * Risk and Crisis Management
     **/
    @Column(name = "s5_scqi_3_sub_1")
    private String riskAndCrisisManagementDesc;

    @Column(name = "s5_scqi_3_sub_2")
    private String riskAndCrisisManagementFile;

    @Column(name = "s5_scqi_3_status")
    private Integer riskAndCrisisManagementStatus;

    @Column(name = "s5_scqi_3_status_comment")
    private String riskAndCrisisManagementStatusComment;


    /**
     * Application Contact
     **/
    @Column(name = "ap_cont_1")
    private String applicationContactName;
    @Column(name = "ap_cont_2")
    private String applicationContactNumber;
    @Column(name = "ap_cont_3")
    private String applicationContactPosition;
    @Column(name = "ap_cont_4")
    private String applicationContactEmail;


    @Column(name = "overall_status")
    private Integer overallStatus;
    @Column(name = "overall_status_comment")
    private String overallStatusComment;
    @Column(name = "extra_evd_elabo_status")
    private Integer extraEvdStatus;
    @Column(name = "is_extenstion_requested")
    private Integer isExtensionRequested;
    @Column(name = "extension_requested_date")
    private Long extensionRequestedDate;
    @Column(name = "extension_requested_status")
    private Integer extensionRequestedStatus;
    @Column(name = "reason_for_extension")
    private String reasonForExtension;
    @Column(name = "add_data_sub_date")
    private Long additionalDataSubDate;
    @Column(name = "file_path_parameter")
    private String filePathParameter;


    /**
     * Common columns
     **/

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "updated_ip")
    private String updatedIp;

    @Column(name = "created_app_id")
    private Long createdAppId;

    @Column(name = "updated_app_id")
    private Long updatedAppId;

    @Column(name = "created_time")
    public Long createdTime;

    @Column(name = "last_updated_time")
    public Long lastUpdatedTime;

    @PrePersist
    public void prePersist() {
        createdTime = System.currentTimeMillis();
        lastUpdatedTime = System.currentTimeMillis();
        createdIp = WebUtils.getClientIpAddress();
        updatedIp = WebUtils.getClientIpAddress();
        isExtensionRequested = 0;
        extensionRequestedStatus = 0;
        createdAppId = WebUtils.getAppId();
        updatedAppId = WebUtils.getAppId();
        createdBy = WebUtils.getUserId();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedTime = System.currentTimeMillis();
        updatedIp = WebUtils.getClientIpAddress();
        updatedAppId = WebUtils.getAppId();
    }


    @Transient
    public void appUpdateSave() {
        lastUpdatedTime = System.currentTimeMillis();
    }


}
