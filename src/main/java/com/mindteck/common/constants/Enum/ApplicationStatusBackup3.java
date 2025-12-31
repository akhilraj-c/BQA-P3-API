package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatusBackup3 {

    INITIAL_STATUS( 0 , "Initial Status"), //done
    INSTITUTION_REGISTERED(1,"Institution registered"), //done
    BQA_ADMIN_SHORTLISTED(2, "BQA admin shortlisted the application"),
    BQA_ADMIN_REJECTED(3 , "BQA admin rejected the application"), //done
    BQA_MOVED_MOVED(4, "BQA admin moved the application"), //done
    BQA_ADMIN_APPROVED(5, "BQA admin approved -and listed - By BQA Admin and send mail to all institutions  (scheduled)"), //done

    BQA_ADMIN_APPROVED_UPLOAD_DOCUMENT(6, "BQA admin approved - Institution user uploading documents for administrative check"), //done

    FAILED_TO_SUBMIT(7 , "Institution failed to submit the application on time"),

    INSTITUTION_SUBMITTED(8 , "Institution user submitted application for administrative check"), //done


    ASSIGNED_APPLICATION_MANAGER(9, "Application manager assigned - application_manager : Application - assigned an AM - amname"), //done

    ADMINISTRATIVE_CHECk_COMPLETED(10 , "Administrative check completed - Administrative check completed"), //done
    REQUIRED_ADDITION_DATA(11 , "Incomplete"), //done

    PROCEED_WITH_INCOMPLETE_STATUS(12,"Admin Check(Sent)"),
    APPLICATION_MANAGER_REJECTED(13 , "Application manager rejected the application"),

    APPLICATION_MANAGER_ACKNOWLEDGEMENT(14 , "Application Acknowledgement"),

    INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK(15, "Application manager requested for additional data : Inst. user re-submitted the documents for administrative check"), //done

    ILEP_PANEL_CREATED(16 , "Application manager created Evaluation Panel- AM assigned Evaluation Panel and submitted for Director approval "), //done

    DIRECTOR_APPROVED_ILEP_PANEL(17, "Application manager created Evaluation Panel -Director approved the Evaluation Panel"), //done


    /**------------------------- keeping 8 status for future------------------------------------------------------------------------------------*/

    EVALUATION_CONFLICT_OF_INTEREST_INSTITUTION(25, "Evaluation (Conflict of Interest- Institution)"),

     INSTITUTION_ILEP_PANEL_NON_CONFLICT(26 , "Application manager created ilep panel - Institution user signed the non conflict against Evaluation Panel -0/3 "), //done

    INSTITUTION_ILEP_PANEL_CONFLICT(27 , "Application manager created ilep panel - Institution user signed the conflict against Evaluation Panel -0/3 "), //done

    INSTITUTION_ILEP_PANEL_CONFLICT_AM_APPROVE(28 , "AM approve the ilep conflict raised by institution"), //done

    INSTITUTION_ILEP_PANEL_CONFLICT_DFO_APPROVE(29 , "DFO approve the ilep conflict raised by institution after AM approve"), //done

    ILEP_PANEL_USER_1_INSTITUTION_CONFLICT(30, "Ilep user 1 conflict"), //done
    ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT(31, "Ilep user 1 non-conflict"), //done

    ILEP_PANEL_USER_2_INSTITUTION_CONFLICT(32, "Ilep user 2 conflict"), //done
    ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT(33, "Ilep user 2 non-conflict"), //done

    ILEP_PANEL_USER_3_INSTITUTION_CONFLICT(34, "Ilep user 3 conflict"), //done
    ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT(35, "Ilep user 3 non-conflict"), //done

    ILEP_PANEL_ASSIGNED(36 , "Ilep panel assigned"), //done

    /**---------------------------------------------keeping 10 status for future--------------------------------------------------------------*/

    ACCESS_GRANT_ILEP_1(46 , "Grant Access Ilep 1"), //done
    ACCESS_GRANT_ILEP_2(47 , "Grant Access  Ilep 2"), //done
    ACCESS_GRANT_ILEP_3(48 , "Grant Access  Ilep 3"), //done
    ACCESS_GRANT(49 , "Grant Access"), // keeping for clarification in code change , should remove after code update

/**--------------------------------------------------keeping 10 status for future-----------------------------------------------------------------------------------*/
    MEETING_CREATED(60 , "Meeting create by Application manager"), //done

    MEETING_COMPLETED(61 , "Meeting completed and Mom uploaded"), //done

    SECOND_MEETING_CREATED(62, "Evaluation (Second evaluation Meeting)"),

    SECOND_MEETING_COMPLETED(63, "Evaluation (Second evaluation Meeting completed)"),


    /**-----------------------------------------------keeping 10 status for future ------------------------------------------*/

    /**
     * sub status starts
     *
     */

    ILEP_EVALUATE_LISTED(74, "Ilep member evaluation met / Listed"), //done
    ILEP_EVALUATE_DEFERRED(75 , "ILEP members  evaluation partially met / deferred "), //done

    ILEP_EVALUATE_NOT_LISTED(76 , "ILEP members  evaluation not met / not listed "), //done

    /**-----------------------------------------------keeping 10 status for future -------------------------------------------------------------*/

    SITE_VISIT_REQUIRED(86 , "Site visit required"), //done

    SITE_VISIT_NOT_REQUIRED(87 , "Site visit not required"), //done

    /**------------------------------------------------keeping 10 status for future --------------------------------------------------------*/

    SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE(98, "Institution user accepted the site visit"), //done

    SITE_VISIT_DATE_EXTENSION_REQUESTED(99 , "Meeting completed and Mom uploaded : Date extension requested for the site visit"), //done

    SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST(100 , "Meeting completed and Mom uploaded - AM approved the site visit date change"), //done

    SITE_VISIT_AM_REJECT_DATE_AND_SELECT_NEW_DATE(101 , "Meeting completed and Mom uploaded - AM reject the date change request and add new date"),

    SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE(102 , "Meeting completed and Mom uploaded - AM changes the site visit date"), //done

    INSTITUTION_UPDATES_SITE_VISIT_DOCUMENT(103 , "Institution updates the site visit document "), //done

    /**------------------------------------------------- keeping 10 status for future ----------------------------------------------------*/



    SIGN_NON_CONFIDENTIAL(114 , "Sign non-confidential"), //done

    NOT_SIGN_NON_CONFIDENTIAL(115 , "Not Sign non-confidential"), //done

    SITE_VISIT_DONE_AND_REPORT_UPLOAD(116 , "Site visit done and report uploaded"), //done


  //  MEETING_COMPLETED_AND_MOM_UPLOADED(42, "Meeting completed and Mom uploaded - AM - Meeting completed & MOM uploaded"),

    /**
     * sub status ends
     */

    /**------------------------------------------------- keeping 10 status for future ----------------------------------------------------*/


    ILEP_EVALUATION_SUBMITTED(126, "ILEP Evaluation submitted to AM"), //done

    AM_EVALUATE_SUBMISSION_WITH_COMMENT(127,"AM Evaluate the Submission and revert with comments"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/


    AM_APPROVE_THE_EVALUATION(133,"AM approved the evaluation and submitted (status)"), //done

    DFO_CHIEF_ADD_EVALUATION(134,"DFO chief added evaluation comments"), //done

    AM_EDIT_EVALUATION_BASED_ON_DFO_CHIEF_COMMENT(135, "AM updated the evaluation report as per DFO chief comments"), //done

    DFO_CHIEF_ADD_COMMENTS_AGAIN(136, "DFO chief add comments again"), //done

    DFO_CHIEF_APPROVED_THE_REPORT(137,"DFO chief approved the report"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/
    AM_SHARE_TO_GDQ_AC_COMMITTIE(143 , "Report shared to GDQ AC Committie "), //done

    GDQ_AC_COMMITTIE_MEMBER_UPDATE_COMMENT(144 , "GDQ AC Committie members feedback updated"), //done

    AM_UPDATE_EVALUATION_AS_PER_GDQ_COMMENT(145, "AM updated the evaluation report as per GDQ AC Committie feedback"), //done

    GDQ_AC_COMMITTIE_APPROVED(146 , "GDQ AC Committie approved the report"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/


    AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED(152 , "Factual accuracy started with defered status and the dealine is -date"), //done

    INSTITUTION_FULLFILL_THE_EVALUATION_CONDITIONS_ARE_PARTIALLY_MET(153,"Institute updated the documents for the partially met items"), //done

    AM_VERIFIED_THE_UPDATED_DOCUMENT(154,"Am verified the updated document"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    ILEP_UPDATE_THE_EVALUATION_AS_LISTED_OR_NOTLISTED(160,"ILEP updated the evaluation report as Listed/Not listed"),


    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    DOC_SHARED_TO_QAC_BY_AM(166 , "Shared doc to QAC By AM"), //done

    QAC_FEEDBACK_SUBMITTED_UPDATED_AM(167 , "AM updated the QAC members feedback"), //done

    DFO_DIRECTOR_UPDATE_EVALUATION_FORM_AS_PER_QAC(168, "DFO Director updated the evaluation report as per QAC members feedback"), //done

    DFO_ADMIN_CHANGE_STATUS_QAC_COMMITTE_APPROVED(169, "DFO Admin -change status to QAC Committie approved and copy shared to Institute"), //done

    /**------------------------------------------------- keeping 10 status for future ----------------------------------------------------*/

    AM_FACTUAL_ACCURACY_WITH_NOT_LISTED(180,"Factual accuracy started with Not listed"), //done

    AM_FACTUAL_ACCURACY_WITH_LISTED(181,"Factual accuracy started with  listed"), //done

    INSTITUTION_COMMENTS_ADDED(182 , "Institution commented for factual accuracy listed/notlisted"), //done

    AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED(183, "Factual accuracy completed,  Listed"), //done

    AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED(184, "Factual accuracy completed,  Not Listed"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    INSTITUTION_INITIATE_AN_APPEAL_5_DAYS(190, "Institution initiate an appeal with in 5 days"), //done

    DFO_DIRECTOR_APPROVE_THE_APPEAL(191,"Dfo director approve the appeal"), //done

    DFO_DIRECTOR_REJECTED_THE_APPEAL(192, "Dfo director rejected the request"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    DOC_SHARED_TO_NAC(198 , "Shared doc to NAC"), //done

    NAC_DOC_APPROVED_BY_DFO_ADMIN(199 , "Report approved by NAC"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/


    MCU_DOC_GENERATED(205 , "MCU document generated with serial number"), //done

    MCO_UPLOADED_EDITED_DOC(206 , "MCO uploaded the report and shared to DFO chief"), //done

    DFO_CHIEF_ADD_COMMENT_BACK_TO_MCO(207 , "DFO chief added comments"),//done

    MCO_RE_UPLOAD_THE_EDITED_REPORT(208, "MCO uploaded the report and shared to DFO chief"), //done

    DFO_CHIEF_SIGNED_OFF_AND_UPLOAD_THE_REPORT(209, "DFO Chief - signed off and uploaded the report."), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    DFO_CHIEF_SHARED_TO_CABINET(205, "DFO chief shared report to Cabinet"), //done

    DFO_ADMIN_APPROVE_AS_CABINET_APPROVE(206,"DFO Admin will update as Cabinet Approved- and share a copy to Institution as a status as listed"),



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


    public static ApplicationStatusBackup3 getByCode(Integer code) {
        for (ApplicationStatusBackup3 applicationStatus : values()) {
            if (code == applicationStatus.getCode()) {
                return applicationStatus;
            }
        }
        return null;
    }

}