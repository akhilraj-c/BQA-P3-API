package com.mindteck.common.models;

import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_mail_template")
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplate extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_code")
    private String templateCode;

    @Column(name = "template_description")
    private String templateDescription;

    @Column(name = "template_body")
    private String templateBody;

    @Column(name = "template_subject")
    private String templateSubject;

    @Column(name = "display_sequence")
    private Integer displaySequence;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "institute_cc")
    private Integer instituteCc;

    @Column(name = "dfo_admin_cc")
    private Integer dfoAdminCc;

    @Column(name = "am_cc")
    private Integer amCc;

    @Column(name = "dfo_member_cc")
    private Integer dfoMemberCc;

    @Column(name = "chief_cc")
    private Integer chiefCc;

    @Column(name = "director_cc")
    private Integer directorCc;

    @Column(name = "ilep_cc")
    private Integer ilepCc;

    @Column(name = "institution_head_cc")
    private Integer headOfInstitutionCc;

    /** common fields  */

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
