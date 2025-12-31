package com.mindteck.common.modules.revalidation.model;

import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_monitoring_change_form")
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringChangeForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "S9884155")
    @Column(name = "form_unique_id", nullable = false)
    private Long formUniqueId;


    @ApiModelProperty(value = "changeType", dataType = "Integer" ,example = "1")
    @Column(name = "change_type")
    private Integer changeType;

    @ApiModelProperty(value = "description", dataType = "String" ,example = "description")
    @Column(name = "description")
    private String description;

    @ApiModelProperty(value = "file", dataType = "String" ,example = "file")
    @Column(name = "file")
    private String file;

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

    @PrePersist
    public void prePersist() {
        createdTime = System.currentTimeMillis();
        lastUpdatedTime = System.currentTimeMillis();
        createdIp = WebUtils.getClientIpAddress();
        updatedIp = WebUtils.getClientIpAddress();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedTime = System.currentTimeMillis();

    }
}
