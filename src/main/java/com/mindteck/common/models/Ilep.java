package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_ilep")
@NoArgsConstructor
@AllArgsConstructor
public class Ilep extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @Column(name = "ilep_member_id")
    private Long ilepMemberId;

    @Column(name = "is_head")
    private Integer isHead;

    @Column(name = "is_dfo_approved")
    private Integer isDfoApproved;

    @Column(name = "grand_access")
    private Integer grandAccess;


    @Column(name = "institution_conflict_data")
    private String institutionConflictData;

    @Column(name = "ilep_conflict_data")
    private String ilepConflictData;

    @Column(name = "institution_conflict_status")
    private Integer institutionConflictStatus;

    @Column(name = "ilep_conflict_status")
    private Integer ilepConflictStatus;

    @Column(name = "is_conflict_approved_am")
    private Integer isConflictApprovedAm;

    @Column(name = "is_conflict_approved_dfo")
    private Integer isConflictApprovedDfo;

    @Column(name = "is_history")
    private Integer isHistory;



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
        isConflictApprovedDfo = 0;
        isConflictApprovedAm = 0;
        ilepConflictStatus = 0;
        institutionConflictStatus = 0;
        grandAccess = 0;
        isHistory = 0;
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
