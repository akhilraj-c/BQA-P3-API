package com.mindteck.common.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ApiUrls {

    public static final String API_SPEC_LOGIN = "/api-spec-login";

    public static final String ADMIN_API_SPEC_DOWNLOAD = "/public/document";

    public static final String BASE_URL = "/api/";

    public static final String APP_REGISTRATION = BASE_URL + "auth/app/registration"; // API ID:1

    public static final String LOGOUT = BASE_URL + "auth/logout";

    public static final String LOGIN = BASE_URL + "auth/login"; // API ID:2

    public static final String RESET_PASSWORD = BASE_URL + "auth/reset-password"; // API ID:4

    public static final String FORGOT_PASSWORD = BASE_URL + "auth/forgot-password"; // API ID:3

    public static final String REFRESH_TOKEN = BASE_URL + "auth/refresh-token"; // API ID:5

    public static final String USER_REGISTRATION = BASE_URL + "/auth/user/form-submission"; // API ID:6
    public static final String USER_QP_REGISTRATION = BASE_URL + "/auth/user/qp/form-submission"; // API ID:6

    public static final String GET_REGISTRATION_DATES = BASE_URL + "auth/admin/get-registartion-date";

    public static final String APPLICATION_MANAGER_EVALUATE_FORM = BASE_URL + "/user/evaluate-form"; // API ID:15
    public static final String SUBMIT_FORM = BASE_URL + "/user/submit-form"; // API ID:15
    public static final String OVERALL_ADMIN_CHECK_DETAILS = BASE_URL + "/user/overall-admin-check-details"; // API ID:15
    public static final String FORCE_CHANGE_STATUS = BASE_URL + "/user/force-change-status"; // API ID:15
    public static final String BQA_UPDATE = BASE_URL + "bqa/update-status"; // API ID:7

    public static final String ASSIGN_APPLICATION_MANAGER = BASE_URL + "dfo/assign-application-manager"; // API ID:14

    public static final String GET_ILEP_EVALUATION_COPY = BASE_URL + "ilep/evaluation-copy/{formUniqueId}";

    public static final String APPLICATION_DETAILS = BASE_URL + "user/get-application-details"; // API ID:8
    public static final String GET_APPLICATION_LIST = BASE_URL + "user/application-list"; // API ID:9

    public static final String GET_INSTITUTE_LISTING_DETAILS = BASE_URL + "user/get-institute-listing-details";

    public static final String REQUEST_DATE_EXTENSION = BASE_URL + "institution/date-extension"; // API ID:10


    public static final String DATE_EXTENSION_APPROVAL = BASE_URL + "user/extension-approval"; // API ID:13



    public static final String INSTITUTION_UPDATE_FORM = BASE_URL + "user/update-application"; // API ID:11

    public static final String GET_APPLICATION_MANAGER_LIST = BASE_URL + "user/application-manager-list"; // API ID:12


    public static final String USER_EDIT = BASE_URL + "admin/edit"; // API ID:16




    public static final String CREATE_USER = BASE_URL + "admin/create-user"; // API ID:17

    public static final String DIRECTOR_CREATE_USER = BASE_URL + "user/create-user";

    public static final String GET_USER_LIST = BASE_URL + "admin/user-list"; // API ID:18


    public static final String INSTITUTION_CONFLICT = BASE_URL + "institute/conflict/action"; // API ID:19

    public static final String ILEP_CONFLICT = BASE_URL + "ilep/conflict/action"; // API ID:20

    public static final String DFO_PANEL_APPROVE = BASE_URL + "dfo/approve-ilep"; // API ID:21

    public static final String UPLOAD_MOM = BASE_URL + "am/upload-first-meeting-doc"; // API ID:22

    public static final String REMOVE_PANEL = BASE_URL + "am/remove-ilep"; // API ID:23

    public static final String CREATE_ILEP_MEMBER = BASE_URL + "am/create-ilep-member"; // API ID:24

    public static final String CREATE_MEETING = BASE_URL + "am/create-meeting"; // API ID:25

    public static final String GET_AM_CONFLICT_DETAILS = BASE_URL + "am/get-conflict-form-details/{formUniqueId}"; // API ID:26

    public static final String GET_INSTITUTE_OWN_CONFLICT = BASE_URL + "institute/get-institute-conflict-form-details/{formUniqueId}"; // API ID:27

    public static final String GET_ILEP_OWN_CONFLICT = BASE_URL + "ilep/get-ilep-conflict-form-details/{formUniqueId}"; // API ID:28

    public static final String GET_FORM_ILEP_MEMBERS = BASE_URL + "institution/get-ilep-members/{formUniqueId}"; // API ID:29

    public static final String AM_APPROVE_CONFLICT = BASE_URL + "am/conflict-approve"; // API ID:30

    public static final String GET_INSTITUTE_CONFLICT_DFO = BASE_URL + "dfo/get-conflict-form-details/{formUniqueId}"; // API ID:31

    public static final String GET_ILEP_EVALUATION_APPLICATION_FORM = BASE_URL + "user/get-ilep-evaluation-form/{formUniqueId}"; // API ID:32

    public static final String ILEP_EVALUATE = BASE_URL + "ilep/evaluate-application-form"; // API ID:33

    public static final String INSTITUTION_ADD_BRANCH = BASE_URL + "institute/add-branch"; // API ID:34
    public static final String INSTITUTION_GET_ALL_BRANCH = BASE_URL + "institute/get-all-branch/{formUniqueId}"; // API ID:35

    public static final String GET_USER_BY_TYPE = BASE_URL + "user/get-by-type"; // API ID:36

    public static final String AM_APPROVES_THE_REPORT = BASE_URL + "am/approves-report/{formUniqueId}"; // API ID:37

    public static final String ILEP_SUBMIT_SUMMARY = BASE_URL + "ilep/submit-summary"; // API ID:38

    public static final String QAC_APPROVES_REPORT = BASE_URL + "qac/approves-report/{formUniqueId}"; // API ID:39

    public static final String DFO_APPROVES_THE_REPORT = BASE_URL + "dfo/approves-report/{formUniqueId}"; // API ID:40

    public static final String ILEP_UPDATE_EVALUATION = BASE_URL + "ilep/update-evaluation-form"; // API ID:41

    public static final String INSTITUTE_SIGN_NON_CONFIDENTIAL = BASE_URL + "institute/sign-non-confidentail/{formUniqueId}"; // API ID:42

    public static final String GET_SITE_VISIT_DATA = BASE_URL + "institute/get-site-visit-form/{formUniqueId}"; // API ID:43

    public static final String CREATE_SITE_VISIT_DATA = BASE_URL + "am/create-site-visit-form"; // API ID:44

    public static final String GET_MEETING_DETAILS = BASE_URL + "user/get-meeting/{formUniqueId}"; // API ID:45

    public static final String INSTITUTION_UPDATE_SITE_VISIT_DOCUMENT = BASE_URL + "institute/update-site-visit-form"; // API ID:46

    public static final String INSTITUTION_ACCEPT_SITE_VISIT_DATE = BASE_URL + "institute/accept/site-visit"; // API ID:47

    public static final String INSTITUTION_REQUEST_NEW_SITE_VISIT_DATE = BASE_URL + "institute/request/new-site-visit-date"; // API ID:48

    public static final String GET_SITE_VISIT_DATE_CHANGE_DETAILS = BASE_URL + "get/new-site-visit-date/{formUniqueId}"; // API ID:49

    public static final String AM_ACTION__VISIT_MEETING_HELD = BASE_URL + "am/action/meeting-held"; // API ID:50

    public static final String SITE_VISIT_DONE = BASE_URL + "am/action/site-visit-done"; // API ID:50

    public static final String AM_ACTION_DATE_REQUEST = BASE_URL + "am/action/date-request"; // API ID:51

    public static final String GDQ_UPLOAD_DOCUMENTS = BASE_URL + "gdq/upload-documents"; // API ID:52

    public static final String GDQ_GET_DOCUMENTS = BASE_URL + "gdq/get/documents/{formUniqueId}"; // API ID:53

    public static final String AM_ALLOW_GRANT_ACCESS = BASE_URL + "am/allow-grand-access"; // API ID:54

    public static final String AM_DOC_SHARED_STATUS_UPDATE = BASE_URL + "am/doc-shared-gdq"; // API ID:55

    public static final String GDQ_REVIEW_COMPLETED = BASE_URL + "gdg/review-completed-update"; // API ID:56

    public static final String QAC_SUBMIT_FEEDBACK = BASE_URL + "am/submit-qac-feedback"; // API ID:57

    public static final String DOC_SHARED_TO_QAC = BASE_URL + "am/doc-shared-QAC-update"; // API ID:58
    public static final String DOC_SHARED_TO_NAC = BASE_URL + "am/doc-shared-NAC-update"; // API ID:59
 //   public static final String MCU_SCANNED_DOCUMENT_UPDATE = BASE_URL + "mco/MCO-scanned-document-update"; // API ID:60
    public static final String DFO_APPROVED = BASE_URL + "am/dfo-director-approved"; // API ID:61

    public static final String INSTITUTION_UPDATE_COMMENT = BASE_URL + "institution/update-comment"; // API ID:62
    public static final String NAC_DOC_APPROVE = BASE_URL + "dfo-admin/nac-approve-doc"; // API ID:63

    public static final String GET_FEEDBACK_DOCUMENT_DETAIL = BASE_URL + "am/get-feedback-document-details/{formUniqueId}"; // API ID:64

 //   public static final String MCU_SAVE_SERIAL_NO = BASE_URL + "am/MCU-save-serial_no"; // API ID:65
    public static final String QAC_SUBMIT_REPORT = BASE_URL + "qac/submit-report"; // API ID:66

    public static final String PARTIALLY_MET_AM_UPDATE_STATUS = BASE_URL + "am/partially-met/update-comment"; // API ID:67

    public static final String DFO_APPROVE_ILEP_CONFLICT = BASE_URL + "dfo/approve-ilep-conflict"; // API ID:68

    public static final String BQA_ADMIN_DUE_DATES = BASE_URL + "bqa/admin/due-dates"; // API ID:69

    public static final String BQA_ADMIN_SET_DUE_DATES = BASE_URL + "bqa/admin/due-dates"; // API ID:70

    public static final String SET_MAIL_TEMPLATE = BASE_URL + "bqa/admin/set/mail-template"; // API ID:71

    public static final String GET_MAIL_TEMPLATE = BASE_URL + "bqa/admin/get/mail-template/{mailId}"; // API ID:72

    public static final String LIST_MAIL_TEMPLATES = BASE_URL + "bqa/admin/list-mail-template"; // API ID:73

    public static final String NAC_SUBMIT_FEEDBACK = BASE_URL + "am/submit-nac-feedback"; // API ID:74

    public static final String ADD_REGISTRTION_DATE = BASE_URL + "/bqa/admin/add/registartion-date"; // API ID:75

    public static final String DELETE_REGISTRTION_DATE = BASE_URL + "/bqa/admin/delete/registartion-date/{dateId}"; // API ID:76

    public static final String CHANGE_APPLICATION_STATUS = BASE_URL + "/am/change-application-status"; // API ID:77

    public static final String AM_GRAND_ACCESS_TO_ILEP = BASE_URL + "/am/grand-access"; // API ID:78

    public static final String AM_CHANGE_SITE_VISIT_DATE= BASE_URL + "/am/change/site-visit-date"; // API ID:79

    public static final String AM_EVALUATE_WITH_REVERT_COMMENT= BASE_URL + "/am/evaluation/comment"; // API ID:80

    public static final String EVALUATION_FEEDBACK_COMMENTS_UPLOAD = BASE_URL + "feedback/file-upload"; // API ID 81

    public static final String FEEDBACK_STATUS_UPDATE = BASE_URL + "feedback/status-update"; // API ID 82

    public static final String FACTUAL_ACCURACY_STARTED = BASE_URL + "am/factual-accuracy-started"; // API ID 83

    public static final String FACTUAL_ACCURACY_COMPLETED= BASE_URL + "am/factual-accuracy-completed";// API ID 84

    public static final String DFO_APPROVE_APPEAL = BASE_URL + "/dfo/action-appeal"; // API ID 85

   // public static final String DFO_CHIEF_SIGN_REPORT = BASE_URL + "dfo-chief/report/sign"; //API ID 88


    public static final String DFO_CHIEF_SHARED_REPORT_CABINET = BASE_URL + "dfo-chief/shared/cabinet"; // API ID 86

    public static final String DFO_ADMIN_CABINET_APPROVED = BASE_URL + "dfo-chief/cabinet/approve"; // API ID 87

    public static final String CREATE_APPEAL = BASE_URL + "institution/create-appeal"; // API ID 88

  //  public static final String MCO_COMMENT = BASE_URL + "dfo-chief/mco/comment"; // API ID 89

    public static final String AM_FACTUAL_ACCURACY_WITH_DEFERED = BASE_URL + "am/defered/factual-accuracy/{formUniqueId}"; // API ID 90

    //Assign ilep special apis

    public static final String CREATE_ILEP_MEMBER_SEP = BASE_URL + "am/create-ilep-member-sep"; // API ID:91
    public static final String DFO_APPROVE_ILEP_SEP = BASE_URL + "dfo/approve-ilep-sep"; // API ID:92
    public static final String INSTITUTION_CONFLICT_SEP = BASE_URL + "institute/conflict/action-sep"; // API ID:93
    public static final String GET_AM_CONFLICT_DETAILS_SEP = BASE_URL + "am/get-conflict-form-details-sep/{formUniqueId}"; // API ID:94
    public static final String GET_INSTITUTE_OWN_CONFLICT_SEP = BASE_URL + "institute/get-institute-conflict-form-details-sep/{formUniqueId}"; // API ID:95
    public static final String AM_APPROVE_CONFLICT_SEP = BASE_URL + "am/conflict-approve-sep"; // API ID:96
    public static final String GET_INSTITUTE_CONFLICT_DFO_SEP = BASE_URL + "dfo/get-conflict-form-details-sep/{formUniqueId}"; // API ID:97
    public static final String DFO_APPROVE_ILEP_CONFLICT_SEP = BASE_URL + "dfo/approve-ilep-conflict-sep"; // API ID:98
    public static final String REMOVE_PANEL_SEP = BASE_URL + "am/remove-ilep-sep"; // API ID:99
    public static final String ILEP_CONFLICT_SEP = BASE_URL + "ilep/conflict/action-sep"; // API ID:100
    public static final String GET_FORM_ILEP_MEMBERS_SEP = BASE_URL + "institution/get-ilep-members-sep/{formUniqueId}"; // API ID:101
    public static final String GET_ASSIGNED_ILEP_MEMBERS_SEP = BASE_URL + "ilep/get-assigned-ilep-sep/{formUniqueId}"; //API ID:102
    public static final String AM_GRAND_ACCESS_TO_ILEP_SEP = BASE_URL + "/am/grand-access-sep"; // API ID:103
    public static final String GET_ALL_APPROVE_HISTORY = BASE_URL + "/institute/get-all-approve-status/{formUniqueId}"; //API ID:104
    public static final String SET_ALL_APPROVE_HISTORY = BASE_URL + "/institute/set-all-approve-status"; //API ID:105

    public static final String GET_LOGS = BASE_URL + "/form/logs"; //API ID 106

    public static final String GET_STATUS_LIST = BASE_URL + "/admin/status-list"; //API ID 107

    public static final String GET_STATUS = BASE_URL + "/admin/get-status/{statusId}"; //API ID 108

    public static final String EDIT_STATUS = BASE_URL + "/admin/edit-status"; //API ID 109

    public static final String REVERT_CONFLICT = BASE_URL + "/am/reset/conflict"; //API ID 110

    public static final String RESET_APPLICATION_STATUS = BASE_URL + "/dfo/reset-status/{formUniqueId}"; //API ID 111
    public static final String REJECT_APPLICATION_STATUS = BASE_URL + "am/reject/application/{formUniqueId}/{rejectionReason}"; // API ID 112

    public static final String UPLOAD_AGENDA_FORM = BASE_URL + "am/upload-agenda-form"; // API ID:113

    public static final String INSTITUTE_UPLOAD_AGENDA_FORM = BASE_URL + "am/institute-upload-agenda-form"; // API ID:114

    public static final String REJECT_SITE_VISIT_DOCUMENTS = BASE_URL + "am/reject/site-visit-doc/{formUniqueId}"; //API ID 115

    public static final String UPLOAD_REPORT_DOCUMENT = BASE_URL + "ilep/upload/report"; // API ID 116

    public static final String AM_UPLOAD_SITE_VISIT_EVALUATED_DOC = BASE_URL + "am/upload-site-visit-evaluated-doc"; // API ID:117

    public static final String ILEP_UPLOAD_SITE_VISIT_EVALUATED_DOC = BASE_URL + "ilep/upload-site-visit-evaluated-doc"; // API ID:118

    public static final String GET_REJECTION_REASON = BASE_URL + "institute/get-rejection-reason/{formUniqueId}"; // API ID:119

    public static final String CHANGE_PASSWORD = BASE_URL + "user/change-password"; //API ID:120

    public static final String GET_STANDARDS_WORK_FLOW = BASE_URL + "workflow/stds"; //API ID:121
    public static final String SAVE_STANDARDS_WORK_FLOW = BASE_URL + "workflow/stds"; //API ID:122

    public static final String GET_STANDARDS_WORK_FLOW_CF = BASE_URL + "workflow/cf"; //API ID:121
    public static final String SAVE_STANDARDS_WORK_FLOW_CF = BASE_URL + "workflow/cf"; //API ID:122
    public static final String EVALUATE_STANDARDS_WORK_FLOW = BASE_URL + "workflow/evaluate-stds"; //API ID:122

    public static final String GET_PROGRAM_STRUCTURE = BASE_URL + "workflow/ps"; //API ID:123
    public static final String SAVE_PROGRAM_STRUCTURE = BASE_URL + "workflow/ps"; //API ID:124

    public static final String GET_PROGRAM_STRUCTURE_FLOW = BASE_URL + "workflow/ps-flow"; //API ID:125
    public static final String SAVE_PROGRAM_STRUCTURE_FLOW = BASE_URL + "workflow/ps-flow"; //API ID:126
    public static final String VERIFY_PROGRAM_STRUCTURE_FLOW = BASE_URL + "workflow/verify-ps-flow"; //API ID:126

    public static final String GET_MAPPING_PANEL = BASE_URL + "workflow/mp"; //API ID:127
    public static final String SAVE_MAPPING_PANEL = BASE_URL + "workflow/mp"; //API ID:128
    public static final String GET_CONFIRMATION_PANEL = BASE_URL + "workflow/cp"; //API ID:129
    public static final String SAVE_CONFIRMATION_PANEL = BASE_URL + "workflow/cp"; //API ID:130

    public static final String ENFORCE_REVALIDATION = BASE_URL + "revalidation/enforce"; //API ID:131
    public static final String GET_LIMITED_USER_LIST = BASE_URL + "user/user-list"; //API ID:132

    public static final String SAVE_PROGRAM_STRUCTURE_CHECKLIST = BASE_URL + "ps/checklist"; //API ID:133
    public static final String GET_PROGRAM_STRUCTURE_CHECKLIST = BASE_URL + "/ps/checklist/{formUniqueId}/{slNo}"; //API ID:134
    public static final String APPROVE_PAYMENT = BASE_URL + "dfo/approve-payment/{formUniqueId}"; //API ID:135

    public static final String APPLY_FOR_ARCHIVAL = BASE_URL + "institution/archival/apply"; //API ID:136
    public static final String UPDATE_ARCHIVAL_STATUS = BASE_URL + "dfo/archival/update-status"; //API ID:137
    public static final String GET_ARCHIVAL_FORM = BASE_URL + "user/get-archival-form/{formUniqueId}"; //API ID:138

    public static final String ENFORCE_MONITORING = BASE_URL + "monitoring/enforce"; //API ID:139
    public static final String GET_MONITORING_FORM = BASE_URL + "user/get-monitoring-form/{formUniqueId}"; //API ID:140
    public static final String GET_INSTITUTES_HAVING_QUALIFICATIONS = BASE_URL + "dfo/get-existing-institutes"; //API ID:141

    public static final String GENERATE_PRE_SIGNED_URL = BASE_URL + "user/generate-pre-signed-url";  //API ID:142

    public static final String SWITCH_ROLE = BASE_URL + "user/switch-role";//API ID:143\
    public static final String GET_ROLES = BASE_URL + "user/roles";  //API ID:144

    public static final String DIRECTOR_USER_EDIT = BASE_URL + "user/edit"; // API ID:146

    public static final String GET_ILEP_USER = BASE_URL + "user/ilep-user-list"; // API ID:145

    public static final String GET_NON_ADMIN_NON_INSTITUTE_USER = BASE_URL + "admin/non-admin-non-inst-user-list"; // API ID:146

    public static final String INSTITUTE_USER_EDIT = BASE_URL + "admin/institute-edit";// API ID:148

    public static final String GET_INSTITUTE_USER_LIST = BASE_URL + "admin/institute-user-list";// API ID:149

    public static final String SAVE_QP_ID = BASE_URL + "user/save-qpid";// API ID:150

    public static final String GET_QUALIFICATION_DATA_FOR_BQA_TEAM = BASE_URL + "public/qualification-details";// API ID:162

    public static final String GET_FQ_DETAILS = BASE_URL + "user/get-fq-details" ; // API ID:163


}
