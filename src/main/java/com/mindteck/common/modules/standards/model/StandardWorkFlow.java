package com.mindteck.common.modules.standards.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_std_work_flow")
@ApiModel(value = "standardWorkFlow")
@NoArgsConstructor
@AllArgsConstructor
public class StandardWorkFlow extends AbstractModel implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "Sampl name")
    @Column(name = "form_unique_id", nullable = false)
    public Long formUniqueId;

    @ApiModelProperty(value = "parentTag", dataType = "String" ,example = "Standard 1")
    @Column(name = "parent_tag", nullable = false)
    public String parentTag;

    @ApiModelProperty(value = "tag", dataType = "String" ,example = "Standard 1.1")
    @Column(name = "tag", nullable = false )
    public String tag;

    @ApiModelProperty(value = "version", dataType = "Integer" ,example = "1.1")
    @Column(name = "version", nullable = false)
    public Integer version;

    //@ApiModelProperty(value = "subVersion", dataType = "Integer" ,example = "1.1")
    @JsonIgnore
    @Column(name = "sub_version", nullable = false)
    public Integer subVersion;


    //@ApiModelProperty(value = "description", dataType = "String" ,example = "Description")
    @JsonIgnore
    @Column(name = "description", nullable = false)
    public String description;

    @Column(name = "inst_description", nullable = false)
    public String instDescription;

   // @ApiModelProperty(name = "file", dataType = "String" ,example = "Description")
    @JsonIgnore
    @Column(name = "file", nullable = false)
    public String file;

    @ApiModelProperty(value = "status", dataType = "Integer" ,example = "1.1")
    @Column(name = "status", nullable = true)
    public Integer status;

    //Columns used for administrative check
    @ApiModelProperty(value = "amStatus", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    @Column(name = "am_status", nullable = true)
    public Integer amStatus;

    @ApiModelProperty(value = "latestAmCheck", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    @Column(name = "latest_am_check", nullable = true)
    public Integer latestAmCheck;

    @Column(name = "latest_inst_check", nullable = true)
    public Integer latestInstCheck;

    @ApiModelProperty(value = "amUserId", dataType = "Long" ,example = "Administrative checked user id")
    @Column(name = "am_user_id", nullable = true)
    public Long amUserId;

    @ApiModelProperty(value = "amComment", dataType = "String" ,example = "Administrative check comment")
    @Column(name = "am_comment", nullable = true)
    public String amComment;

    @ApiModelProperty(value = "amFile", dataType = "String" ,example = "Administrative check file")
    @Column(name = "am_file", nullable = true)
    public String amFile;

    @ApiModelProperty(value = "amTempField", dataType = "String" ,example = "Administrative check file")
    @Column(name = "am_temp", nullable = true)
    public String amTempField;

    @Transient
    @ApiModelProperty(value = "file", dataType = "String" ,example = "list of file detils")
    public List<WorkFlowFileDetails> fileDetails;

    @Column(name = "is_cf", nullable = false)
    @JsonIgnore
    public Integer isCf; //0 for program structure and 1 conditional fulfilment

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

    @Column(name = "last_updated_time")
    public Long lastUpdatedTime;

    @Column(name = "is_revalidation")
    public Integer isRevalidation = 0;

    @Column(name = "have_major_changes")
    public Integer haveMajorChanges = 0;

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

    // Override equals and hashCode based on id and name
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardWorkFlow standardWorkFlow = (StandardWorkFlow) o;
        boolean status =  formUniqueId.longValue() == standardWorkFlow.formUniqueId.longValue()
                && parentTag.equals(standardWorkFlow.parentTag)
                && tag.equals(standardWorkFlow.tag)
                && version.intValue() == standardWorkFlow.version.intValue();
              //  && subVersion.intValue() == person.subVersion.intValue();
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formUniqueId, parentTag, tag, version);
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
