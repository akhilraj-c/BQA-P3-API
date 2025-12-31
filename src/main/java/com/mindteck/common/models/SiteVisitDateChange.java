package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "tbl_site_date_change_requests")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiteVisitDateChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "form_unique_id")
    private Long formUniqueId;
    @Column(name = "institute_name")
    private String instituteName;
    @Column(name = "ilep_member_names")
    private String IlepMemberNames;
    @Column(name = "licence_body")
    private String licenceBody;

    @Column(name = "requested_date_1")
    private Long requestedDate1;

    @Column(name = "requested_date_2")
    private Long requestedDate2;

    @Column(name = "requested_date_3")
    private Long requestedDate3;
    @Column(name = "justification")
    private String justification;
    @Column(name = "am_approve")
    private Integer amApprove;
    @Column(name = "chief_approve")
    private String chiefApprove;


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


