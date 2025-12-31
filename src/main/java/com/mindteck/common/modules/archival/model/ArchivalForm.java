package com.mindteck.common.modules.archival.model;

import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_archival_form")
@NoArgsConstructor
@AllArgsConstructor
public class ArchivalForm extends AbstractModel implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "S9884155")
    @Column(name = "form_unique_id", nullable = false)
    private Long formUniqueId;

    @ApiModelProperty(value = "decision", dataType = "String" ,example = "decision")
    @Column(name = "decision")
    private String decision;

    @ApiModelProperty(value = "comment", dataType = "String" ,example = "comment")
    @Column(name = "comment")
    private String comment;

    @ApiModelProperty(value = "reason", dataType = "String" ,example = "reason")
    @Column(name = "reason")
    private String reason;

    @ApiModelProperty(value = "sector", dataType = "Integer" ,example = "sector")
    @Column(name = "sector")
    private Integer sector;

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
