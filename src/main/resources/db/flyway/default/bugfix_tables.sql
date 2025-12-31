ALTER TABLE tbl_ilep_evaluation_form
ADD COLUMN partial_met_file TEXT DEFAULT NULL AFTER partial_met_comment,
ADD COLUMN am_feedback_file TEXT DEFAULT NULL AFTER am_feedback_comment

;

ALTER TABLE tbl_institute_form
ADD COLUMN factual_accuracy_comment TEXT DEFAULT NULL AFTER factual_accuracy_file;


ALTER TABLE tbl_doc_feedback
ADD COLUMN dfo_cabinet_approved_comment TEXT DEFAULT NULL AFTER dfo_cabinet_approved,
ADD COLUMN dfo_cabinet_approved_file TEXT DEFAULT NULL AFTER dfo_cabinet_approved_comment,
ADD COLUMN dfo_approve_appeal_comment TEXT DEFAULT NULL AFTER dfo_cabinet_approved,
ADD COLUMN dfo_approve_appeal_file TEXT DEFAULT NULL AFTER dfo_approve_appeal_comment,
ADD COLUMN am_approve_report_comment TEXT DEFAULT NULL AFTER dfo_approve_appeal_file,
ADD COLUMN am_approve_report_file TEXT DEFAULT NULL AFTER am_approve_report_comment,
ADD COLUMN doc_shared_to_nac_comment TEXT DEFAULT NULL AFTER am_approve_report_file,
ADD COLUMN doc_shared_to_nac_file TEXT DEFAULT NULL AFTER doc_shared_to_nac_comment,
ADD COLUMN doc_shared_to_qac_comment TEXT DEFAULT NULL AFTER doc_shared_to_nac_file,
ADD COLUMN doc_shared_to_qac_file TEXT DEFAULT NULL AFTER doc_shared_to_qac_comment,
ADD COLUMN factual_accuracy_start_comment TEXT DEFAULT NULL AFTER doc_shared_to_qac_file,
ADD COLUMN factual_accuracy_start_file TEXT DEFAULT NULL AFTER factual_accuracy_start_comment,
ADD COLUMN qac_approve_comment TEXT DEFAULT NULL AFTER factual_accuracy_start_file,
ADD COLUMN qac_approve_file TEXT DEFAULT NULL AFTER qac_approve_comment,
ADD COLUMN dfo_chief_approve_comment TEXT DEFAULT NULL AFTER qac_approve_file,
ADD COLUMN dfo_chief_approve_file TEXT DEFAULT NULL AFTER dfo_chief_approve_comment,
ADD COLUMN gdq_ac_approve_comment TEXT DEFAULT NULL AFTER dfo_chief_approve_file,
ADD COLUMN gdq_ac_approve_file TEXT DEFAULT NULL AFTER gdq_ac_approve_comment,
ADD COLUMN institute_appeal_comment TEXT DEFAULT NULL AFTER gdq_ac_approve_file,
ADD COLUMN institute_appeal_file TEXT DEFAULT NULL AFTER institute_appeal_comment,
ADD COLUMN dfo_shared_cabinet_comment TEXT DEFAULT NULL AFTER dfo_shared_cabinet,
ADD COLUMN dfo_shared_cabinet_file TEXT DEFAULT NULL AFTER dfo_shared_cabinet_comment,
ADD COLUMN dfo_signed_comment TEXT DEFAULT NULL AFTER dfo_signed_file,
ADD COLUMN nac_file TEXT DEFAULT NULL AFTER nac_comment,
ADD COLUMN gdq_ac_shared_comment TEXT DEFAULT NULL AFTER nac_file,
ADD COLUMN gdq_ac_shared_file TEXT DEFAULT NULL AFTER gdq_ac_shared_comment
;

ALTER TABLE tbl_planned_submission_dates
ADD COLUMN submission_end_date DOUBLE NOT NULL DEFAULT 0 AFTER planned_date;


ALTER TABLE tbl_institute_form
ADD COLUMN current_status_due_date DOUBLE DEFAULT 0 NULL AFTER over_all_approve_history;


ALTER TABLE tbl_institute_form
ADD COLUMN regulatory_other_data TEXT DEFAULT NULL AFTER over_all_approve_history,
ADD COLUMN licenced_by_other_data TEXT DEFAULT NULL AFTER regulatory_other_data,
ADD COLUMN institution_type_other_data TEXT DEFAULT NULL AFTER licenced_by_other_data,
ADD COLUMN field_other_data TEXT DEFAULT NULL AFTER institution_type_other_data;

ALTER TABLE tbl_form_appln_manager
ADD COLUMN regulatory_other_data TEXT DEFAULT NULL AFTER others_data,
ADD COLUMN licenced_by_other_data TEXT DEFAULT NULL AFTER regulatory_other_data,
ADD COLUMN institution_type_other_data TEXT DEFAULT NULL AFTER licenced_by_other_data,
ADD COLUMN field_other_data TEXT DEFAULT NULL AFTER institution_type_other_data;

