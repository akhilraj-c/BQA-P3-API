package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_meeting")
@NoArgsConstructor
@AllArgsConstructor
public class Meeting extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @Column(name = "first_date_and_time")
    private Long firstDateAndTime;
    @Column(name = "first_link_or_location")
    private String firstLinkOrLocation;

    @Column(name = "first_meeting_type")
    private Integer firstMeetingType;

    @Column(name = "first_method")
    private Integer firstMethod;

    @Column(name = "dead_line")
    private Long deadLine;

    @Column(name = "second_date_and_time")
    private Long secondDateAndTime;
    @Column(name = "second_link_or_location")
    private String secondLinkOrLocation;

    @Column(name = "second_meeting_type")
    private Integer secondMeetingType;

    @Column(name = "second_method")
    private Integer secondMethod;

    @Column(name = "mom_url")
    private String firstMeetingDocumentsAndComments;



    @Column(name = "first_meet_report_doc_cmnts")
    private String firstMeetingReportDocumentAndComments;

    @Column(name = "first_meet_ques_cmnts")
    private String firstMeetingQuestionAndComments;

    @Column(name = "first_meet_evid_cmnts")
    private String firstMeetingEvidenceAndComments;


    @Column(name = "second_meet_report_doc_cmnts")
    private String secondMeetingReportDocumentAndComments;

    @Column(name = "second_meet_ques_cmnts")
    private String secondMeetingQuestionAndComments;

    @Column(name = "second_meet_evid_cmnts")
    private String secondMeetingEvidenceAndComments;

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
