package com.mindteck.common.modules.program_structure.model;


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
@Table(name = "tbl_program_structure")
@ApiModel(value = "programStructure")
@NoArgsConstructor
@AllArgsConstructor
public class ProgramStructure extends AbstractModel implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(name = "formUniqueId", dataType = "Long" ,example = "Sampl name")
    @Column(name = "form_unique_id", nullable = false)
    public Long formUniqueId;

    @ApiModelProperty(name = "slNo", dataType = "Integer" ,example = "1")
    @Column(name = "sl_no", nullable = false)
    public Integer slNo;

    @ApiModelProperty(name = "unitCode", dataType = "String" ,example = "Standard 1")
    @Column(name = "unit_code", nullable = false)
    public String unitCode;

    @ApiModelProperty(name = "title", dataType = "String" ,example = "Standard 1.1")
    @Column(name = "title", nullable = false )
    public String title;

    @ApiModelProperty(name = "isMandatory", dataType = "Integer" ,example = "1.1")
    @Column(name = "mandatory", nullable = false)
    public Integer isMandatory;

    @ApiModelProperty(name = "nqfLevel", dataType = "String" ,example = "nqfLevel")
    @Column(name = "nqf_level", nullable = false)
    public String nqfLevel;

    @ApiModelProperty(name = "nqfCredit", dataType = "String" ,example = "nqfCredit")
    @Column(name = "nqf_credit", nullable = false)
    public String nqfCredit;

    @ApiModelProperty(name = "mappingScoreCard", dataType = "String" ,example = "URL")
    @Column(name = "mapping_score_card", nullable = false)
    public String mappingScoreCard;

    @ApiModelProperty(name = "overallNqfLevel", dataType = "String" ,example = "nqfLevel")
    @Column(name = "overall_nqf_level", nullable = false)
    public String overallNqfLevel;

    @ApiModelProperty(name = "overallNqfCredit", dataType = "String" ,example = "overallNqfCredit")
    @Column(name = "overall_nqf_credit", nullable = false)
    public String overallNqfCredit;

    @ApiModelProperty(name = "comments", dataType = "String" ,example = "comments")
    @Column(name = "comments", nullable = false)
    public String comments;

    //@ApiModelProperty(value = "description", dataType = "String" ,example = "Description")
    @JsonIgnore
    @Column(name = "description", nullable = false)
    public String description;

    // @ApiModelProperty(name = "file", dataType = "String" ,example = "Description")
    @JsonIgnore
    @Column(name = "file", nullable = false)
    public String file;


    @ApiModelProperty(value = "status", dataType = "Integer" ,example = "1.1")
    @Column(name = "status", nullable = true)
    public Integer status;

    //Columns used for administrative check
    @ApiModelProperty(name = "version", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    @Column(name = "version", nullable = true)
    public Integer version;

    @JsonIgnore
    @Column(name = "sub_version", nullable = false)
    public Integer subVersion;

    @ApiModelProperty(name = "amStatus", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    @Column(name = "am_status", nullable = true)
    public Integer amStatus;

    @ApiModelProperty(name = "latestAmCheck", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    @Column(name = "latest_am_check", nullable = true)
    public Integer latestAmCheck;

    @ApiModelProperty(name = "amUserId", dataType = "Long" ,example = "Administrative checked user id")
    @Column(name = "am_user_id", nullable = true)
    public Long amUserId;

    @ApiModelProperty(name = "amComment", dataType = "String" ,example = "Administrative check comment")
    @Column(name = "am_comment", nullable = true)
    public String amComment;

    @ApiModelProperty(name = "amFile", dataType = "String" ,example = "Administrative check file")
    @Column(name = "am_file", nullable = true)
    public String amFile;

    @ApiModelProperty(name = "amTempField", dataType = "String" ,example = "Administrative check file")
    @Column(name = "am_temp", nullable = true)
    public String amTempField;

    @Column(name = "am_file_status", nullable = false)
    @JsonIgnore
    public Integer amFileStatus;

    @Column(name = "am_file_comment", nullable = false)
    @JsonIgnore
    public String amFileComment;

    @Column(name = "file_type", nullable = false)
    @JsonIgnore
    public Integer fileType; //0 for mapping score card

    @Transient
    public List<WorkFlowFileDetails> fileDetails;

    @Transient
    public List<WorkFlowFileDetails> fileDetails2;


    @Column(name = "is_revalidation")
    public Integer isRevalidation = 0;

    @Column(name = "have_major_changes")
    public Integer haveMajorChanges = 0;

    @Column(name = "revalidation_desc", nullable = false)
    public String revalidationDesc;

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
        ProgramStructure programStructure = (ProgramStructure) o;
        boolean status =  formUniqueId.longValue() == programStructure.formUniqueId.longValue()
                && unitCode.equals(programStructure.unitCode)
           //     && fileType.intValue() == programStructure.fileType.intValue()
                && version.intValue() == programStructure.version.intValue();
        //  && subVersion.intValue() == person.subVersion.intValue();
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(formUniqueId, unitCode, version);
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