ALTER TABLE tbl_institute_form
ADD COLUMN random_date DOUBLE DEFAULT 0 NULL AFTER planned_sub_date;


ALTER TABLE tbl_site_visit 
ADD COLUMN agenda_form TEXT DEFAULT NULL AFTER report_file,
ADD COLUMN agenda_comment TEXT DEFAULT NULL AFTER agenda_form,
ADD COLUMN filled_agenda_form TEXT DEFAULT NULL AFTER agenda_comment,
ADD COLUMN filled_agenda_comment TEXT DEFAULT NULL AFTER filled_agenda_form,
ADD COLUMN am_mark_site_visit INTEGER DEFAULT 0 AFTER filled_agenda_comment,
ADD COLUMN am_mark_site_visit_comment TEXT DEFAULT NULL AFTER am_mark_site_visit,
ADD COLUMN am_reject_document_comment TEXT DEFAULT NULL AFTER am_mark_site_visit_comment
;

ALTER TABLE tbl_meeting
ADD COLUMN first_link_or_location TEXT DEFAULT NULL AFTER mom_url
ADD COLUMN first_meeting_type INTEGER DEFAULT NULL AFTER link_or_location,
ADD COLUMN first_method INTEGER DEFAULT NULL AFTER meeting_type,
ADD COLUMN dead_line DOUBLE DEFAULT 0 NULL AFTER method,
ADD COLUMN first_meet_report_doc_cmnts TEXT DEFAULT  NULL AFTER dead_line,
ADD COLUMN first_meet_ques_cmnts TEXT DEFAULT  NULL AFTER first_meet_report_doc_cmnts,
ADD COLUMN first_meet_evid_cmnts TEXT DEFAULT  NULL AFTER first_meet_ques_cmnts,
ADD COLUMN second_date_and_time TEXT DEFAULT NULL AFTER first_meet_evid_cmnts,
ADD COLUMN second_link_or_location TEXT DEFAULT NULL AFTER second_date_and_time,
ADD COLUMN second_meeting_type INTEGER DEFAULT NULL AFTER second_link_or_location,
ADD COLUMN second_method INTEGER DEFAULT NULL AFTER second_meeting_type,
ADD COLUMN second_meet_report_doc_cmnts TEXT DEFAULT  NULL AFTER first_meet_evid_cmnts,
ADD COLUMN second_meet_ques_cmnts TEXT DEFAULT  NULL AFTER second_meet_report_doc_cmnts,
ADD COLUMN second_meet_evid_cmnts TEXT DEFAULT  NULL AFTER second_meet_ques_cmnts
;

ALTER TABLE tbl_institute_form
ADD COLUMN rejection_reason TEXT DEFAULT NULL AFTER current_status_due_date,
ADD COLUMN Rejected_by_user_type DOUBLE DEFAULT 0 NULL AFTER rejection_reason;

ALTER TABLE tbl_site_visit
ADD COLUMN am_evaluate_uploaded_site_visit_doc TEXT DEFAULT NULL,
ADD COLUMN ilep_evaluate_uploaded_site_visit_doc TEXT DEFAULT NULL AFTER am_evaluate_uploaded_site_visit_doc
;


ALTER TABLE tbl_form_appln_manager
ADD COLUMN file_path_parameter TEXT DEFAULT NULL AFTER add_data_sub_date
;

ALTER TABLE tbl_ilep_evaluation_form
ADD COLUMN dec1 TEXT DEFAULT NULL AFTER inst_cmnt_bck,
ADD COLUMN dec2 TEXT DEFAULT NULL AFTER dec1,
ADD COLUMN dec3 TEXT DEFAULT NULL AFTER dec2,
ADD COLUMN draft TEXT DEFAULT NULL AFTER dec3,
ADD COLUMN condfil TEXT DEFAULT NULL AFTER draft
;

ALTER TABLE tbl_site_visit
ADD COLUMN inst_upload_ext_evd_dead_line BIGINT DEFAULT NULL AFTER ilep_evaluate_uploaded_site_visit_doc
;
ALTER TABLE tbl_institute_form
ADD COLUMN inst_factual_accuracy_dead_line BIGINT DEFAULT NULL AFTER factual_accuracy_comment
;

ALTER TABLE tbl_ilep_evaluation_form
ADD COLUMN is_extenstion_requested INT DEFAULT NULL AFTER condfil,
ADD COLUMN extension_requested_date BIGINT DEFAULT NULL AFTER is_extenstion_requested,
ADD COLUMN extension_requested_status INT DEFAULT NULL AFTER extension_requested_date,
ADD COLUMN reason_for_extension TEXT DEFAULT NULL AFTER extension_requested_status
;