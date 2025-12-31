package com.mindteck.common.constants.Enum;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ErrorCode {

    SUCCESS(HttpStatus.OK, 20001, "Success"),
    ERROR(HttpStatus.SERVICE_UNAVAILABLE, 20002, "Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "Bad request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40001, "Unauthorized access"),
    INVALID_HEADER(HttpStatus.UNAUTHORIZED, 40002, "Invalid auth header"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 40003, "Permission denied"),
    INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 40006, "Input validation failed"),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, 200003, "Data not found"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 40004,
            "Please try again network issue observed"),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, 200003, "Email not found . Please provide a valid Email"),

    INVALID_FORMAT(HttpStatus.BAD_REQUEST, 40005,
            "Cannot deserialize value"),
    INPUT_FIELDS_EMPTY(HttpStatus.BAD_REQUEST, 40007,
            "Required properties of the request body are either missing or empty."),
    MAX_PROFILE_UPLOAD_SIZE(HttpStatus.BAD_REQUEST, 40008, "File size cannot exceed 250kb."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Internal Server Error"),

    USER_INACTIVE(HttpStatus.NOT_FOUND, 40009, "User is inactive. Please contact super admin"),

    //App registration validation
    APP_REGISTRATION_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200101,
            "Input validation failed"),
    APP_NOT_REGISTERED(HttpStatus.FORBIDDEN, 200102, "App not registered"),

    // Login validation
    LOGIN_INPUT_VALIDATION(HttpStatus.UNPROCESSABLE_ENTITY, 200201, "Input validation failed"),

    LOGIN_INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, 200202, "Invalid credentials"),

    LOGIN_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, 200203, "user not found"),

    LOGIN_APP_NOT_REGISTERED(HttpStatus.UNPROCESSABLE_ENTITY, 200204, "App not registered"),

    //    RESET password
    RESET_PASSWORD_INPUT_VALIDATION(HttpStatus.UNPROCESSABLE_ENTITY, 200401, "Input validation failed"),

    RESET_PASSWORD_APP_NOT_REGISTERED(HttpStatus.UNPROCESSABLE_ENTITY, 200402, "App not registered"),

    RESET_PASSWORD_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, 200403, "user not found"),

    RESET_PASSWORD_INVALID_OTP(HttpStatus.UNPROCESSABLE_ENTITY, 200404, "Invalid otp given"),

    RESET_PASSWORD_EXPIRED_OTP(HttpStatus.UNPROCESSABLE_ENTITY, 200405, "Expired otp"),

    //Forgot password validation
    FORGOT_PASSWORD_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200301,
            "Input validation failed"),

    //Refresh token validation
    REFRESH_TOKEN_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200501,
            "Input validation failed"),

    REFRESH_TOKEN_NOT_VALID(HttpStatus.UNPROCESSABLE_ENTITY, 200502, "Invalid token given"),

    LOGIN_USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, 200203, "user already registered"),

    LOGIN_USER_ALREADY_EXIST_WITH_SAME_EMAIL_AND_LICENSE(HttpStatus.BAD_REQUEST, 200203, "user already registered with same email and license number"),

    LOGIN_USER_ALREADY_EXIST_WITH_PLANNED_SUB_DATE_AND_LICENSE(HttpStatus.BAD_REQUEST, 200203, "user already registered with same Planned submission date and license number"),

    APPLICATION_SUBMISSION_NOT_PERMITTED(HttpStatus.BAD_REQUEST, 200204,
            "Institution already tried to register in current submission time"),

    USER_REGISTRATION_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200301,
            "Input validation failed"),

    APPLICATION_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 200302,
            "Application Not Found"),

    APPLICATION_MANAGER_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200303,
            "Input validation failed"),

    APPLICATION_MANAGER_EVALUATE_FORM_AM_NOT_ASSIGNED(HttpStatus.BAD_REQUEST, 200303,
            "Application manager not assigned"),

    //BQA admin change status
    BQA_UPDATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200601,
            "Input validation failed"),

    BQA_UPDATE_REGISTRATION_NOT_FOUND(HttpStatus.NOT_FOUND, 200602,
            "Invalid registration id"),
    BQA_UPDATE_ALREADY_APPROVED(HttpStatus.UNPROCESSABLE_ENTITY, 200603,
            "Institution already approved"),

    BQA_UPDATE_ALREADY_REJECTED(HttpStatus.UNPROCESSABLE_ENTITY, 200604,
            "Institution already rejected"),

    BQA_UPDATE_ALREADY_MOVED(HttpStatus.UNPROCESSABLE_ENTITY, 200605,
            "Institution already moved"),

    BQA_UPDATE_ALREADY_SHORTLISTED(HttpStatus.UNPROCESSABLE_ENTITY, 200606,
            "Institution already shortlisted"),
    BQA_UPDATE_ACTION_NOT_PERMITTED(HttpStatus.UNPROCESSABLE_ENTITY, 200607,
            "Action not permitted fot this user"),
    BQA_UPDATE_INVALID_USER(HttpStatus.BAD_REQUEST, 200608,
            "Action Not permitted for this user"),

    BQA_UPDATE_NOT_REGISTERED(HttpStatus.BAD_REQUEST, 200609,
            "Institution not registered"),
    // Assign application manager
    ASSIGN_APPLICATION_MANAGER_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200901,
            "Input validation failed"),
    ASSIGN_APPLICATION_MANAGER_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 200902,
            "Institute record not found"),
    ASSIGN_APPLICATION_MANAGER_ALREADY_ASSIGNED(HttpStatus.UNPROCESSABLE_ENTITY, 200903,
            "Institute record already assigned to application manager"),

    ASSIGN_APPLICATION_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, 200904,
            "Application manager not found"),

    ASSIGN_APPLICATION_MANAGER_INSTITUTE_FORM_NOT_SUBMITTED(HttpStatus.BAD_REQUEST, 200905,
            "Institution not submitted the application details"),



    ACTION_CANNOT_BE_PERFORMED(HttpStatus.BAD_REQUEST, 200906,
            "Action cannot be performed"),


    //GET APPLICATION DETAILS
    APPLICATION_DETAILS_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 200601,
            "Institute record not found"),

    APPLICATION_DETAILS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200602,
            "Input validation failed"),

    APPLICATION_DETAILS_INVALID_REQUEST_TYPE(HttpStatus.BAD_REQUEST, 200603,
            "Invalid request type given"),

    APPLICATION_DETAILS_NEW_EXTENSION(HttpStatus.BAD_REQUEST, 200604,
            "Date extension rejected , required new date"),


    //Date Extension Request
    DATE_EXTENSION_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201001,
            "Input validation failed"),
    DATE_EXTENSION_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 201002,
            "Form not found"),
    DATE_EXTENSION_INVALID_TYPE(HttpStatus.BAD_REQUEST, 201003,
            "Invalid type provided"),

    //Form listing for all users
    FORM_LIST_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201101,
            "Input validation failed"),
    FORM_LIST_INVALID_USER(HttpStatus.BAD_REQUEST, 201102,
            "Invalid user "),

    DATE_EXTENSION_APPROVAL_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200308,
            "Input validation failed"),

    //GET submission dates validation
    GET_SUBMISSION_DATES_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201901,
            "Input validation failed"),

    GET_SUBMISSION_DATES_INPUT_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, 201902,
            "No data"),
    //UPDATE MY FORM
    UPDATE_MY_FORM_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201201,
            "Input validation failed"),
    UPDATE_MY_FORM_INVALID_TYPE(HttpStatus.BAD_REQUEST, 201202,
            "Update form type invalid"),
    UPDATE_MY_FORM_INVALID_USER(HttpStatus.BAD_REQUEST, 201203,
            "User action not permitted"),
    UPDATE_MY_FORM_EDIT_NOT_PERMITTED(HttpStatus.BAD_REQUEST, 201204,
            "Update not permitted"),

    UPDATE_MY_FORM_RESUBMIT_DATE_EXPIRED(HttpStatus.BAD_REQUEST, 201205,
            "Resubmit of the document date expired"),

    UPDATE_MY_FORM_NOT_APPROVED(HttpStatus.BAD_REQUEST, 201206,
            "Registration not approved"),

    //APPLICATION MANAGER LIST
    APPLICATION_MANAGER_LIST_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201101,
            "Input validation failed"),


    //User Edit
    USER_EDIT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201401,
            "Input validation failed"),

    USER_EDIT_ACTION_NOT_PERMITTED(HttpStatus.UNPROCESSABLE_ENTITY, 201402,
            "User don't have permission"),


    //CREATE USER
    CREATE_USER_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201301,
            "Input validation failed"),
    CREATE_USER_ALREADY_EXIST(HttpStatus.UNPROCESSABLE_ENTITY, 201302,
            "User already exist"),

    USER_LIST_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201101,
            "Input validation failed"),

    ROLE_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 201103,
            "Role not found"),


    //Institution conflict action
    INSTITUTION_CONFLICT_ACTION_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201501,
            "Input validation failed"),

    INSTITUTION_CONFLICT_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 201502,
            "formUniqueId not found"),

    INSTITUTION_CONFLICT_ALREADY_SUBMITTED(HttpStatus.CONFLICT, 201503,
            "Institution already submitted the conflict"),

    INSTITUTION_CONFLICT_ILEP_PANEL_DFO_NOT_APPROVED(HttpStatus.BAD_REQUEST, 201504,
            "ILEP Panel not approved by DFO"),


    //Ilep conflict action
    ILEP_CONFLICT_ACTION_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201601,
            "Input validation failed"),
    ILEP_CONFLICT_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 201602,
            "formUniqueId not found"),
    ILEP_CONFLICT_INSTITUTION_CONFLICT_NOT_SUBMITTED(HttpStatus.NOT_FOUND, 201603,
            "Institution not submitted the conflict"),

    //ILEP PANEL create
    CREATE_ILEP_MEMBER_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201601,
            "Input validation failed"),
    CREATE_ILEP_MEMBER_INPUT_MEMBER_EMPTY(HttpStatus.UNPROCESSABLE_ENTITY, 201602,
            "This panel member has an existing conflict of interest. Please select a different panel member from the list."),

    CREATE_ILEP_MEMBER_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 201603,
            "Institute record not found"),

    CREATE_ILEP_MEMBER_USER_NOT_FOUND(HttpStatus.NOT_FOUND, 201604,
            "user not found"),

    CREATE_ILEP_MEMBER_INVALID_USER(HttpStatus.UNPROCESSABLE_ENTITY, 201605,
            "Invalid userId given"),

    CREATE_ILEP_MEMBER_PANEL_EXIST(HttpStatus.UNPROCESSABLE_ENTITY, 201605,
            "Panel already exist"),

    CREATE_ILEP_MEMBER_ADMINISTRATIVE_CHECK_NOT_COMPLETED(HttpStatus.BAD_REQUEST, 201606,
            "Administrative check not completed"),

    ILEP_MEMBER_CANNOT_BE_CREATED(HttpStatus.BAD_REQUEST, 201607,
            "ILEP cannot be created"),

    ILEP_NOT_CREATED(HttpStatus.NOT_FOUND, 201608,
            "ILEP not created"),

    DFO_APPROVE_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 201603,
            "formUniqueId not found"),

    DFO_APPROVE_PANEL_NOT_FOUND(HttpStatus.NOT_FOUND, 201604,
            "Panel not found"),

    DFO_APPROVE_PANEL_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201605,
            "Input validation failed"),


    DFO_ILEP_NOT_APPROVED(HttpStatus.NOT_FOUND, 201608,
            "ILEP not approved"),

    DFO_APPROVE_PANEL_ILEP_NOT_ASSIGNED(HttpStatus.BAD_REQUEST, 201606,
            "ILEP Panel not assigned"),

    MEETING_NOT_FOUND(HttpStatus.NOT_FOUND, 201604,
            "Meeting not found"),

    UPLOAD_MOM_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201601,
            "Input validation failed"),

    UPLOAD_MOM_INPUT_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 201602,
            "Institute record not found"),

    UPLOAD_MOM_INPUT_MEETING_NOT_FOUND(HttpStatus.NOT_FOUND, 201603,
            "Meeting not found"),

    UPLOAD_MOM_INPUT_MEETING_NOT_SCHEDULED(HttpStatus.BAD_REQUEST, 201604,
            "Meeting not scheduled by AM"),

    REMOVE_PANEL_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 201603,
            "formUniqueId not found"),
    REMOVE_PANEL_CONFLICT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 201603,
            "conflict not found"),

    REMOVE_PANEL_NOT_FOUND(HttpStatus.NOT_FOUND, 201604,
            "Panel not found"),

    REMOVE_PANEL_CANNOT_REMOVE(HttpStatus.NOT_FOUND, 201604,
            "Can't remove panel since Action Pending"),

    REMOVE_PANEL_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 201606,
            "Input validation failed"),
    //CREATE MEETING

    CREATE_MEETING_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 202001,
            "Input validation failed"),

    CREATE_MEETING_INSTITUTE_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 202002,
            "Institute not found"),

    CREATE_MEETING_IILEP_NOT_CREATED(HttpStatus.UNPROCESSABLE_ENTITY, 202003,
            "ILEP panel not created"),

    CREATE_MEETING_CONFLICT_NOT_AGREED(HttpStatus.UNPROCESSABLE_ENTITY, 202004,
            "Both parties not agreed no conflict of interest"),

    CREATE_MEETING_ACCESS_NOT_GRANTED_ILEP(HttpStatus.BAD_REQUEST, 202005,
            "Access grant to ILEP not done"),

    CREATE_MEETING_INVALID_MEETING_TYPE(HttpStatus.BAD_REQUEST, 202006,
            "Invalid meeting type"),

    CREATE_MEETING_INVALID_METHOD_TYPE(HttpStatus.BAD_REQUEST, 202007,
            "Invalid method type"),

    //GET CONFLICT FORM DETAILS AM

    GET_AM_CONFLICT_DETAILS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 202101,
            "Input validation failed"),

    GET_AM_CONFLICT_DETAILS_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 202102,
            "Institute record not found"),

    GET_AM_CONFLICT_DETAILS_CONFLICT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 202103,
            "Conflict not found"),

    GET_INSTITUTE_OWN_CONFLICT_DETAILS_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 202102,
            "Institute record not found"),

    GET_INSTITUTE_OWN_CONFLICT_DETAILS_CONFLICT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 202103,
            "Conflict not found"),

    GET_FORM_ILEP_MEMBERS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 202201,
            "Input validation failed"),

    GET_FORM_ILEP_MEMBERS_FORM_UNIQUE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, 202202,
            "Invalid form unique id"),

    //APPROVE CONFLICT
    AM_APPROVE_CONFLICT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 202501,
            "Input validation failed"),

    AM_APPROVE_CONFLICT_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 202502,
            "Institute record not found"),

    AM_APPROVE_CONFLICT_CONFLICT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 202503,
            "Conflict not found"),

    AM_APPROVE_CONFLICT_INSTITUTION_CONFLICT_NOT_SUBMITTED(HttpStatus.NOT_FOUND, 202504,
            "Institution not submitted conflict form"),

    //GET ILEP EVALUATION DETAILS

    GET_ILEP_EVALUATION_APPLICATION_FORM_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 202801,
            "Institute not found"),
    GET_ILEP_EVALUATION_APPLICATION_ILEP_NOT_EVALUATED(HttpStatus.UNPROCESSABLE_ENTITY, 202802,
            "ILEP not evaluated"),

    ILEP_EVALUATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 200601,
            "Input validation failed"),

    INSITUTION_ADD_BRANCH_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 202901,
            "Input validation failed"),
    INSITUTION_ADD_BRANCH_INVALID_UNIQUE_ID(HttpStatus.NOT_FOUND, 202902,
            "Invalid form unique Id"),

    INSITUTION_GET_ALL_BRANCH_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203001,
            "Input validation failed"),
    INSITUTION_GET_ALL_BRANCH_INVALID_UNIQUE_ID(HttpStatus.NOT_FOUND, 203002,
            "Invalid form unique Id"),

    GET_USER_BY_TYPE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203101,
            "Input validation failed"),

    GET_USER_BY_TYPE_INVALID_USER_TYPE(HttpStatus.NOT_ACCEPTABLE, 203102,
            "Invalid user type"),

    AM_APPROVES_REPORT_INPUT_VALIDATION_FAILED(HttpStatus.NOT_FOUND, 203201,
            "Input validation failed"),
    AM_APPROVES_REPORT_INVALID_UNIQUE_ID(HttpStatus.NOT_FOUND, 203202,
            "Invalid form unique Id"),

    //SUBMIT SUMMARY ILEP EVALUATION
    ILEP_SUBMIT_SUMMARY_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203301,
            "Input validation failed"),

    ILEP_SUBMIT_SUMMARY_APPLICATION_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203302,
            "Application Not Found"),

    ILEP_SUBMIT_SUMMARY_APPLICATION_NOT_EVALUATED(HttpStatus.UNPROCESSABLE_ENTITY, 203303,
            "ILEP evaluation not completed for application"),
    ILEP_SUBMIT_SUMMARY_APPLICATION_COND_FILL_REQUIRED(HttpStatus.UNPROCESSABLE_ENTITY, 203303,
            "ILEP evaluation condfil is required"),
    ILEP_SUBMIT_SUMMARY_PANEL_ID_REQUIRED(HttpStatus.UNPROCESSABLE_ENTITY, 203303,
            "ILEP evaluation panel id is required"),

    ILEP_SUBMIT_SUMMARY_SITE_VISIT_NOT_DONE(HttpStatus.UNPROCESSABLE_ENTITY, 203304,
            "Site visit not completed"),

    //QAC APPROVE REPORT

    QAC_APPROVES_REPORT_INPUT_VALIDATION_FAILED(HttpStatus.NOT_FOUND, 203401,
            "Input validation failed"),

    QAC_APPROVES_REPORT_APPLICATION_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203402,
            "Application Not Found"),

    QAC_APPROVES_REPORT_USER_NOT_AUTHORIZED(HttpStatus.UNPROCESSABLE_ENTITY, 203403,
            "User not authorized to perform action"),

    QAC_APPROVES_REPORT_DFO_NOT_APPROVED(HttpStatus.UNPROCESSABLE_ENTITY, 203404,
            "DFO not approved the updated report"),

    DFO_APPROVES_REPORT_INVALID_UNIQUE_ID(HttpStatus.NOT_FOUND, 203304,
            "Invalid form unique Id"),

    DFO_APPROVES_REPORT_INPUT_VALIDATION_FAILED(HttpStatus.NOT_FOUND, 203305,
            "Input validation failed"),

    ILEP_APPLICATION_NOT_EVALUATED(HttpStatus.UNPROCESSABLE_ENTITY, 203306,
            "ILEP evaluation not completed for application"),

    //SIGN NO CONFIDENTIALITY AGREEMENT

    INSTITUTE_SIGN_NON_CONFIDENTIAL_APPLICATION_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203501,
            "Application Not Found"),

    INSTITUTE_SIGN_NON_CONFIDENTIAL_SITE_VISIT_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203502,
            "Site visit details not found"),

    INSTITUTE_SIGN_NON_CONFIDENTIAL_SITE_VISIT_NOT_REQUIRED(HttpStatus.UNPROCESSABLE_ENTITY, 203503,
            "Institution not requested for site visit"),

    INSTITUTE_SIGN_NON_CONFIDENTIAL_AM_AGENDA_NOT_UPLOADED(HttpStatus.BAD_REQUEST, 203504,
            "AM agenda not uploaded for site visit"),

    SITE_VISIT_APPLICATION_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203504,
            "Application Not Found"),

    SITE_VISIT_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203505,
            "Site visit details not found"),

    SITE_VISIT_CREATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203601,
            "Input validation failed"),

    SITE_VISIT_CREATE_INVALID_FORM_ID(HttpStatus.NOT_FOUND, 203602,
            "Invalid form unique id"),

    SITE_VISIT_CREATE_STATUS_NOT_MET(HttpStatus.BAD_REQUEST, 203603,
            "Current status is less than 32"),


    INSTITUTE_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 203603,
            "Institute not found"),

    INSTITUTION_UPDATE_DOC_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203701,
            "Input validation failed"),

    INSTITUTION_UPDATE_DOC_INVALID_FORM_ID(HttpStatus.NOT_FOUND, 203702,
            "Invalid form unique id"),

    INSTITUTION_UPDATE_DOC_INVALID_SITE_VISIT(HttpStatus.NOT_FOUND, 203703,
            "Site visit not found"),

    INSTITUTION_UPDATE_DOC_ACTION_NOT_ALLOWED(HttpStatus.BAD_REQUEST, 203704,
            "Action not allowed"),

    INSTITUTION_ACCEPT_DATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203801,
            "Input validation failed"),

    INSTITUTION_ACCEPT_DATE_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 203802,
            "Invalid form unique Id"),

    INSTITUTION_ACCEPT_DATE_SITE_VISIT_NOT_CREATED(HttpStatus.NOT_FOUND, 203803,
            "Site visit not created"),

    INSTITUTION_ACCEPT_DATE_NOT_ALLOWED(HttpStatus.NOT_FOUND, 203804,
            "Date should be one of the given date"),

    INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203901,
            "Input validation failed"),

    INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_INVALID_FORM_UNIQUE_ID(HttpStatus.UNPROCESSABLE_ENTITY, 203902,
            "Invalid form unique Id"),

    INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED(HttpStatus.UNPROCESSABLE_ENTITY, 203903,
            "Site visit not created"),

    GET_SITE_VISIT_DATE_CHANGE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 204001,
            "Input validation failed"),

    GET_SITE_VISIT_DATE_CHANGE_INVALID_FORM_UNIQUE_ID(HttpStatus.UNPROCESSABLE_ENTITY, 204002,
            "Invalid form unique Id"),

    GET_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED(HttpStatus.UNPROCESSABLE_ENTITY, 204003,
            "Site visit not created"),

    GET_SITE_VISIT_DATE_CHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, 204004,
            "Site visit date change not requested"),

    //    AM UPDATE DATE VISIT
    AM_ACTION_DATE_REQUEST_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 204201,
            "Input validation failed"),
    AM_ACTION_DATE_REQUEST_APPLICATION_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 204202,
            "Application Not Found"),
    AM_ACTION_DATE_REQUEST_SITE_VISIT_NOT_CREATED(HttpStatus.UNPROCESSABLE_ENTITY, 204203,
            "Site visit not created"),

    AM_ACTION_DATE_REQUEST_SITE_VISIT_CHANGE_NOT_REQUESTED(HttpStatus.BAD_REQUEST, 204204,
            "Institution not requested for site visit date change"),

    // GDQ_UPLOAD_DOCUMENT
    GDQ_UPLOAD_DOCUMENT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 204301,
            "Input validation failed"),

    GDQ_UPLOAD_DOCUMENT_SITE_VISIT_DATE_CHANGE_INVALID_FORM_UNIQUE_ID(HttpStatus.UNPROCESSABLE_ENTITY, 204302,
            "Invalid form unique Id"),

    // GDQ_GET_DOCUMENT
    GDQ_GET_DOCUMENT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 204401,
            "Input validation failed"),

    GDQ_GET_DOCUMENT_INVALID_FORM_UNIQUE_ID(HttpStatus.UNPROCESSABLE_ENTITY, 204402,
            "Invalid form unique Id"),


    //QAC SUBMIT FEEDBACK
    QAC_SUBMIT_FEEDBACK_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205001,
            "Input validation failed"),

    QAC_SUBMIT_FEEDBACK_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 205002,
            "formUniqueId not found"),

    //NAC APPROVE DOC
    NAC_DOC_APPROVE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205101,
            "Input validation failed"),

    NAC_DOC_APPROVE_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 205102,
            "formUniqueId not found"),

    //GET FEEDBACK_DOCUMENT details
