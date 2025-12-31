package com.mindteck.common.modules.confirmation_panel.model;


import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_confirmation_panel")
@ApiModel(value = "confirmationPanel")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationPanel extends AbstractModel implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "Sampl name")
    @Column(name = "form_unique_id", nullable = false)
    public Long formUniqueId;

    @ApiModelProperty(value = "name", dataType = "String" ,example = "Standard 1")
    @Column(name = "name", nullable = false)
    public String name;

    @ApiModelProperty(value = "position", dataType = "String" ,example = "Standard 1.1")
    @Column(name = "position", nullable = false )
    public String position;

    @ApiModelProperty(value = "isChair", dataType = "Integer" ,example = "1.1")
    @Column(name = "chair", nullable = false)
    public Integer isChair;

    @ApiModelProperty(value = "overAllNqfLevel", dataType = "String" ,example = "Description")
    @Column(name = "over_all_nqf_level", nullable = false)
    public String overAllNqfLevel;

    @ApiModelProperty(value = "overAllNqfCredit", dataType = "String" ,example = "Description")
    @Column(name = "over_all_nqf_credit", nullable = false)
    public String overAllNqfCredit;


    @ApiModelProperty(value = "comments", dataType = "String" ,example = "Description")
    @Column(name = "comments", nullable = false)
    public String comments;

    @ApiModelProperty(value = "date", dataType = "String" ,example = "Description")
    @Column(name = "date", nullable = false)
    public Long date;


    @Column(name = "have_major_changes")
    public Integer haveMajorChanges = 0;



    /**
     * Common columns
     **/

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_time")
    public Long createdTime;
    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "updated_ip")
    private String updatedIp;

    @Column(name = "created_app_id")
    private Long createdAppId;

    @Column(name = "updated_app_id")
    private Long updatedAppId;

    @Column(name = "is_revalidation")
    public Integer isRevalidation = 0;

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
