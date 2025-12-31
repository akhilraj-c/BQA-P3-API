package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatusBackup4 {

    INITIAL_STATUS( 0 , "Initial Status"), //done
    INSTITUTION_REGISTERED(1,"Institution registered"), //done
    BQA_ADMIN_SHORTLISTED(2, "BQA admin shortlisted the application"),
    BQA_ADMIN_REJECTED(3 , "BQA admin rejected the application"), //done
    BQA_MOVED_MOVED(4, "BQA admin moved the application"), //done
    BQA_ADMIN_APPROVED(5, "BQA admin approved -and listed - By BQA Admin and send mail to all institutions "), //done

    BQA_ADMIN_APPROVED_UPLOAD_DOCUMENT(6, "BQA admin approved - Institution user uploading documents for administrative check"), //done

    FAILED_TO_SUBMIT(7 , "Institution failed to submit the application on time"),

    INSTITUTION_SUBMITTED(8 , "Institution user submitted application for administrative check"), //done


    ASSIGNED_APPLICATION_MANAGER(9, "Application manager assigned - application_manager : Application - assigned an AM - amname"), //done

    ADMINISTRATIVE_CHECk_COMPLETED(10 , "Administrative check completed - Administrative check completed"), //done
    REQUIRED_ADDITION_DATA(11 , "Incomplete"), //done

    PROCEED_WITH_INCOMPLETE_STATUS(12,"Admin Check(Sent)"),
    APPLICATION_MANAGER_REJECTED(13 , "Application manager rejected the application"),

    INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK(14, "Application manager requested for additional data : Inst. user re-submitted the documents for administrative check"), //done


    ILEP_PANEL_CREATED(15 , "Application manager created Evaluation Panel- AM assigned Evaluation Panel and submitted for Director approval "), //done

    DIRECTOR_APPROVED_ILEP_PANEL(16, "Application manager created Evaluation Panel -Director approved the Evaluation Panel"), //done

     INSTITUTION_ILEP_PANEL_NON_CONFLICT(17 , "Application manager created ilep panel - Institution user signed the non conflict against Evaluation Panel -0/3 "), //done

    INSTITUTION_ILEP_PANEL_CONFLICT(18 , "Application manager created ilep panel - Institution user signed the conflict against Evaluation Panel -0/3 "), //done

    INSTITUTION_ILEP_PANEL_CONFLICT_AM_APPROVE(19 , "AM approve the ilep conflict raised by institution"), //done

    INSTITUTION_ILEP_PANEL_CONFLICT_DFO_APPROVE(20 , "DFO approve the ilep conflict raised by institution after AM approve"), //done

    ILEP_PANEL_USER_1_INSTITUTION_CONFLICT(21, "Ilep user 1 conflict"), //done
    ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT(22, "Ilep user 1 non-conflict"), //done

    ILEP_PANEL_USER_2_INSTITUTION_CONFLICT(23, "Ilep user 2 conflict"), //done
    ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT(24, "Ilep user 2 non-conflict"), //done

    ILEP_PANEL_USER_3_INSTITUTION_CONFLICT(25, "Ilep user 3 conflict"), //done
    ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT(26, "Ilep user 3 non-conflict"), //done

    ILEP_PANEL_ASSIGNED(27 , "Ilep panel assigned"), //done

    ACCESS_GRANT_ILEP_1(28 , "Grant Access Ilep 1"), //done
    ACCESS_GRANT_ILEP_2(29 , "Grant Access  Ilep 2"), //done
    ACCESS_GRANT_ILEP_3(30 , "Grant Access  Ilep 3"), //done
    ACCESS_GRANT(26 , "Grant Access"), // keeping for clarification in code change , should remove after code update


    MEETING_CREATED(31 , "Meeting create by Application manager"), //done

    MEETING_COMPLETED(32 , "Meeting completed and Mom uploaded"), //done

    /**
     * sub status starts
     *
     */

    ILEP_EVALUATE_LISTED(33, "Ilep member evaluation met / Listed"), //done
    ILEP_EVALUATE_DEFERRED(34 , "ILEP members  evaluation partially met / deferred "), //done

    ILEP_EVALUATE_NOT_LISTED(35 , "ILEP members  evaluation not met / not listed "), //done

    SITE_VISIT_REQUIRED(36 , "Site visit required"), //done

    SITE_VISIT_NOT_REQUIRED(37 , "Site visit not required"), //done

    SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE(38, "Institution user accepted the site visit"), //done

    SITE_VISIT_DATE_EXTENSION_REQUESTED(39 , "Meeting completed and Mom uploaded : Date extension requested for the site visit"), //done

    SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST(40 , "Meeting completed and Mom uploaded - AM approved the site visit date change"), //done

    SITE_VISIT_AM_REJECT_DATE_AND_SELECT_NEW_DATE(41 , "Meeting completed and Mom uploaded - AM reject the date change request and add new date"),

    SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE(42 , "Meeting completed and Mom uploaded - AM changes the site visit date"), //done

    INSTITUTION_UPDATES_SITE_VISIT_DOCUMENT(43 , "Institution updates the site visit document "), //done

    SIGN_NON_CONFIDENTIAL(44 , "Sign non-confidential"), //done

    NOT_SIGN_NON_CONFIDENTIAL(45 , "Not Sign non-confidential"), //done

    SITE_VISIT_DONE_AND_REPORT_UPLOAD(46 , "Site visit done and report uploaded"), //done


  //  MEETING_COMPLETED_AND_MOM_UPLOADED(42, "Meeting completed and Mom uploaded - AM - Meeting completed & MOM uploaded"),

    /**
     * sub status ends
     */

  ILEP_EVALUATION_SUBMITTED(47, "ILEP Evaluation submitted to AM"), //done

    AM_EVALUATE_SUBMISSION_WITH_COMMENT(48,"AM Evaluate the Submission and revert with comments"), //done

    AM_APPROVE_THE_EVALUATION(49,"AM approved the evaluation and submitted (status)"), //done

    DFO_CHIEF_ADD_EVALUATION(50,"DFO chief added evaluation comments"), //done

    AM_EDIT_EVALUATION_BASED_ON_DFO_CHIEF_COMMENT(51, "AM updated the evaluation report as per DFO chief comments"), //done

    DFO_CHIEF_ADD_COMMENTS_AGAIN(52, "DFO chief add comments again"), //done

    DFO_CHIEF_APPROVED_THE_REPORT(53,"DFO chief approved the report"), //done

    AM_SHARE_TO_GDQ_AC_COMMITTIE(54 , "Report shared to GDQ AC Committie "), //done

    GDQ_AC_COMMITTIE_MEMBER_UPDATE_COMMENT(55 , "GDQ AC Committie members feedback updated"), //done

    AM_UPDATE_EVALUATION_AS_PER_GDQ_COMMENT(56, "AM updated the evaluation report as per GDQ AC Committie feedback"), //done

    GDQ_AC_COMMITTIE_APPROVED(57 , "GDQ AC Committie approved the report"), //done


    AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED(58 , "Factual accuracy started with defered status and the dealine is -date"), //done

    INSTITUTION_FULLFILL_THE_EVALUATION_CONDITIONS_ARE_PARTIALLY_MET(59,"Institute updated the documents for the partially met items"), //done

    AM_VERIFIED_THE_UPDATED_DOCUMENT(60,"Am verified the updated document"), //done

    ILEP_UPDATE_THE_EVALUATION_AS_LISTED_OR_NOTLISTED(64,"ILEP updated the evaluation report as Listed/Not listed"),




    DOC_SHARED_TO_QAC_BY_AM(65 , "Shared doc to QAC By AM"), //done

    QAC_FEEDBACK_SUBMITTED_UPDATED_AM(66 , "AM updated the QAC members feedback"), //done

    DFO_DIRECTOR_UPDATE_EVALUATION_FORM_AS_PER_QAC(67, "DFO Director updated the evaluation report as per QAC members feedback"), //done

    DFO_ADMIN_CHANGE_STATUS_QAC_COMMITTE_APPROVED(68, "DFO Admin -change status to QAC Committie approved and copy shared to Institute"), //done

    AM_FACTUAL_ACCURACY_WITH_NOT_LISTED(69,"Factual accuracy started with Not listed"), //done

    AM_FACTUAL_ACCURACY_WITH_LISTED(70,"Factual accuracy started with  listed"), //done

    INSTITUTION_COMMENTS_ADDED(71 , "Institution commented for factual accuracy listed/notlisted"), //done

    AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED(72, "Factual accuracy completed,  Listed"), //done

    AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED(73, "Factual accuracy completed,  Not Listed"), //done

    INSTITUTION_INITIATE_AN_APPEAL_5_DAYS(74, "Institution initiate an appeal with in 5 days"), //done

    DFO_DIRECTOR_APPROVE_THE_APPEAL(75,"Dfo director approve the appeal"), //done

    DFO_DIRECTOR_REJECTED_THE_APPEAL(76, "Dfo director rejected the request"), //done

    DOC_SHARED_TO_NAC(77 , "Shared doc to NAC"), //done

    NAC_DOC_APPROVED_BY_DFO_ADMIN(78 , "Report approved by NAC"), //done


    MCU_DOC_GENERATED(79 , "MCU document generated with serial number"), //done

    MCO_UPLOADED_EDITED_DOC(80 , "MCO uploaded the report and shared to DFO chief"), //done

    DFO_CHIEF_ADD_COMMENT_BACK_TO_MCO(81 , "DFO chief added comments"),//done

    MCO_RE_UPLOAD_THE_EDITED_REPORT(82, "MCO uploaded the report and shared to DFO chief"), //done

    DFO_CHIEF_SIGNED_OFF_AND_UPLOAD_THE_REPORT(83, "DFO Chief - signed off and uploaded the report."), //done

    DFO_CHIEF_SHARED_TO_CABINET(84, "DFO chief shared report to Cabinet"), //done

    DFO_ADMIN_APPROVE_AS_CABINET_APPROVE(85,"DFO Admin will update as Cabinet Approved- and share a copy to Institution as a status as listed"),



    /**--------------------------------------------------------------------------------------------------------------------------------*/








    AM_REQUESTED_FOR_ADDITIONAL_DATA_TO_RESUBMIT(48 , "AM - Requeted for additional data to re-submit the application"),

    INSTITUTION_USER_RE_SUBMIT_DOCUMENTS(49, "Institution user- submitted the re-submit documents"),

    INSTITUTION_DOCUMENT_SUBMIT_DATE_EXPIRED(50, "Re-submit documents date expired"),


    AM_APPROVES_EVALUATION_FORM(51 , "AM approves the report"),

    DFO_APPROVES_EVALUATION_FORM(52 , "DFO approves the report"),

    QAC_APPROVES_EVALUATION_FORM(53 , "QAC approves the report"),



    QAC_FEEDBACK_SUBMITTED(55 , "QAC panel feedback submitted"),




    FACTUAL_ACCURACY_NEEDED(57 , "Factual accuracy required"),

    FACTUAL_ACCURACY_SUBMITTED(58,"Factual accuracy submitted"),

    QAC_REPORT_SUBMITTED(59 , "QAC Report submitted"),










    DFO_DIRECTOR_DOC_APPROVED(64 , "Doc approved by DFO Director"),














    ;





    private final Integer code;
    private final String name;


    public static ApplicationStatusBackup4 getByCode(Integer code) {
        for (ApplicationStatusBackup4 applicationStatus : values()) {
            if (code == applicationStatus.getCode()) {
                return applicationStatus;
            }
        }
        return null;
    }

}