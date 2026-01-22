package com.mindteck.common.modules.user.model.rest.formdata;


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
@Table(name = "tbl_qualification_profile")
@ApiModel(value = "QualificationProfileData")
@NoArgsConstructor
@AllArgsConstructor
public class QualificationProfileData extends AbstractModel  implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "Sampl name")
    @Column(name = "form_unique_id", nullable = false)
    public Long formUniqueId;

    @ApiModelProperty(value = "qualificationNameEnglish", dataType = "String" ,example = "Sampl name")
    @Column(name = "qualification_name_english", nullable = false)
    public String qualificationNameEnglish;

    @ApiModelProperty(value = "qualificationNameArabic", dataType = "String" ,example = "Sampl name")
    @Column(name = "qualification_name_arabic", nullable = false)
    public String qualificationNameArabic;

    @ApiModelProperty(value = "institutionNameEnglish", dataType = "String" ,example = "Sampl name")
    @Column(name = "institution_name_english", nullable = false)
    public String institutionNameEnglish;

    @ApiModelProperty(value = "institutionNameArabic", dataType = "String" ,example = "Sampl name")
    @Column(name = "institution_name_arabic", nullable = false)
    public String institutionNameArabic;

    @ApiModelProperty(value = "collegeNameEnglish", dataType = "String" ,example = "Sampl name")
    @Column(name = "college_name_english", nullable = false)
    public String collegeNameEnglish;

    @ApiModelProperty(value = "collegeNameArabic", dataType = "String" ,example = "Sampl name")
    @Column(name = "college_name_arabic", nullable = false)
    public String collegeNameArabic;

    @ApiModelProperty(value = "listingId", dataType = "String" ,example = "Sampl name")
    @Column(name = "listing_id")
    public String listingId;

    @ApiModelProperty(value = "approvalNumber", dataType = "String" ,example = "Sampl name")
    @Column(name = "approval_number")
    public String approvalNumber;

    @ApiModelProperty(value = "issueDate", dataType = "Long" ,example = "Epoch Time")
    @Column(name = "issue_date")
    public Long issueDate;

    @ApiModelProperty(value = "expiryDate", dataType = "Long" ,example = "Epoch Time")
    @Column(name = "exp_date")
    public Long expiryDate;

    @ApiModelProperty(value = "startDate", dataType = "Long" ,example = "Epoch Time")
    @Column(name = "start_date")
    public Long startDate;

    @ApiModelProperty(value = "board", dataType = "String" ,example = "Board Name")
    @Column(name = "board_name")
    public String boardName;

    @ApiModelProperty(value = "narrow", dataType = "String" ,example = "Narrow Name")
    @Column(name = "narrow")
    public String narrow;

    @ApiModelProperty(value = "detailed", dataType = "String" ,example = "Detailed Name")
    @Column(name = "detailed")
    public String detailed;

    @ApiModelProperty(value = "qualificationDesc", dataType = "String" ,example = "Qualification Desc")
    @Column(name = "qualification_desc")
    public String qualificationDesc;

    @ApiModelProperty(value = "qualificationDescUrl", dataType = "String" ,example = "Qualification Desc URL")
    @Column(name = "qualification_desc_url")
    public String qualificationDescUrl;

    @ApiModelProperty(value = "filePathParameter", dataType = "String" ,example = "filePathParameter")
    @Column(name = "file_path_parameter")
    private String filePathParameter;

    @ApiModelProperty(value = "targetLearners", dataType = "String" ,example = "Qualification Desc")
    @Column(name = "target_learners")
    public String targetLearners;

    @ApiModelProperty(value = "targetLearnersUrl", dataType = "String" ,example = "Qualification Desc URL")
    @Column(name = "target_learners_url")
    public String targetLearnersUrl;

    @Column(name = "overall_status")
    private Integer overallStatus;

    @ApiModelProperty(value = "submissionDate", dataType = "Long" ,example = "1600000000")
    @Column(name = "submission_date")
    public Long submissionDate;


    @ApiModelProperty(value = "authPersonName", dataType = "String" ,example = "authPersonName")
    @Column(name = "auth_person_name")
    public String authPersonName;

    @ApiModelProperty(value = "authPersonTitle", dataType = "String" ,example = "authPersonTitle")
    @Column(name = "auth_person_title")
    public String authPersonTitle;

    @ApiModelProperty(value = "authPersonEmail", dataType = "String" ,example = "authPersonEmail")
    @Column(name = "auth_person_email")
    public String authPersonEmail;

    @ApiModelProperty(value = "authPersonMobile", dataType = "String" ,example = "authPersonMobile")
    @Column(name = "auth_person_mobile")
    public String authPersonMobile;

    @ApiModelProperty(value = "signFlag", dataType = "Integer" ,example = "0 not signed 1 signed")
    @Column(name = "sign_flag")
    public Integer signFlag; //0 not signed 1 signed

    @Column(name = "is_revalidation")
    public Integer isRevalidation = 0;

