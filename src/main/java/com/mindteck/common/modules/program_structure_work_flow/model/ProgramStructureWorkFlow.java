package com.mindteck.common.modules.program_structure_work_flow.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.standards.model.WorkFlowFileDetails;
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
@Table(name = "tbl_program_structure_work_flow")
@ApiModel(value = "programStructureFlow")
@NoArgsConstructor
@AllArgsConstructor
public class ProgramStructureWorkFlow extends AbstractModel implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "Sampl name")
    @Column(name = "form_unique_id", nullable = false)
    public Long formUniqueId;

    @ApiModelProperty(value = "programTag", dataType = "String" ,example = "Standard 1")
    @Column(name = "program_tag", nullable = false)
    public String programTag;

    @ApiModelProperty(value = "tag", dataType = "String" ,example = "Standard 1")
    @Column(name = "tag", nullable = false)
    public String tag;

    @ApiModelProperty(value = "version", dataType = "Integer" ,example = "1.1")
    @Column(name = "version", nullable = false)
    public Integer version;

    //@ApiModelProperty(value = "description", dataType = "String" ,example = "Description")
    @Column(name = "description", nullable = false)
    @JsonIgnore
    public String description;

    //@ApiModelProperty(value = "file", dataType = "String" ,example = "URL")
    @Column(name = "file", nullable = false)
    @JsonIgnore
    public String file;

    //@ApiModelProperty(value = "subVersion", dataType = "Integer" ,example = "1.1")
    @Column(name = "sub_version", nullable = false)
    @JsonIgnore
    public Integer subVersion;

    @ApiModelProperty(value = "status", dataType = "Integer" ,example = "1.1")
    @Column(name = "status", nullable = true)
    public Integer status;

    //Columns used for administrative verification
    @ApiModelProperty(value = "amVerificationStatus", dataType = "Integer" ,example = "null for pending, 0 for not verified 1 for verified, ")
    @Column(name = "am_verification_status", nullable = true)
    public Integer amVerificationStatus;

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

    @ApiModelProperty(name = "latestAmCheck", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    @Column(name = "latest_am_check", nullable = true)
    public Integer latestAmCheck;

    @Transient
    @ApiModelProperty(name = "fileDetails", dataType = "String" ,example = "list of file detils")
    public List<WorkFlowFileDetails> fileDetails;


    @Column(name = "is_revalidation")
    public Integer isRevalidation = 0;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramStructureWorkFlow programStructure = (ProgramStructureWorkFlow) o;
        boolean status =  formUniqueId.longValue() == programStructure.formUniqueId.longValue()
                && programTag.equals(programStructure.programTag)
                && tag.equals(programStructure.tag)
                && version.intValue() == programStructure.version.intValue();
        //  && subVersion.intValue() == person.subVersion.intValue();
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formUniqueId, programTag, tag, version);
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
