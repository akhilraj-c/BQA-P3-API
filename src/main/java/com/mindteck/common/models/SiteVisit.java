package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "tbl_site_visit")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiteVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "form_unique_id")
    private Long formUniqueId;
    @Column(name = "institute_name")
    private String instituteName;
    @Column(name = "person_involved")
    private String personInvolved;
    @Column(name = "site_visit_required")
    private Integer siteVisitRequired;

    @Column(name = "date1")
    private Long date1;
    @Column(name = "date2")
    private Long date2;
    @Column(name = "date3")
    private Long date3;
    @Column(name = "visit_date")
    private Long visitDate;
    @Column(name = "inst_rep_name")
    private String instituteRepresentativeName;
    @Column(name = "inst_rep_position")
    private String instituteRepresentativePosition;
    @Column(name = "inst_rep_contact")
    private String instituteRepresentativeContact;
    @Column(name = "inst_rep_email")
    private String instituteRepresentativeEmail;
    @Column(name = "ilep_documents")
    private String ilepDocuments;
    @Column(name = "inst_documents")
    private String instDocuments;


    @Column(name = "confidentiality_status")
    private Integer confidentialityStatus;
    @Column(name = "is_date_ext_req")
    private Integer isDateExtensionRequested = 0;

    @Column(name = "report_file")
    private String reportFile;

    @Column(name = "agenda_form")
    private String agendaForm;

    @Column(name = "agenda_comment")
    private String agendaFormComment;

    @Column(name = "filled_agenda_form")
    private String filledAgendaForm;

    @Column(name = "filled_agenda_comment")
    private String filledAgendaFormComment;

    @Column(name = "am_mark_site_visit")
    private Integer amMarkSiteVisit;

    @Column(name = "am_mark_site_visit_comment")
    private String amMarkSiteVisitComment;

    @Column(name = "am_reject_document_comment")
    private String amRejectDocumentComment;


    @Column(name = "am_evaluate_uploaded_site_visit_doc")
    private String amEvaluateUploadedSiteVisitDoc;

    @Column(name = "ilep_evaluate_uploaded_site_visit_doc")
    private String ilepEvaluateUploadedSiteVisitDoc;

    @Column(name = "inst_upload_ext_evd_dead_line")
    private Long instituteUploadExtraEvidenceDeadLine;


    /**
     * comnon fields
     */

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


