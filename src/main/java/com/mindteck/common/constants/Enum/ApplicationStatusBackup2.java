package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatusBackup2 {

    INITIAL_STATUS( 0 , "Initial Status"),
    BQA_ADMIN_SHORTLISTED(1, "BQA admin shortlisted the application"),
    BQA_ADMIN_REJECTED(2 , "BQA admin rejected the application"),
    BQA_MOVED_MOVED(3, "BQA admin moved the application"),
    BQA_ADMIN_APPROVED(4, "BQA admin approved -and listed - By BQA Admin and send mail to all institutions "),

    BQA_ADMIN_APPROVED_UPLOAD_DOCUMENT(5, "BQA admin approved - Institution user uploading documents for administrative check"),


    FAILED_TO_SUBMIT(6 , "Institution failed to submit the application on time"),

    INSTITUTION_SUBMITTED(7 , "Institution user submitted application for administrative check"),

    ASSIGNED_APPLICATION_MANAGER(8, "Application manager assigned - application_manager : Application - assigned an AM - amname"),

    REQUIRED_ADDITION_DATA(9 , "Application manager requested for additional data : AM administrative check completed - Incomplete"),

    INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK(10, "Application manager requested for additional data : Inst. user re-submitted the documents for administrative check"),

    ADMINISTRATIVE_CHECk_COMPLETED(11 , "Administrative check completed - Administrative check completed"),

    APPLICATION_MANAGER_REJECTED(12 , "Application manager rejected the application"),

    ILEP_PANEL_CREATED(13 , "Application manager created Evaluation Panel- AM assigned Evaluation Panel and submitted for Director approval "),

    DIRECTOR_APPROVED_ILEP_PANEL(14, "Application manager created Evaluation Panel -Director approved the Evaluation Panel"),

    INSTITUTION_ILEP_PANEL_CONFLICT(15 , "Application manager created ilep panel - Institution user signed the conflict against Evaluation Panel -0/3 "),

    INSTITUTION_ILEP_PANEL_CONFLICT_AM_APPROVE(16 , "AM approve the ilep conflict raised by institution"),

    INSTITUTION_ILEP_PANEL_CONFLICT_DFO_APPROVE(17 , "DFO approve the ilep conflict raised by institution after AM approve"),

    INSTITUTION_ILEP_PANEL_NON_CONFLICT(18 , "Application manager created ilep panel - Institution user signed the non conflict against Evaluation Panel -0/3 "),
    ILEP_PANEL_USER_1_INSTITUTION_CONFLICT(19, "Ilep user 1 conflict"),
    ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT(20, "Ilep user 1 non-conflict"),

    ILEP_PANEL_USER_2_INSTITUTION_CONFLICT(21, "Ilep user 2 conflict"),
    ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT(22, "Ilep user 2 non-conflict"),

    ILEP_PANEL_USER_3_INSTITUTION_CONFLICT(23, "Ilep user 3 conflict"),
    ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT(24, "Ilep user 3 non-conflict"),

    ILEP_PANEL_ASSIGNED(25 , "Ilep panel assigned"),

    ACCESS_GRANT(26 , "Grant Access"),

    MEETING_CREATED(27 , "Meeting create by Application manager"),

    MEETING_COMPLETED(28 , "Meeting completed and Mom uploaded"),





    /**
     * sub status starts
     *
     */

    ILEP_EVALUATE_LISTED(29, "Ilep member evaluation met / Listed"),
    ILEP_EVALUATE_DEFERRED(30 , "ILEP members  evaluation partially met / deferred "),

    ILEP_EVALUATE_NOT_LISTED(31 , "ILEP members  evaluation not met / not listed "),


    SITE_VISIT_REQUIRED(32 , "Site visit required"),

    SITE_VISIT_NOT_REQUIRED(33 , "Site visit not required"),

    SITE_VISIT_DATE_EXTENSION_REQUESTED(34 , "Meeting completed and Mom uploaded : Date extension requested for the site visit"),

    SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE(35, "Institution user accepted the site visit"),

    SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST(36 , "Meeting completed and Mom uploaded - AM approved the site visit date change"),

    SITE_VISIT_AM_FINALIZE_THE_DATE_DATE(37 , "Meeting completed and Mom uploaded - AM finalised the site visit date"),

    INSTITUTION_UPDATES_SITE_VISIT_DOCUMENT(38 , "Institution updates the site visit document "),

    SIGN_NON_CONFIDENTIAL(39 , "Sign non-confidential"),


    MEETING_HELD_AFTER_SIGNING_NON_CONFIDENTIAL(41 , "Meeting created by Application manager- AM created meeting schedule"),

    MEETING_COMPLETED_AND_MOM_UPLOADED(42, "Meeting completed and Mom uploaded - AM - Meeting completed & MOM uploaded"),

    GDQ_DOC_SHARED(43 , "Meeting completed and Mom uploaded : GDQ shared Doc "),

    GDQ_DOC_UPLOADED(44 , "Meeting completed and Mom uploaded - GDQ Documents uploaded"),
    GDQ_AC_REVIEWED_COMPLETED(46 , "Meeting completed and Mom uploaded - GDQ Review Completed"),

    /**
     * sub status ends
     */


    AM_REQUESTED_FOR_ADDITIONAL_DATA_TO_RESUBMIT(48 , "AM - Requeted for additional data to re-submit the application"),

    INSTITUTION_USER_RE_SUBMIT_DOCUMENTS(49, "Institution user- submitted the re-submit documents"),

    INSTITUTION_DOCUMENT_SUBMIT_DATE_EXPIRED(50, "Re-submit documents date expired"),


    AM_APPROVES_EVALUATION_FORM(51 , "AM approves the report"),

    DFO_APPROVES_EVALUATION_FORM(52 , "DFO approves the report"),

    QAC_APPROVES_EVALUATION_FORM(53 , "QAC approves the report"),

    QAC_DOC_SHARED(54 , "Shared doc to QAC"),

    QAC_FEEDBACK_SUBMITTED(55 , "QAC panel feedback submitted"),


    INSTITUTION_COMMENTS_ADDED(56 , "Comments updated by institution"),

    FACTUAL_ACCURACY_NEEDED(57 , "Factual accuracy required"),

    FACTUAL_ACCURACY_SUBMITTED(58,"Factual accuracy submitted"),

    QAC_REPORT_SUBMITTED(59 , "QAC Report submitted"),

    NAC_DOC_SHARED(60 , "Shared doc to NAC"),

    NAC_DOC_APPROVED(61 , "NAC panel feedback updated"),

    MCU_DOC_GENERATED(62 , "MCU document generated with serial number"),

    SCANNED_DOCUMENT_COMPLETED(63 , "MCU generated document updated and uploaded - doc upadte view"),

    DFO_DIRECTOR_DOC_APPROVED(64 , "Doc approved by DFO Director"),














    ;





    private final Integer code;
    private final String name;


    public static ApplicationStatusBackup2 getByCode(Integer code) {
        for (ApplicationStatusBackup2 applicationStatus : values()) {
            if (code == applicationStatus.getCode()) {
                return applicationStatus;
            }
        }
        return null;
    }

}
