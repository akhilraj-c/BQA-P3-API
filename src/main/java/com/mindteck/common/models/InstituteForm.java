package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_institute_form")
@NoArgsConstructor
@AllArgsConstructor
public class InstituteForm extends AbstractModel implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    //    private String qua    lificationTitle;
    @Column(name = "providers") // Added
    private String providers;   // String instead of List

    @Column(name = "level")
    private String level;

    @Column(name = "credit")
    private String credit;

    @Column(name = "number_of_units_courses_modules") // Added
    private String numberOfUnitsCoursesModules;

    @Column(name = "expected_submission_date") // Added
    private Long expectedSubmissionDate;  // long timestamp

    @Column(name = "applicant_organization_name") // Added
    private String applicantOrganizationName;

    @Column(name = "institute_name")
    private String institutionName;

    @Column(name = "inst_app_lic_no")
    private String instAppLicNo;

    @Column(name = "license_type")
    private Integer licenseType;

    @Column(name = "others_data")
    private String othersData;

    @Column(name = "not_applicable")
    private String notApplicable;


    @Column(name = "approval_doc_path")
    private String approvalDocFile;

    @Column(name = "issue_date")
    private Long issueDate;

    @Column(name = "exp_date")
    private Long expDate;

    @Column(name = "is_bqa_reviewed")
    private Integer isBqaReviewed;

    @Column(name = "review_issue_date")
    private Long reviewIssueDate;

    @Column(name = "review_jud_result")
    public String reviewJudResult;


    @Column(name = "is_offering_nan_loc_course_qa")
    public Integer isOfferingNanLocCourseQa;

    @Column(name = "offering_description")
    private String offeringDescription;

    @Column(name = "planned_sub_date")
    private String plannedSubDate;

    @Column(name = "random_date")
    private Long randomDate;

    @Column(name = "poc_name")
    private String contactPersonName;

    @Column(name = "poc_email")
    private String contactPersonEmail;

    @Column(name = "poc_cn_number")
    private String contactPersonNumber;

    @Column(name = "poc_title")
    private String contactPersonTitle;

    @Column(name = "status")
    private Integer status;

    @Column(name = "sub_status")
    private Integer subStatus;
    @Column(name = "completed_status")
    private String completedStatus;

    @Column(name = "resubmit_status")
    private Integer resubmitStatus;

    @Column(name = "resubmit_count")
    private Long resubmitCount;

    @Column(name = "last_resubmit_date")
    public Long lastResubmitDate;

    @Column(name = "current_submit_date")
    public Long currentSubmitDate;

    @Column(name = "current_stage")
    private Integer current_stage;

    @Column(name = "date_ext_status")
    private Integer dateExtensionStatus;

    @Column(name = "date_ext_reason")
    private String dateExtensionReason;

    @Column(name = "reqsted_ext_date")
    private Long requestedExtensionDate;

    @Column(name = "vfn_ext_date")
    private Long verificationExtensionDate;

    @Column(name = "evln_ext_date")
    private Long evaluationExtensionDate;

    @Column(name = "is_extenstion_requested")
    private Integer isDateExtensionRequested;

    @Column(name = "form_flow_history")
    private String form_flow_history;

    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "updated_ip")
    private String updatedIp;

    @Column(name = "created_app_id")
    private Long createdAppId;

    @Column(name = "updated_app_id")
    private Long updatedAppId;

    @Column(name = "created_time")
    private Long createdTime;

    @Column(name = "last_updated_time")
    private Long lastUpdatedTime;

    /*** Additional column added  */
    @Column(name = "assigned_app_manager")
    private Long assignedAppManager;
    @Column(name = "institution_id")
    private Long institutionId;

    @Column(name = "factual_accuracy_file")
    private String factualAccuracyFile;

    @Column(name = "factual_accuracy_comment")
    private String factualAccuracyComment;
    @Column(name = "inst_factual_accuracy_dead_line")
    private Long instituteFactualAccuracyDeadLine;


    @Column(name = "inst_appeal")
    private Integer institutionAppeal;
    @Column(name = "inst_appeal_approve")
    private Integer institutionAppealApprove;
    @Column(name = "inst_appeal_exp")
    private Long institutionAppealExpiry;

    @Column(name = "over_all_approve_history")
    private String overAllApproveHistory;

    @Column(name = "current_status_due_date")
    private Long currentStatusDueDate;

    @Column(name = "regulatory_other_data")
    private String regulatoryOthersData;
    @Column(name = "licenced_by_other_data")
    private String licencedByOthersData;
    @Column(name = "institution_type_other_data")
    private String institutionTypeOtherData;
    @Column(name = "field_other_data")
    private String fieldOtherData;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "rejection_date")
    private Long rejectionDate;

    @Column(name = "Rejected_by_user_type")
    private Integer RejectedByUserType;

    @Column(name = "terminated_mail_sent")
    private Integer terminatedMailSent;

    @Column(name = "remainder_mail_sent")
    private Integer remainderMailSent;

    // Additional columns for Qualification Placements
    @Column(name = "qualification_title")
    private String qualificationTitle;
    @Column(name = "no_of_modules")
    private Integer noOfModules;

    // nq and fq columns
    @Column(name = "qualification_type")
    private Integer qualificationType; // 1 awarding body , 2 sole provider

    @Column(name = "awarding_body")
    private String awardingBody;

    @Column(name = "included_in_other")
    private String includedInOther;

    @Column(name = "qualification_framework")
    private String qualificationFramework;

    @Column(name = "qualification_size")
    private String qualificationSize;

    @Column(name = "listing_id")
    private String listingId;
    @Column(name = "form_parent_unique_id")
    private Long formParentUniqueId;
    @Column(name = "overall_status")
    public Integer overallStatus;
    @Column(name = "is_draft")
    private Integer isDraft = 0; //isDraft = 0 not a draft 1 its a draft


    //for QP evaluation
    @Column(name = "overall_evln_status")
    private Integer overAllEvaluationStatus;
    @Column(name = "overall_evln_cmt")
    private String overAllEvaluationStatusComment;
    @Column(name = "evln_deadline")
    private Long evaluationDeadLine;
    @Column(name = "overall_verification_status")
    private Integer overAllVerificationStatus;
    @Column(name = "overall_verification_cmt")
    private String overAllVerificationStatusComment;
    @Column(name = "verification_deadline")
    private Long verificationDeadLine;
    @Column(name = "evaluation_flag")
    private Integer evaluationFlag;
    @Column(name = "verification_flag")
    private Integer verificationFlag;

    @Column(name = "evaluation_rejection_count")
    private Integer evaluationRejectionCount = 0;
    @Column(name = "verification_rejection_count")
    private Integer verificationRejectionCount = 0;

    @Column(name = "is_paid")
    private Integer isPaid = 0;


    //for revalidation
    @Column(name = "revalidation_form_unique_id")
    private Long revalidationFormUniqueId;

    @Column(name = "is_revalidation")
    private Integer isRevalidation = 0;

    @Column(name = "is_verification_panel_required")
    private Integer isVerificationPanelRequired = 0;

    @Column(name = "original_placement_date")
    private Long originalPlacementDate;

    @Column(name = "placed_date")
    private Long placedDate;

    @Column(name = "qp_id")
    private String qpId;

    @PrePersist
    public void prePersist() {
        institutionAppeal = 0;
        institutionAppealApprove= 0;
        institutionAppealExpiry = 0L;
        createdTime = System.currentTimeMillis();
        lastUpdatedTime = System.currentTimeMillis();
        createdIp = WebUtils.getClientIpAddress();
        updatedIp = WebUtils.getClientIpAddress();
        dateExtensionStatus = 0;
        isDateExtensionRequested = 0;
        currentStatusDueDate = 0L;
        remainderMailSent = 0;
        terminatedMailSent=0;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedTime = System.currentTimeMillis();
//        updatedIp = WebUtils.getClientIpAddress();
//        updatedAppId = WebUtils.getAppId();

    }

    @Override
    public InstituteForm clone() throws CloneNotSupportedException
    {
        Object clonedObject = super.clone();
        if(clonedObject instanceof InstituteForm) {
            return (InstituteForm) clonedObject;
        } else {
            throw new CloneNotSupportedException("Clone returned a non InstituteForm Object >> "+clonedObject.getClass().getName());
        }
    }

    public boolean isRevalidationForm() {
        return !Objects.isNull(isRevalidation) && isRevalidation == 1;
    }
}