//    NAC_DOC_APPROVE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205101,
//            "Input validation failed"),

    GET_FEEDBACK_DOCUMENT_DETAIL_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 205201,
            "formUniqueId not found"),

    GET_FEEDBACK_DOCUMENT_DETAIL_INVALID_DOCUMENT(HttpStatus.NOT_FOUND, 205202,
            "document not found"),

    //SAVE SERIAL NUMBER
    MCU_SAVE_SERIAL_NO_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205301,
            "Input validation failed"),

    MCU_SAVE_SERIAL_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 205302,
            "formUniqueId not found"),

    AM_UPDATE_PARTIAL_MET_STATUS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205401,
            "Input validation failed"),

    AM_UPDATE_PARTIAL_MET_STATUS_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 205402,
            "formUniqueId not found"),

    AM_UPDATE_PARTIAL_MET_STATUS_ILEP_NOT_EVALUATED(HttpStatus.NOT_ACCEPTABLE, 205403,
            "Ilep not evaluated the form"),

    AM_UPDATE_PARTIAL_MET_STATUS_MET(HttpStatus.NOT_ACCEPTABLE, 205404,
            "Status already met"),


    ILEP_MEMBER_CANNOT_SUBMIT(HttpStatus.UNPROCESSABLE_ENTITY, 205405,
            "ILEP chairpersom can only submit the evaluation"),

    INVALID_APPLICATION_STATUS(HttpStatus.UNPROCESSABLE_ENTITY, 205406,
            "Current application status is invalid"),


    GET_DUE_DATES_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205501,
            "Input validation failed"),

    GET_DUE_DATES_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 205502,
            "User don't have permission"),


    SET_DUE_DATES_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 205601,
            "Input validation failed"),

    SET_DUE_DATES_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 205602,
            "User don't have permission"),

    SET_DUE_DATES_TYPE_ACTION_NOT_FOUND(HttpStatus.NOT_FOUND, 205603,
            "One or more type and action combination not found"),

    //    SET MAIL TEMPLATE
    SET_MAIL_TEMPLATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206001,
            "Input validation failed"),

    SET_MAIL_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, 206002,
            "Template not found with given code"),

    SET_MAIL_TEMPLATE_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206003,
            "User don't have permission"),

    GET_MAIL_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, 206101,
            "Template not found"),

    GET_MAIL_TEMPLATE_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206102,
            "User don't have permission"),


    LIST_MAIL_TEMPLATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206201,
            "Input validation failed"),

    LIST_MAIL_TEMPLATE_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206202,
            "User don't have permission"),

    NAC_SUBMIT_FEEDBACK_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 205604,
            "formUniqueId not found"),

    // SUBMIT REGISTRATION DATES
    GET_REGISTRATION_DATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206201,
            "Input validation failed"),

    GET_REGISTRATION_DATES_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206202,
            "User don't have permission"),
    ADD_REGISTRATION_DATES_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206301,
            "Input validation failed"),

    ADD_REGISTRATION_DATES_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206301,
            "User don't have permission"),

    DELETE_REGISTRATION_DATES_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206401,
            "User don't have permission"),

    DELETE_REGISTRATION_DATES_NOT_FOUND(HttpStatus.NOT_FOUND, 206402,
            "Registration date not found"),

    CHANGE_APPLICATION_STATUS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206501,
            "Input validation failed"),
    CHANGE_APPLICATION_STATUS_USER_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, 206502,
            "User don't have permission"),

    CHANGE_APPLICATION_STATUS_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 206503,
            "formUniqueId not found"),

    CHANGE_APPLICATION_STATUS_INVALID_STATUS(HttpStatus.NOT_FOUND, 206504,
            "Status not allowed"),

    AM_GRAND_ACCESS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206601,
            "Input validation failed"),

    AM_GRAND_ACCESS_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 206602,
            "formUniqueId not found"),

    AM_GRAND_ACCESS_NOT_A_PANEL_MEMBER(HttpStatus.NOT_ACCEPTABLE, 206603,
            "User not a panel member"),

    AM_GRAND_ACCESS_ACCESS_GRANTED(HttpStatus.NOT_ACCEPTABLE, 206604,
            "Already access granted"),
    AM_GRAND_ACCESS_CONFLICT_FORM_NOT_SIGNED(HttpStatus.NOT_ACCEPTABLE, 206605,
            "Conflict form not signed"),


    AM_CHANGE_SITE_VISIT_DATE_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206701,
            "Input validation failed"),

    AM_CHANGE_SITE_VISIT_DATE_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 206702,
            "formUniqueId not found"),

    AM_CHANGE_SITE_VISIT_DATE_ACTION_NOT_ALLOWED(HttpStatus.BAD_REQUEST, 206703,
            "Action not allowed"),

    AM_CHANGE_SITE_VISIT_DATE_SITE_VISIT_NOT_CREATED(HttpStatus.UNPROCESSABLE_ENTITY, 206704,
            "Site visit not created"),

    AM_EVALUATE_WITH_REVERT_COMMENT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206801,
            "Input validation failed"),

    AM_EVALUATE_WITH_REVERT_COMMENT_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 206802,
            "formUniqueId not found"),

    AM_EVALUATE_WITH_REVERT_COMMENT_ILEP_EVALUATION_NOT_SUBMITTED(HttpStatus.BAD_REQUEST, 206803,
            "ILEP Evaluation not submitted"),

    AM_ACTION_MEETING_HELD_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206901,
            "Input validation failed"),

    AM_ACTION_MEETING_HELD_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 206902,
            "formUniqueId not found"),

    AM_ACTION_MEETING_HELD_INSTITUTION_NON_CONFIDENTIAL_NOT_SIGNED(HttpStatus.BAD_REQUEST, 206903,
            "Institution non-confidential agreement not signed"),

    // feedback file upload from dfo , gdq , qac
    EVALUATION_FEEDBACK_FILE_UPLOAD_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 208101,
            "Input validation failed"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 208102,
            "formUniqueId not found"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_PERMISSION_FOR_DFO_CHIEF(HttpStatus.UNAUTHORIZED, 208103,
            "File upload permission only for dfo chief"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_PERMISSION_FOR_GDQ_AC(HttpStatus.UNAUTHORIZED, 208104,
            "File upload permission only for gdq ac"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_PERMISSION_FOR_AM(HttpStatus.UNAUTHORIZED, 208105,
            "File upload permission only for AM"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_ILEP_NOT_EVALUATED(HttpStatus.NOT_FOUND, 208106,
            "Ilep evaluation not found"),
    EVALUATION_FEEDBACK_FILE_UPLOAD_INVALID_TYPE(HttpStatus.UNPROCESSABLE_ENTITY, 208107,
            "Invalid type provided only [1 , 2 ,3 ] is accepted"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_EVALUATION_NOT_APPROVED(HttpStatus.UNPROCESSABLE_ENTITY, 208108,
            "Evaluation not approved by am"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_AM_NOT_UPDATED_COMMENT(HttpStatus.UNPROCESSABLE_ENTITY, 208109,
            "Am didn't updated the evaluation comment"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_AM_NOT_SHARED_TO_GDQ(HttpStatus.UNPROCESSABLE_ENTITY, 208110,
            "Am didn't shared the document to gdq"),

    EVALUATION_FEEDBACK_FILE_UPLOAD_AM_NOT_SHARED_TO_QAC(HttpStatus.UNPROCESSABLE_ENTITY, 208111,
            "Am didn't shared the document to qac"),

    AM_SHARED_DOC_TO_QAC_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 203901,
            "Input validation failed"),

    AM_SHARED_DOC_TO_QAC_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 203902,
            "ormUniqueId not found"),

    AM_SHARED_DOC_TO_QAC_ILEP_NOT_EVALUATED(HttpStatus.NOT_FOUND, 203903,
            "Ilep evaluation not found"),

    AM_SHARED_DOC_TO_QAC_STATUS_NOT_LISTED_OR_NOTLISTED(HttpStatus.NOT_FOUND, 203904,
            "Application status not in listed or not-listed state"),

    AM_SHARED_DOC_TO_QAC_STATUS_NOT_MET(HttpStatus.NOT_FOUND, 203905,
            "Application status not in 61 or 64"),


    INVALID_USER_TYPE(HttpStatus.UNPROCESSABLE_ENTITY, 203906,
            "Invalid user type provided only [3,4,6] is accepted"),


    FACTUAL_ACCURACY_COMPLETED_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 208401,
            "Input validation failed"),

    FACTUAL_ACCURACY_COMPLETED_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 208402,
            "formUniqueId not found"),

    FACTUAL_ACCURACY_COMPLETED_INSTITUTE_NOT_COMMENTED(HttpStatus.BAD_REQUEST, 208403,
            "Institution not commented for factual accuracy listed/notlisted\n"),

    INSTITUTION_COMMENT_BACK_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 206201,
            "Input validation failed"),

    INSTITUTION_COMMENT_BACK_INVALID_FORM_UNIQUE_ID(HttpStatus.UNPROCESSABLE_ENTITY, 206202,
            "Invalid form unique Id"),

    INSTITUTION_COMMENT_BACK_ILEP_NOT_EVALUATED(HttpStatus.UNPROCESSABLE_ENTITY, 206203,
            "Ilep not evaluated"),
    INSTITUTION_COMMENT_BACK_FACTUAL_ACCURACY_NOT_STARTED(HttpStatus.UNPROCESSABLE_ENTITY, 206204,
            "Factual accuracy not started"),

    DFO_APPROVE_APPEAL_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 208501,
            "Input validation failed"),

    DFO_APPROVE_APPEAL_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 208502,
            "Invalid form uniqueId"),

    DFO_APPROVE_APPEAL_INSTITUTION_NOT_CREATED_APPEAL(HttpStatus.BAD_REQUEST, 208503,
            "Institution not created appeal"),

    DFO_CHIEF_SHARED_REPORT_CABINET_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 208601,
            "Input validation failed"),

    DFO_CHIEF_SHARED_REPORT_CABINET_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 208602,
            "Invalid form uniqueId"),

    DFO_ADMIN_CABINET_APPROVED_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 208701,
            "Input validation failed"),

    DFO_ADMIN_CABINET_APPROVED_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 208702,
            "Invalid form uniqueId"),


    DFO_CHIEF_SIGN_REPORT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 208801,
            "Input validation failed"),

    DFO_CHIEF_SIGN_REPORT_INVALID_FORM_UNIQUE(HttpStatus.UNPROCESSABLE_ENTITY, 208802,
            "Invalid form unique id"),

    DFO_CHIEF_SIGN_REPORT_STATUS_NOT_MET(HttpStatus.UNPROCESSABLE_ENTITY, 208801,
            "Application status is not 80 or 82"),

    INSTITUTION_NOT_ALLOWED_TO_APPEAL(HttpStatus.UNPROCESSABLE_ENTITY, 208803,
            "Institution not allowed to raise the appeal -> due to the time expiry"),

    AM_FACTUAL_DEFERED_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209001,
            "Input validation failed"),

    AM_FACTUAL_DEFERED_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 209002,
            "Invalid form unique id"),

    AM_FACTUAL_DEFERED_STATUS_NOT_MET(HttpStatus.UNPROCESSABLE_ENTITY, 209003,
            "Current status must be 61"),

    AM_FACTUAL_DEFERED_ILEP_NOT_EVALUATED(HttpStatus.NOT_ACCEPTABLE, 209004,
            "Ilep not evaluated the form"),


    //ILEP PANEL create seperate
    CREATE_ILEP_MEMBER_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209101,
            "Input validation failed"),
    CREATE_ILEP_MEMBER_SEP_INPUT_MEMBER_EMPTY(HttpStatus.UNPROCESSABLE_ENTITY, 209102,
            "This panel member has an existing conflict of interest. Please select a different panel member from the list."),

    CREATE_ILEP_MEMBER_SEP_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 209103,
            "Institute record not found"),

    CREATE_ILEP_MEMBER_SEP_USER_NOT_FOUND(HttpStatus.NOT_FOUND, 209104,
            "user not found"),

    CREATE_ILEP_MEMBER_SEP_INVALID_USER(HttpStatus.UNPROCESSABLE_ENTITY, 209105,
            "Invalid userId given"),

    CREATE_ILEP_MEMBER_SEP_PANEL_EXIST(HttpStatus.UNPROCESSABLE_ENTITY, 209105,
            "Panel already exist"),
    CREATE_ILEP_MEMBER_SEP_ALREADY_ASSIGNED(HttpStatus.UNPROCESSABLE_ENTITY, 209106,
            "User already assign once and removed"),

    CREATE_ILEP_MEMBER_SEP_ADMINISTRATIVE_CHECK_NOT_COMPLETED(HttpStatus.BAD_REQUEST, 209106,
            "Administrative check not completed"),

    CREATE_ILEP_MEMBER_SEP_ILEP_MEMBER_CANNOT_BE_CREATED(HttpStatus.BAD_REQUEST, 209107,
            "ILEP cannot be created"),

    CREATE_ILEP_MEMBER_SEP_ILEP_NOT_CREATED(HttpStatus.NOT_FOUND, 209108,
            "ILEP not created"),

    //DFO Approve ilep

    DFO_APPROVE_PANEL_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209201,
            "Input validation failed"),

    DFO_APPROVE_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 209202,
            "formUniqueId not found"),

    DFO_APPROVE_SEP_PANEL_NOT_FOUND(HttpStatus.NOT_FOUND, 209203,
            "Ilep not found"),

    DFO_SEP_ILEP_NOT_APPROVED(HttpStatus.NOT_FOUND, 209204,
            "ILEP not approved"),

    DFO_APPROVE_SEP__ILEP_NOT_ASSIGNED(HttpStatus.BAD_REQUEST, 209205,
            "ILEP Panel not assigned"),

    GET_ILEP_MEMBER_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210201,
            "Input validation failed"),

    GET_ILEP_MEMBER_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 210202,
            "formUniqueId not found"),

    GET_ILEP_MEMBERS_SEP_NOT_FOUND(HttpStatus.NOT_FOUND, 210203,
            "Ilep members not found"),

    //Institution conflict action seperate
    INSTITUTION_CONFLICT_ACTION_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209301,
            "Input validation failed"),

    INSTITUTION_CONFLICT_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 209302,
            "formUniqueId not found"),

    INSTITUTION_CONFLICT_SEP_ALREADY_SUBMITTED(HttpStatus.CONFLICT, 209303,
            "Institution already submitted the conflict"),

    INSTITUTION_CONFLICT_SEP_ILEP_PANEL_DFO_NOT_APPROVED(HttpStatus.BAD_REQUEST, 209304,
            "ILEP Panel not approved by DFO"),

    INSTITUTION_CONFLICT_SEP_ILEP_NOT_FOUND(HttpStatus.BAD_REQUEST, 209305,
            "Ilep member not found"),

    //APPROVE CONFLICT SEPARATE
    AM_APPROVE_CONFLICT_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209601,
            "Input validation failed"),

    AM_APPROVE_CONFLICT_SEP_INSTITUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 209602,
            "Institute record not found"),

    AM_APPROVE_CONFLICT_SEP_CONFLICT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 209603,
            "Conflict not found"),

    AM_APPROVE_CONFLICT_SEP_INSTITUTION_CONFLICT_NOT_SUBMITTED(HttpStatus.NOT_FOUND, 209604,
            "Institution not submitted conflict form"),

    //DFO APPROVE SEPARATE API

    DFO_APPROVE_CONFLICT_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209801,
            "Input validation failed"),
    DFO_APPROVE_CONFLICT_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 209802,
            "formUniqueId not found"),

    DFO_APPROVE_CONFLICT_SEP_AM_NOT_APPROVED(HttpStatus.NOT_ACCEPTABLE, 209803,
            "Am not approved the conflict"),

    DFO_APPROVE_CONFLICT_SEP_ILEP_NOT_FOUND(HttpStatus.NOT_FOUND, 209804,
            "Ilep member not found"),

    //Ilep conflict action
    ILEP_CONFLICT_ACTION_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210001,
            "Input validation failed"),
    ILEP_CONFLICT_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 210002,
            "formUniqueId not found"),
    ILEP_CONFLICT_SEP_INSTITUTION_CONFLICT_NOT_SUBMITTED(HttpStatus.NOT_FOUND, 210003,
            "Institution not submitted the conflict"),
    ILEP_CONFLICT_SEP_ILEP_NOT_ASSIGNED_INSTITUTION_CONFLICT_NOT_SUBMITTED(HttpStatus.NOT_FOUND, 210003,
            "Ilep not assigned"),

    //Remove ilep member separate

    REMOVE_PANEL_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 209901,
            "Input validation failed"),
    REMOVE_PANEL_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 209902,
            "formUniqueId not found"),
    REMOVE_PANEL_SEP_CONFLICT_FORM_NOT_FOUND(HttpStatus.NOT_FOUND, 209903,
            "conflict not found"),

    REMOVE_PANEL_SEP_NOT_FOUND(HttpStatus.NOT_FOUND, 209904,
            "Panel not found"),

    REMOVE_PANEL_SEP_CANNOT_REMOVE(HttpStatus.NOT_FOUND, 209905,
            "Can't remove panel since Action Pending"),

    //Am grand access separate

    AM_GRAND_ACCESS_SEP_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210301,
            "Input validation failed"),

    AM_GRAND_ACCESS_SEP_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 210302,
            "formUniqueId not found"),

    AM_GRAND_ACCESS_SEP_NOT_A_PANEL_MEMBER(HttpStatus.NOT_ACCEPTABLE, 210303,
            "User not a panel member"),

    AM_GRAND_ACCESS_SEP_ACCESS_GRANTED(HttpStatus.NOT_ACCEPTABLE, 210304,
            "Already access granted"),
    AM_GRAND_ACCESS_SEP_CONFLICT_FORM_NOT_SIGNED(HttpStatus.NOT_ACCEPTABLE, 210305,
            "Conflict form not signed"),

    SET_APPROVE_HISTORY_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210401,
            "Input validation failed"),

    SET_APPROVE_HISTORY_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 210402,
            "formUniqueId not found"),

    GET_APPROVE_HISTORY_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210501,
            "Input validation failed"),

    GET_APPROVE_HISTORY_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 210502,
            "formUniqueId not found"),


    GET_LOGS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210601,
            "Input validation failed"),

    EDIT_APP_STATUS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 210901,
            "Input validation failed"),

    EDIT_APP_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, 210902,
            "Status not found"),


    GET_STATUS_LIST_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 2010701,
            "Input validation failed"),

    GET_STATUS_LIST_INPUT_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, 2010702,
            "No data"),

    GET_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, 210801,
            "Status not found"),

    RESET_APPLICATION_STATUS_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 211101,
            "formUniqueId not found"),

    //Revert Conflicts

    REVERT_CONFLICT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 211001,
            "Input validation failed"),

    REVERT_CONFLICT_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 211002,
            "formUniqueId not found"),

    REVERT_CONFLICT_NOT_A_PANEL_MEMBER(HttpStatus.NOT_ACCEPTABLE, 211003,
            "User not a panel member"),

    REVERT_CONFLICT_CONFLICT_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, 211004,
            "Conflict not found"),

    REVERT_CONFLICT_INVALID_TYPE(HttpStatus.NOT_ACCEPTABLE, 211005,
            "Invalid type "),

    REVERT_CONFLICT_ACCESS_GRANTED(HttpStatus.NOT_ACCEPTABLE, 211006,
            "Access granted "),

    REVERT_CONFLICT_CONFLICT_USER_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, 211007,
            "Conflict user not found"),

    REJECT_APPLICATION_STATUS_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 211201,
            "formUniqueId not found"),

    INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 209002,
            "Invalid form unique id"),

    REJECT_APPLICATION_STATUS_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY,211501,"Input validation failed"),

    REJECT_SITE_VISIT_DOC_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND, 211502,
            "formUniqueId not found"),
    REJECT_SITE_VISIT_DOC_SITE_VISIT_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, 211503,
            "Site visit not found"),

    UPLOAD_MEETING_REPORT_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY, 211601,
            "Input validation failed"),

    UPLOAD_MEETING_REPORT_INVALID_FORM_UNIQUE_ID(HttpStatus.UNPROCESSABLE_ENTITY, 211602,
            "Invalid form uniqueId"),

    UPLOAD_MEETING_REPORT_MEETING_NOT_CREATED(HttpStatus.UNPROCESSABLE_ENTITY, 211603,
            "Meeting not created"),

    UPLOAD_MEETING_REPORT_INVALID_MEETING_TYPE(HttpStatus.UNPROCESSABLE_ENTITY, 211604,
            "Invalid meeting type"),

    UPLOAD_MEETING_REPORT_DEADLINE(HttpStatus.UNPROCESSABLE_ENTITY, 211605,
            "Report upload deadline reached"),

    INSTITUTE_IS_NOT_REJECTED(HttpStatus.UNPROCESSABLE_ENTITY, 211606,
            "Institute in not rejected"),

    CHANGE_PASSWORD_INPUT_VALIDATION_FAILED(HttpStatus.BAD_REQUEST,212001,
            "Input validation failed"),
    CHANGE_PASSWORD_USER_NOT_FOUND(HttpStatus.NOT_FOUND,21202,
            "User not found"),
    CHANGE_PASSWORD_WRONG_PASSWORD(HttpStatus.NOT_ACCEPTABLE,21203,
            "Wrong password"),
    REGISTRATION_TYPE_NOT_VALID(HttpStatus.NOT_ACCEPTABLE,21203,
            "Registration type invalid"),
    GET_ILEP_EVALUATION_COPY_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY,21211,
            "Input validation failed"),

    GET_ILEP_EVALUATION_COPY_INVALID_FORM_UNIQUE_ID(HttpStatus.NOT_FOUND,21212,
            "Application form not found"),
    GET_ILEP_EVALUATION_COPY_NOT_FOUND(HttpStatus.NOT_FOUND,21213,
            "Evaluation report copy not found"),

    GENERATE_PRE_SIGNED_URL_INPUT_VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_ENTITY,21221,
            "Input validation failed"),

    ADMINISTRATIVE_CHECK_FAILED(HttpStatus.BAD_REQUEST, 22001,
            "Administrative status update failed (amStatus should be 1 or 0)"),
    ADMINISTRATIVE_CHECK_FAILED_INVALID_STDS(HttpStatus.BAD_REQUEST, 22002,
            "Administrative status update failed (standardWorkFlowsList should not be empty)"),

    ADMINISTRATIVE_CHECK_FAILED_INVALID_OVER_ALL_STATUS(HttpStatus.BAD_REQUEST, 22003,
            "Administrative status update failed (overallStatus should be valid)"),

    PROGRAM_STRUCTURE_VERSION_NOT_VALID(HttpStatus.BAD_REQUEST, 22004,
            "Pass version against program structure"),

    FORM_SUBMISSION_FAILED(HttpStatus.BAD_REQUEST, 22005,
            "Form submission failed"),

    INSTITUTE_NOT_LISTED(HttpStatus.BAD_REQUEST, 22006,
            "Institute not listed yet"),

    LAST_ERROR(HttpStatus.BAD_REQUEST, 23000,
            "Administrative status update failed (amStatus should be 1 or 0)"),

    INSTITUTION_ARCHIVAL_FAILED(HttpStatus.NOT_FOUND, 23009,
            "No qualifications are registered / All qualifications are Updated"),
    GENERATE_PRE_SIGNED_URL_INVALID_METHOD(HttpStatus.BAD_REQUEST,21224,
            "Invalid method , Allowed methods: GET, PUT,DELETE"),
    INVALID_STATIC_KEY(HttpStatus.FORBIDDEN, 23015, "Invalid static key"),;




    private final HttpStatus status;

    private final Integer code;

    private final String description;

    private final boolean hidden;

    ErrorCode(HttpStatus status, Integer code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
        this.hidden = false;
    }

    ErrorCode(HttpStatus status, Integer code, String description, boolean hidden) {
        this.status = status;
        this.code = code;
        this.description = description;
        this.hidden = hidden;
    }

    public static ErrorCode[] getVisibleErrorCodes() {
        List<ErrorCode> errorCodeList = new ArrayList<>();
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (!errorCode.isHidden()) {
                errorCodeList.add(errorCode);
            }
        }
        ErrorCode[] errorCodeArray = new ErrorCode[errorCodeList.size()];
        return errorCodeList.toArray(errorCodeArray);
    }
}
