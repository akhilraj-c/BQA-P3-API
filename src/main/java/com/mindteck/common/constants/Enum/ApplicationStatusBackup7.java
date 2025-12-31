package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatusBackup7 {

    INITIAL_STATUS( 0 , "Initial Status"), //done
    INSTITUTION_REGISTERED(1,"Institution complete and submit the registration form"), //done
    DFO_ADMIN_SHORTLISTED(2, "DFO Admin short listed the application"),
    DFO_ADMIN_REJECTED(3 , "DFO Admin rejected the registration and did not schedule the application"), //done
    DFO_ADMIN_MOVED(4, "DFO Admin moved the application"), //done
    DFO_ADMIN_APPROVED(5, "DFO Admin add a submission date for the application submission and approve"), //done
    INSTITUTION_UPLOAD_DOCUMENT(6, "Institution user uploading documents for administrative check"), //done
    FAILED_TO_SUBMIT(7 , "Application was not submitted in the scheduled date"), //need to implement

    INSTITUTION_SUBMITTED(8 , "Application is submitted by the institution"), //done

    ASSIGNED_APPLICATION_MANAGER(9, "Received Application is assigned with An Application Manager (AM)"), //done

    AM_STARTED_ADMIN_CHECK(10 , "Am started the admin check"),

    ADMINISTRATIVE_CHECk_COMPLETED(11 , "AM completed and saved the Admin Check with complete status"), //done
    REQUIRED_ADDITION_DATA(12 , "Required additional data or Incomplete"), //done

    PROCEED_WITH_INCOMPLETE_STATUS(13,"AM completed and saved the Admin Check with incomplete status"),
    APPLICATION_MANAGER_REJECTED(14 , "AM rejected the application because the admin check requirements are not sufficiently addressed"),

    APPLICATION_MANAGER_ACKNOWLEDGEMENT(15 , "Application Acknowledgement"),  //need to implementy

    INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK(16, "AM received the Admin Check from the institution- resubmit"), //done

    ILEP_PANEL_CREATED(17 , "AM assign the application with proposed ILEP Panel "), //done

    AM_APPROVED_ILEP_PANEL(18, "Application manager created Evaluation Panel -AM approved the Evaluation Panel"), //need to remove


    /**------------------------- keeping 7 status for future------------------------------------------------------------------------------------*/

    EVALUATION_CONFLICT_OF_INTEREST_INSTITUTION(25, "Institute declare conflict"), // not using

    INSTITUTION_ILEP_PANEL_NON_CONFLICT(26 , "Institute declare no-conflict "), //done

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

    FIRST_MEETING_CREATED(60 , "AM set the date and time of the first meeting"), //done

    FIRST_MEETING_DOC_UPLOADED(61 , "First Meeting completed and documents uploaded"), //done

    FIRST_MEETING_REPORT_UPLOADED(62, "First meeting report, question and evidence uploaded (ILEP upload the 1st meeting documents)"),

    SECOND_MEETING_CREATED(63 , "AM set the date and time of the second meeting"), //done

    SECOND_MEETING_REPORT_UPLOADED(64, "AM upload the report draft of the second meeting"),



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
    AM_UPLOAD_AGENDA_FORM(97 , "AM or ILEP upload the agenda form (ILEP upload documents)"),

    SIGN_NON_CONFIDENTIAL(98 , "Sign non-confidential"), //done

    NOT_SIGN_NON_CONFIDENTIAL(99 , "Not Sign non-confidential"), //done

    INSTITUTION_UPDATES_AGENDA_FORM(110 , "Institution updates the agenda form with comments (Institute upload edited documents)"), //done

    AM_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE(111, "AM evaluate extra evidence files uploaded by Institute  by adding comments in file (Assuming as agenda form)"),

    ILEP_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE(112, "ILEP evaluate extra evidence files uploaded by Institute  as  report for AM/ILEP view"),

    AM_REJECT_SITE_VISIT_DOCUMENT(113,"Am reject the site visit document with comments"),
    SITE_VISIT_REPORT_UPLOAD(114 , "AM uploaded the spoken feedback after the site visit and mark as done"), //done
    SITE_VISIT_DONE(115 , "AM uploaded the spoken feedback after the site visit and mark as done"), //done

    SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE(116 , "AM changes the site visit date"), //done

    /**
     * Flow removed sep 16 13:21
     */

    SITE_VISIT_DONE_AND_REPORT_UPLOAD(112 , "AM uploaded the spoken feedback after the site visit and mark as done"), //done


    /********************************/


    /**
     * Flow removed
     * */

    SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE(0, "Institution user accepted the site visit"), // removed

    SITE_VISIT_DATE_EXTENSION_REQUESTED(0 , "Meeting completed and Mom uploaded : Date extension requested for the site visit"), //removed

    SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST(0 , "Meeting completed and Mom uploaded - AM approved the site visit date change"), //removed

    /******************************************************************************/






    /**------------------------------------------------- keeping 10 status for future ----------------------------------------------------*/








    //  MEETING_COMPLETED_AND_MOM_UPLOADED(42, "Meeting completed and Mom uploaded - AM - Meeting completed & MOM uploaded"),

    /**
     * sub status ends
     */

    /**------------------------------------------------- keeping 10 status for future ----------------------------------------------------*/


    ILEP_EVALUATION_SUBMITTED(126, "AM receive the report from the Panel and start editing (ILEP submitted the report)"), //done

    AM_EVALUATE_SUBMISSION_WITH_COMMENT(127,"AM Evaluate the Submission and revert with comments(AM send back to ILEP)"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/


    AM_APPROVE_THE_EVALUATION(133,"AM approved the evaluation and submitted (status)"), //done

    DFO_CHIEF_ADD_EVALUATION(134,"DFO chief added evaluation comments (Relevant Chief receive the report from the Panel and start editing)"), //done

    AM_EDIT_EVALUATION_BASED_ON_DFO_CHIEF_COMMENT(135, "AM updated the evaluation report as per DFO chief comments"), //done

    DFO_CHIEF_ADD_COMMENTS_AGAIN(136, "DFO chief add comments again"), //done

    DFO_CHIEF_APPROVED_THE_REPORT(137,"Report is sent to the Panel for confirmation after the AM and Chief edits"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/
    AM_SHARE_TO_GDQ_AC_COMMITTIE(143 , "Report is sent to the GDQ AC for approval by the relevant chief"), //done

    GDQ_AC_COMMITTIE_MEMBER_UPDATE_COMMENT(144 , "GDQ AC Committie members feedback updated"), //done

    AM_UPDATE_EVALUATION_AS_PER_GDQ_COMMENT(145, "AM updated the evaluation report as per GDQ AC Committie feedback"), //done

    GDQ_AC_COMMITTIE_APPROVED(146 , "The relevant chief tag the report as approved by the GDQ AC"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/


    AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED(152 , "Factual accuracy started with defered status and the dealine is -date (AM/DFO admin starts the condition fulfillment)"), //done

    INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS(153,"Institution re-submits the required documents"), //done

    AM_COMPLETE_ADMIN_CHECK_FOR_SUBMITTED_DOCUMENTS(154,"AM completes the admin check for condition fulfilled documents"), //done

    AM_REJECTED_THE_DOCUMENT(155, "Am reject application on document update"),

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    ILEP_UPDATE_THE_EVALUATION_AS_LISTED_OR_NOTLISTED(160,"ILEP updated the evaluation report as Listed/Not listed"),


    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    DOC_SHARED_TO_QAC_BY_DIRECTOR(166 , "Report is sent to the QAC for approval by the Director"), //done

    QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR(167 , "The Director tag the report as approved by the QAC"), //done

    DFO_DIRECTOR_UPDATE_EVALUATION_FORM_AS_PER_QAC(168, "DFO Director updated the evaluation report as per QAC members feedback"), //done

    DFO_ADMIN_CHANGE_STATUS_QAC_COMMITTE_APPROVED(169, "DFO Admin -change status to QAC Committie approved and copy shared to Institute"), //done

    /**------------------------------------------------- keeping 10 status for future ----------------------------------------------------*/

    AM_FACTUAL_ACCURACY_WITH_NOT_LISTED(180,"Factual accuracy started with Not listed (The AM/DFO Admin send the report along with profiles for factual accuracy)"), //done

    AM_FACTUAL_ACCURACY_WITH_LISTED(181,"Factual accuracy started with  listed (The AM/DFO Admin send the report along with profiles for factual accuracy)"), //done

    INSTITUTION_COMMENTS_ADDED(182 , "Institution commented for factual accuracy listed/notlisted  (Institue reports factual error or no error)"), //done

    AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED(183, "Factual accuracy completed,  Listed (AM/DFO admin updated the factual accuracy and factual accuracy completed)"), //done

    AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED(184, "Factual accuracy completed,  Not Listed (AM/DFO admin updated the factual accuracy and factual accuracy completed)"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    INSTITUTION_INITIATE_AN_APPEAL_5_DAYS(190, "Institution initiate an appeal with in 5 days"), //done

    DFO_DIRECTOR_APPROVE_THE_APPEAL(191,"Dfo director approve the appeal"), //done

    DFO_DIRECTOR_REJECTED_THE_APPEAL(192, "Dfo director rejected the request"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    DOC_SHARED_TO_NAC(198 , "Report is sent to the NAC for approval by the DFO Admin"), //done

    NAC_DOC_APPROVED_BY_DFO_ADMIN(199 , "The DFO admin tag the report as approved by the NAC"), //done

    /**------------------------------------------------- keeping 5 status for future ----------------------------------------------------*/

    DFO_CHIEF_SHARED_TO_CABINET(205, "DFO chief shared report to Cabinet"), //done

    DFO_ADMIN_APPROVE_AS_CABINET_APPROVE(206,"DFO Admin will update as Cabinet Approved- and share a copy to Institution as a status as listed"),


    /**
     * Unsaved status for timeLine purpose
     */

     INSTITUTION_RESET(1001 , "Conflict reset by institution"),
    ILEP_RESET(1002 , "Conflict reset by Ilep"),

    INST_DATE_EXT_REQ_COMPLETE_REG(1011, "Institution requested date extension for complete registration"),

    DATE_EXT_APPROVED_FOR_REG(1012,"Date extension request for completing registration approved"),

    DATE_EXT_REJECTED_FOR_REG(1013,"Date extension request for completing registration rejected"),

    INST_DATE_EXT_REQ_RESUBMIT_DOC(1014, "Institution requested date extension for re-submitting documents"),

    DATE_EXT_APPROVED_FOR_RESUBMIT_DOC(1015,"Date extension request  for re-submitting documents approved"),

    DATE_EXT_REJECTED_FOR_RESUBMIT_DOC(1016,"Date extension request for re-submitting documents rejected"),



    /**************************************************/






    /**
     *flow removed on Aug 19 2023, keeping status for resolving confusion
     */

    MCU_DOC_GENERATED(0 , "MCU document generated with serial number"), //removed

    MCO_UPLOADED_EDITED_DOC(0 , "MCO uploaded the report and shared to DFO chief"), //removed

    DFO_CHIEF_ADD_COMMENT_BACK_TO_MCO(0 , "DFO chief added comments"),//removed

    MCO_RE_UPLOAD_THE_EDITED_REPORT(0, "MCO uploaded the report and shared to DFO chief"), //removed

    DFO_CHIEF_SIGNED_OFF_AND_UPLOAD_THE_REPORT(0, "DFO Chief - signed off and uploaded the report."), //removed

    /*************************************************************************/




    AM_REQUESTED_FOR_ADDITIONAL_DATA_TO_RESUBMIT(0 , "AM - Requeted for additional data to re-submit the application"),

    INSTITUTION_USER_RE_SUBMIT_DOCUMENTS(0, "Institution user- submitted the re-submit documents"),

    INSTITUTION_DOCUMENT_SUBMIT_DATE_EXPIRED(0, "Re-submit documents date expired"),


    AM_APPROVES_EVALUATION_FORM(0 , "AM approves the report"),

    DFO_APPROVES_EVALUATION_FORM(0 , "DFO approves the report"),

    QAC_APPROVES_EVALUATION_FORM(0 , "QAC approves the report"),



    QAC_FEEDBACK_SUBMITTED(0 , "QAC panel feedback submitted"),




    FACTUAL_ACCURACY_NEEDED(0 , "Factual accuracy required"),

    FACTUAL_ACCURACY_SUBMITTED(0,"Factual accuracy submitted"),

    QAC_REPORT_SUBMITTED(0 , "QAC Report submitted"),










    DFO_DIRECTOR_DOC_APPROVED(0 , "Doc approved by DFO Director"),














    ;





    private final Integer code;
    private final String name;


    public static ApplicationStatusBackup7 getByCode(Integer code) {
        for (ApplicationStatusBackup7 applicationStatus : values()) {
            if (code.equals(applicationStatus.getCode())) {
                return applicationStatus;
            }
        }
        return null;
    }

}