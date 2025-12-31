package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_doc_feedback")
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFeedback extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @Column(name = "qac_feedback_description")
    private String qacFeedbackDescription;

    @Column(name = "qac_feedback_file")
    private String qacFeedbackFile;


    @Column(name = "institution_comment")
    private String institutionComment;

    @Column(name = "institution_additional_info_status")
    private Integer institutionAdditionalInfoStatus;

    @Column(name = "submitted_report_file")
    private String submittedReportFile;

    @Column(name = "nac_comment")
    private String nacComment;

    @Column(name = "nac_file")
    private String nacFile;

    @Column(name = "nac_additional_info_status")
    private Integer nacAdditionalInfoStatus;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "mcu_scanned_file")
    private String mcuScannedFile;

    @Column(name = "mcu_file_comment")
    private String mcuFileComment;


    @Column(name = "nac_feedback_description")
    private String nacFeedbackDescription;

    @Column(name = "nac_feedback_file")
    private String nacFeedbackFile;

    @Column(name = "dfo_cmnt_mco")
    private String dfoCommentMCO;
    @Column(name = "dfo_file_mco")
    private String dfoFileMco;
    @Column(name = "dfo_signed_file")
    private String dfoSignedFile;

    @Column(name = "dfo_signed_comment")
    private String dfoSignedComment;
    @Column(name = "dfo_signed_status")
    private Integer dfoSignedStatus;
    @Column(name = "dfo_shared_cabinet")
    private Integer dfoSharedCabinet;

    @Column(name = "dfo_shared_cabinet_comment")
    private String dfoSharedCabinetComment;

    @Column(name = "dfo_shared_cabinet_file")
    private String dfoSharedCabinetFile;
    @Column(name = "dfo_cabinet_approved")
    private Integer dfoCabinetApproved;

    @Column(name = "dfo_cabinet_approved_comment")
    private String dfoCabinetApprovedComment;

    @Column(name = "dfo_cabinet_approved_file")
    private String dfoCabinetApprovedFile;

    @Column(name = "dfo_approve_appeal_comment")
    private String dfoApproveAppealComment;

    @Column(name = "dfo_approve_appeal_file")
    private String dfoApproveAppealFile;

    @Column(name = "am_approve_report_comment")
    private String amApproveReportComment;

    @Column(name = "am_approve_report_file")
    private String amApproveReportFile;

    @Column(name = "doc_shared_to_nac_comment")
    private String docSharedToNacComment;

    @Column(name = "doc_shared_to_nac_file")
    private String docSharedToNacFile;

    @Column(name = "doc_shared_to_qac_comment")
    private String docSharedToQacComment;

    @Column(name = "doc_shared_to_qac_file")
    private String docSharedToQacFile;
    @Column(name = "factual_accuracy_start_comment")
    private String factualAccuracyStartComment;

    @Column(name = "factual_accuracy_start_file")
    private String factualAccuracyStartFile;

    @Column(name = "qac_approve_comment")
    private String qacApproveComment;

    @Column(name = "qac_approve_file")
    private String qacApproveFile;

    @Column(name = "dfo_chief_approve_comment")
    private String dfoChiefApproveComment;

    @Column(name = "dfo_chief_approve_file")
    private String dfoChiefApproveFile;

    @Column(name = "gdq_ac_approve_comment")
    private String gdqAcApproveComment;

    @Column(name = "gdq_ac_approve_file")
    private String gdqAcApproveFile;

    @Column(name = "institute_appeal_comment")
    private String instituteAppealComment;
    @Column(name = "institute_appeal_file")
    private String instituteAppealFile;

    @Column(name = "gdq_ac_shared_comment")
    private String gdqAcSharedComment;

    @Column(name = "gdq_ac_shared_file")
    private String gdqAcSharedFile;
    /** comnon fields  */

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