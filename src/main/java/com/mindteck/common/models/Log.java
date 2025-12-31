package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_log")
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "user_sub_type")
    private Integer userSubType;


    @Column(name = "user_name")
    private String userName;

    @Column(name = "previous_message")
    private String previousMessage;

    @Column(name = "previous_status")
    private Integer previousStatus;

    @Column(name = "changed_message")
    private String changedMessage;

    @Column(name = "changed_status")
    private Integer changedStatus;



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
