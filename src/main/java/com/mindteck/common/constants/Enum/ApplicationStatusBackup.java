package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationStatusBackup {

    INITIAL_STATUS( 0 , "Initial Status"),
    BQA_ADMIN_SHORTLISTED(1, "BQA admin shortlisted the application"),
    BQA_ADMIN_REJECTED(2 , "BQA admin rejected the application"),
    BQA_MOVED_MOVED(3, "BQA admin moved the application"),
    BQA_ADMIN_APPROVED(4, "BQA admin approved and send mail to all institutions"),

    FAILED_TO_SUBMIT(5 , "Institution failed to submit the application on time"),

    INSTITUTION_SUBMITTED(6 , "Institution successfully submitted the application"),

    ADMINISTRATIVE_CHECk_COMPLETED(7 , "Application manager completed the application evaluation"),

    REQUIRED_ADDITION_DATA(8 , "Application manager requested for additional data"),

    APPLICATION_MANAGER_REJECTED(9 , "Application manager rejected the application"),

    ILEP_PANEL_CREATED(10 , "Application manager created ilep panel"),

    ILEP_PANEL_CONFLICT(11 , "Institution or ilep create conflict"),

    ILEP_PANEL_ASSIGNED(12 , "Ilep panel assigned"),

    ACCESS_GRANT(13 , "Grant Access"),

    MEETING_CREATED(14 , "Meeting create by Application manager"),

    MEETING_COMPLETED(15 , "Meeting completed and Mom uploaded"),

    ILEP_EVELUATE_PARTIAL_MET(16, "Ilep member evaluation partially met"),
    ILEP_EVALUATE_SUB_SECTION(17 , "ILEP members evaluate the Application"),

    /**
     * sub status starts
     */
    SITE_VISIT_REQUIRED(18 , "Site visit required"),

    SITE_VISIT_DATE_EXTENSION_REQUESTED(19 , "Date extension requested for the site visit"),

    SIGN_NON_CONFIDENTIAL(20 , "Sign non-confidential"),

    SITE_VISIT_NOT_REQUIRED(21 , "Site visit not required"),

    INSTITUTION_UPDATES_SITE_VISIT_DOCUMENT(22 , "Institution updates the site visit document "),

    MEETING_HELD_AFTER_SIGNING_NON_CONFIDENTIAL(24 , "Meeting held after the non-confidential agreement signed"),


    ILEP_UPDATE_EVALUATION_FORM(25 , "ILEP members evaluate the Application "),


    GDQ_DOC_SHARED(26 , "GDQ shared Doc "),

    GDQ_AC_REVIEWED_COMPLETED(27 , "GDQ AC review completed"),

    /**
     * sub status ends
     */

    AM_APPROVES_EVALUATION_FORM(28 , "AM approves the report"),

    DFO_APPROVES_EVALUATION_FORM(29 , "DFO approves the report"),

    QAC_APPROVES_EVALUATION_FORM(33 , "QAC approves the report"),

    QAC_DOC_SHARED(34 , "Shared doc to QAC"),

    QAC_FEEDBACK_SUBMITTED(35 , "QAC panel feedback submitted"),

    INSTITUTION_COMMENTS_ADDED(36 , "Comments updated by institution"),

    REPORT_SUBMITTED(37 , "Report submitted"),

    NAC_DOC_SHARED(38 , "Shared doc to NAC"),

    NAC_DOC_APPROVED(39 , "Doc approved by NAC"),

    MCU_DOC_GENERATED(40 , "Document generated using serial number"),

    SCANNED_DOCUMENT_COMPLETED(41 , "Document Scanning completed"),

    DFO_DIRECTOR_DOC_APPROVED(42 , "Doc approved by DFO Director"),

    ;





    private final Integer code;
    private final String name;


    public static ApplicationStatusBackup getByCode(Integer code) {
        for (ApplicationStatusBackup applicationStatus : values()) {
            if (code == applicationStatus.getCode()) {
                return applicationStatus;
            }
        }
        return null;
    }

}