//    @Column(name = "website_address")
//    private String websiteAddress;
//
//    @Column(name = "address")
//    private String address;

    @Column(name = "included_in_other")
    private String includedInOther;

    @Column(name = "qualification_framework")
    private String qualificationFramework;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "qualification_level")
    private String qualificationLevel;

    @Column(name = "qualification_credit")
    private String qualificationCredit;

    @Column(name = "qualification_number")
    private String qualificationNumber;

    @Column(name = "qf_register_link")
    private String qfRegisterLink;

    @Column(name = "qualification_notes")
    private String qualificationNotes;


    @Column(name = "qualification_structure_description")
    private String qualificationStructureDescription;

    @Column(name = "qualification_structure_url")
    private String qualificationStructureUrl;

    @Column(name = "occupational_standards_description")
    private String occupationalStandardsDescription;

    @Column(name = "occupational_standards_url")
    private String occupationalStandardsUrl;

    @Column(name = "background_awarding_body_description")
    private String backgroundAwardingBodyDescription;

    @Column(name = "background_awarding_body_url")
    private String backgroundAwardingBodyUrl;

    @Column(name = "awarding_body_name")
    private String awardingBodyName;

    @Column(name = "awarding_body_website")
    private String awardingBodyWebsite;

//    @Column(name = "awarding_body_address")
//    private String awardingBodyAddress;

    @Column(name = "awarding_body_contact_name")
    private String awardingBodyContactName;

    @Column(name = "awarding_body_contact_position")
    private String awardingBodyContactPosition;

    @Column(name = "awarding_body_contact_number")
    private String awardingBodyContactNumber;

//    @Column(name = "awarding_body_contact_offcl_number")
//    private String awardingBodyContactOffclNumber;

    @Column(name = "awarding_body_contact_email")
    private String awardingBodyContactEmail;

    @Column(name = "offcl_contact_number")
    private String offclContactNumber;

    @Column(name = "providers")
    private String providers;

    @Column(name = "declaration_url")
    private String declarationUrl;
//    @OneToMany(
//            mappedBy = "qualificationProfile",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<QualificationProvider> providers;




    /**
     * Common columns
     **/

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

    //for revalidation
    @Column(name = "qualification_nqf_level")
    private String qualificationNqfLevel;
    @Column(name = "qualification_nqf_credit")
    private String qualificationNqfCredit;
    @Column(name = "number_of_units")
    private String numberOfUnits;
    @Column(name = "orginal_placement_date")
    private Long originalPlacementDate;
    @Column(name = "reason_for_revalidation")
    private String  reasonForRevalidation;
    @Column(name = "notes")
    private String notes;

    @Column(name = "qualification_placement_date")
    private String qualificationPlacementDate;


    //Non db data to UI

    @Transient
    private String name;
    @Transient
    private String position;
    @Transient
    private String mobileNo;
    @Transient
    private String email;

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
