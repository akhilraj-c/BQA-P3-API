package com.mindteck.common.models;

import com.mindteck.common.constants.Enum.BooleanEnum;
import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_ilep_evaluation_form")
@NoArgsConstructor
@AllArgsConstructor
public class ILEPEvaluationForm extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", nullable = false)
    private Long evaluationId;

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @Column(name = "std_1_1")
    private String std_1_1;

    @Column(name = "std_1_2")
    private String std_1_2;

    @Column(name = "std_1_3")
    private String std_1_3;

    @Column(name = "std_1_4")
    private String std_1_4;

    @Column(name = "std_1_5")
    private String std_1_5;

    @Column(name = "std_1_6")
    private String std_1_6;

    @Column(name = "s1_atp_condition")
    private String standard1Condition;

    @Column(name = "s1_atp_suggestion")
    private String standard1Suggestion;

    @Column(name = "s1_atp_judgement")
    private Integer standard1Judgement;

    @Column(name = "s1_atp_judgement_history")
    private String standard1JudgementHistory;

    @Column(name = "std_2_1")
    private String std_2_1;

    @Column(name = "std_2_2")
    private String std_2_2;

    @Column(name = "std_2_3")
    private String std_2_3;

    @Column(name = "std_2_4")
    private String std_2_4;

    @Column(name = "std_2_5")
    private String std_2_5;

    @Column(name = "std_2_6")
    private String std_2_6;

    @Column(name = "std_2_7")
    private String std_2_7;

    @Column(name = "s2_qdar_condition")
    private String standard2Condition;

    @Column(name = "s2_qdar_suggestion")
    private String standard2Suggestion;

    @Column(name = "s2_qdar_judgement")
    private Integer standard2Judgement;

    @Column(name = "s2_qdar_judgement_history")
    private String standard2JudgementHistory;

    @Column(name = "std_3_1")
    private String std_3_1;

    @Column(name = "std_3_2")
    private String std_3_2;

    @Column(name = "std_3_3")
    private String std_3_3;

    @Column(name = "std_3_4", nullable = false)
    private String std_3_4;

    @Column(name = "std_3_5")
    private String std_3_5;

    @Column(name = "std_3_6")
    private String std_3_6;

    @Column(name = "std_3_7")
    public String std_3_7;

    @Column(name = "std_3_8")
    public String std_3_8;
    @Column(name = "std_3_9")
    private String std_3_9;

    @Column(name = "s3_adm_condition")
    private String standard3Condition;

    @Column(name = "s3_adm_suggestion")
    private String standard3Suggestion;

    @Column(name = "s3_adm_judgement")
    private Integer standard3Judgement;

    @Column(name = "s3_adm_judgement_history")
    private String standard3JudgementHistory;

    @Column(name = "std_4_1")
    private String std_4_1;

    @Column(name = "std_4_2")
    private String std_4_2;

    @Column(name = "std_4_3")
    private String std_4_3;

    @Column(name = "std_4_4")
    private String std_4_4;

    @Column(name = "std_4_5")
    private String std_4_5;

    @Column(name = "std_4_6")
    private String std_4_6;

    @Column(name = "std_4_7")
    private String std_4_7;

    @Column(name = "s4_condition")
    private String standard4Condition;

    @Column(name = "s4_suggestion")
    private String standard4Suggestion;

    @Column(name = "s4_judgement")
    private Integer standard4Judgement;

    @Column(name = "s4_judgement_history")
    private String standard4JudgementHistory;

    @Column(name = "std_5_1")
    private String std_5_1;

    @Column(name = "std_5_2")
    private String std_5_2;

    @Column(name = "std_5_3")
    private String std_5_3;

    @Column(name = "s5_scqi_condition")
    private String standard5Condition;

    @Column(name = "s5_scqi_suggestion")
    private String standard5Suggestion;

    @Column(name = "s5_scqi_judgement")
    private Integer standard5Judgement;

    @Column(name = "s5_scqi_judgement_history")
    private String standard5JudgementHistory;

    @Column(name = "overall_judgement")
    private Integer overAllJudgement;

    @Column(name = "overall_judgement_history")
    private String overAllJudgementHistory;

    @Column(name = "condition_fulfilment_date")
    private Long conditionFulfilmentDate;


    @Column(name = "partial_met_count")
    private Integer partialMetCount;

    @Column(name = "partial_met_date")
    private Long partialMetDate;

    @Column(name = "partial_met_comment")
    private String partialMetComment;

    @Column(name = "partial_met_file")
    private String partialMetFile;
    @Column(name = "partial_met_status")
    private Integer partialMetStatus;

    @Column(name = "am_feedback_comment")
    private String amFeedbackComment;

    @Column(name = "am_feedback_file")
    private String amFeedbackFile;

    @Column(name = "am_feedback_action_history")
    private String amFeedbackActionHistory;

    @Column(name = "dfo_chief_eval_cmnt")
    private String dfoChiefEvaluationComment;

    @Column(name = "dfo_chief_plain_cmnt")
    private String dfoChiefPlainComment;

    @Column(name = "gdq_ac_eval_cmnt")
    private String gdqAcEvaluationComment;

    @Column(name = "gdq_ac_plain_cmnt")
    private String gdqAcPlainComment;

    @Column(name = "qac_eval_cmnt")
    private String qacEvaluationComment;

    @Column(name = "qac_plain_cmnt")
    private String qacPlainComment;

    @Column(name = "inst_cmnt_bck_file")
    private String institutionCommentBackFile;

    @Column(name = "inst_cmnt_bck")
    private String institutionCommentBack;

    @Column(name = "fac_accuracy_status")
    private Integer factualAccuracyStatus;

    @Column(name = "dec1")
    private String dec1;
    @Column(name = "dec2")
    private String dec2;
    @Column(name = "dec3")
    private String dec3;
    @Column(name = "draft")
    private String draft;
    @Column(name = "condfil")
    private Integer condfil;
    @Column(name = "is_extenstion_requested")
    private Integer isExtensionRequested;
    @Column(name = "extension_requested_date")
    private Long extensionRequestedDate;
    @Column(name = "extension_requested_status")
    private Integer extensionRequestedStatus;
    @Column(name = "reason_for_extension")
    private String reasonForExtension;




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

    @PrePersist
    public void prePersist() {
        condfil = 0;
        partialMetCount = 0;
        partialMetStatus = BooleanEnum.FALSE.getCode();
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
