package com.mindteck.common.modules.revalidation.model;

import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_monitoring_form")
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringForm extends AbstractModel implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "S9884155")
    @Column(name = "form_unique_id", nullable = false)
    private Long formUniqueId;

    @ApiModelProperty(value = "plannedForChange", dataType = "Integer" ,example = "1")
    @Column(name = "planned_for_change")
    private Integer plannedForChange;

    @ApiModelProperty(value = "plannedDate", dataType = "String" ,example = "1700123545")
    @Column(name = "planned_date")
    private String plannedDate;

    @ApiModelProperty(value = "changeDescription", dataType = "String" ,example = "changeDescription")
    @Column(name = "change_description")
    private String changeDescription;

    @ApiModelProperty(value = "confirmation", dataType = "int" ,example = "confirmation")
    @Column(name = "confirmation")
    private int confirmation;

    @ApiModelProperty(value = "haveChanges", dataType = "Integer" ,example = "1")
    @Column(name = "have_changes")
    private Integer haveChanges;


    @ApiModelProperty(value = "comments", dataType = "String" ,example = "comments")
    @Column(name = "comments")
    private String comments;


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
