package com.mindteck.models_cas;


import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_client_registration")
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistration extends AbstractModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id", nullable = false)
    private Long appId;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "os_info")
    private String osInfo;

    @Column(name = "model_info")
    private String modelInfo;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "count")
    private Integer count;

    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "updated_ip")
    private String updatedIp;

   /* @Column(name = "created_by")
    public Long createdBy;

    @Column(name = "updated_by")
    public Long updatedBy;*/

    @Column(name = "created_time")
    public Long createdTime;

    @Column(name = "last_updated_time")
    public Long lastUpdatedTime;

    @PrePersist
    public void prePersist() {
        count = 1;
        createdTime = System.currentTimeMillis();
        lastUpdatedTime = System.currentTimeMillis();
        createdIp = WebUtils.getClientIpAddress();
        updatedIp = WebUtils.getClientIpAddress();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedTime = System.currentTimeMillis();
        updatedIp = WebUtils.getClientIpAddress();
    }


    @Transient
    public void appUpdateSave() {
        count = count+1;
        lastUpdatedTime = System.currentTimeMillis();
    }
}
