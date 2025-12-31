-- Database script for mindteck  --

------- Client Registration -------


CREATE TABLE tbl_client_registration (
    app_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    device_id	VARCHAR(50),
    os_info	VARCHAR(30),
    model_info	TEXT,
    device_token TEXT,
    app_version VARCHAR(30),
    count	INTEGER,
    created_ip TEXT,
    updated_ip TEXT,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION
);

------- User  -------

CREATE TABLE IF NOT EXISTS tbl_user (
			user_id bigint NOT NULL AUTO_INCREMENT,
			username TEXT NOT NULL,
			email_id TEXT NOT NULL,
			password TEXT NOT NULL,
			user_type SMALLINT NOT NULL,
			sub_type SMALLINT NOT NULL,
			position TEXT,
			contact_number TEXT,
			report_access SMALLINT,
			access_right INTEGER,
			active SMALLINT,
			bio TEXT DEFAULT NULL,
			created_ip TEXT NOT NULL,
			updated_ip TEXT NOT NULL,
			created_app_id bigint NOT NULL,
			updated_app_id bigint NOT NULL,
			created_time DOUBLE PRECISION,
			last_updated_time DOUBLE PRECISION,
			PRIMARY KEY (user_id),
  			UNIQUE KEY user_id (user_id)
		);


------- Forgot password -------


CREATE TABLE IF NOT EXISTS tbl_forgot_password(
    id BIGINT NOT NULL AUTO_INCREMENT,
    email_id TEXT NOT NULL,
    otp_code  VARCHAR(20) NOT NULL,
    expiry_time DOUBLE NOT NULL,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id bigint NOT NULL,
    updated_app_id bigint NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_forgot_password_id (id)
);

------- User login info -------

CREATE TABLE IF NOT EXISTS tbl_user_login_info(
    login_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    user_type SMALLINT NOT NULL,
    app_id SMALLINT NOT NULL,
    token TEXT NOT NULL,
    refresh_token TEXT,
    expiry_time DOUBLE,
    public_key TEXT,
    private_key TEXT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id bigint NOT NULL,
    updated_app_id bigint NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (login_id),
    UNIQUE KEY tbl_user_login_info_id (login_id)
);


-- Registration form --


CREATE TABLE IF NOT EXISTS tbl_institute_form(
    form_id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    institute_name VARCHAR(250) NOT NULL,
    inst_app_lic_no VARCHAR(250),
    license_type SMALLINT  NULL DEFAULT NULL,
    approval_doc_path TEXT,
    issue_date DOUBLE PRECISION,
    exp_date DOUBLE PRECISION,
    is_bqa_reviewed SMALLINT,
    review_issue_date DOUBLE PRECISION,
    review_jud_result TEXT,
    is_offering_nan_loc_course_qa SMALLINT,
    offering_description TEXT,
    planned_sub_date DOUBLE PRECISION,
    poc_name VARCHAR(250),
    poc_email VARCHAR(50),
    poc_cn_number TEXT,
    poc_title TEXT,
    status SMALLINT,
    resubmit_status SMALLINT,
    resubmit_count BIGINT,
    last_resubmit_date DOUBLE PRECISION,
    current_submit_date DOUBLE PRECISION,
    current_stage SMALLINT,
    date_ext_status SMALLINT,
    date_ext_reason TEXT,
    reqsted_ext_date DOUBLE PRECISION,
    is_extenstion_requested SMALLINT,
    form_flow_history TEXT,
    institution_id BIGINT,
    assigned_app_manager BIGINT,
    others_data TEXT,
    not_applicable TEXT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id bigint NOT NULL,
    updated_app_id bigint NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (form_id),
    UNIQUE KEY tbl_institute_form_unique_id (form_unique_id)
);

-- Application Manager form --


CREATE TABLE IF NOT EXISTS tbl_form_appln_manager(
    form_id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    ip1 VARCHAR(250),
    ip1_status SMALLINT,
    ip2 VARCHAR(250),
    ip2_status SMALLINT,
    ip3 SMALLINT,
    ip3_status SMALLINT,
    ip4 SMALLINT,
    ip4_status SMALLINT,
    ip5_sub1 TEXT,
    ip5_sub2 DOUBLE PRECISION,
    ip5_sub3 DOUBLE PRECISION,
    ip5_status SMALLINT,
    ip6 SMALLINT,
    ip6_status SMALLINT,
    ip7 SMALLINT,
    ip7_status SMALLINT,
    ip8_sub1 SMALLINT,
    ip8_sub2 TEXT,
    ip8_status SMALLINT,
    ip9_sub_1 TEXT,
    ip9_sub_2 TEXT,
    ip9_sub_3 TEXT,
    ip9_status SMALLINT,
    ip10_sub_1 TEXT,
    ip10_sub_2 TEXT,
    ip10_sub_3 TEXT,
    ip10_status SMALLINT,
    ip11_sub_1 TEXT,
    ip11_sub_2 TEXT,
    ip11_sub_3 TEXT,
    ip11_sub_4 TEXT,
    ip11_status SMALLINT,
    ip12_sub_1 TEXT,
    ip12_sub_2 TEXT,
    ip12_sub_3 TEXT,
    ip12_sub_4 TEXT,
    ip12_status SMALLINT,
    qa_1 TEXT,
    qa_1_status SMALLINT,
    qa_2 TEXT,
    qa_2_status SMALLINT,
    qa_3 DOUBLE PRECISION,
    qa_3_status SMALLINT,
    qa_4 TEXT,
    qa_4_status SMALLINT,
    s1_atp_1_sub_1 TEXT,
    s1_atp_1_sub_2 TEXT,
    s1_atp_1_status SMALLINT,
    s1_atp_2_sub_1 TEXT,
    s1_atp_2_sub_2 TEXT,
    s1_atp_2_status SMALLINT,
    s1_atp_3_sub_1 TEXT,
    s1_atp_3_sub_2 TEXT,
    s1_atp_3_status SMALLINT,
    s1_atp_4_sub_1 TEXT,
    s1_atp_4_sub_2 TEXT,
    s1_atp_4_status SMALLINT,
    s1_atp_5_sub_1 TEXT,
    s1_atp_5_sub_2 TEXT,
    s1_atp_5_status SMALLINT,
    s1_atp_6_sub_1 TEXT,
    s1_atp_6_sub_2 TEXT,
    s1_atp_6_status SMALLINT,
    s2_qdar_1_sub_1 TEXT,
    s2_qdar_1_sub_2 TEXT,
    s2_qdar_1_status SMALLINT,
    s2_qdar_2_sub_1 TEXT,
    s2_qdar_2_sub_2 TEXT,
    s2_qdar_2_status SMALLINT,
    s2_qdar_3_sub_1 TEXT,
    s2_qdar_3_sub_2 TEXT,
    s2_qdar_3_status SMALLINT,
    s2_qdar_4_sub_1 TEXT,
    s2_qdar_4_sub_2 TEXT,
    s2_qdar_4_status SMALLINT,
    s2_qdar_5_sub_1 TEXT,
    s2_qdar_5_sub_2 TEXT,
    s2_qdar_5_status SMALLINT,
    s2_qdar_6_sub_1 TEXT,
    s2_qdar_6_sub_2 TEXT,
    s2_qdar_6_status SMALLINT,
    s3_adm_1_sub_1 TEXT,
    s3_adm_1_sub_2 TEXT,
    s3_adm_1_status SMALLINT,
    s3_adm_2_sub_1 TEXT,
    s3_adm_2_sub_2 TEXT,
    s3_adm_2_status SMALLINT,
    s3_adm_3_sub_1 TEXT,
    s3_adm_3_sub_2 TEXT,
    s3_adm_3_status SMALLINT,
    s3_adm_4_sub_1 TEXT,
    s3_adm_4_sub_2 TEXT,
    s3_adm_4_status SMALLINT,
    s3_adm_5_sub_1 TEXT,
    s3_adm_5_sub_2 TEXT,
    s3_adm_5_status SMALLINT,
    s3_adm_6_sub_1 TEXT,
    s3_adm_6_sub_2 TEXT,
    s3_adm_6_status SMALLINT,
    s3_adm_7_sub_1 TEXT,
    s3_adm_7_sub_2 TEXT,
    s3_adm_7_status SMALLINT,
    s3_adm_8_sub_1 TEXT,
    s3_adm_8_sub_2 TEXT,
    s3_adm_8_status SMALLINT,
    s3_adm_9_sub_1 TEXT,
    s3_adm_9_sub_2 TEXT,
    s3_adm_9_status SMALLINT,
    s4_c_1_sub_1 TEXT,
    s4_c_1_sub_2 TEXT,
    s4_c_1_status SMALLINT,
    s4_c_2_sub_1 TEXT,
    s4_c_2_sub_2 TEXT,
    s4_c_2_status SMALLINT,
    s4_c_3_sub_1 TEXT,
    s4_c_3_sub_2 TEXT,
    s4_c_3_status SMALLINT,
    s5_scqi_1_sub_1 TEXT,
    s5_scqi_1_sub_2 TEXT,
    s5_scqi_1_status SMALLINT,
    s5_scqi_2_sub_1 TEXT,
    s5_scqi_2_sub_2 TEXT,
    s5_scqi_2_status SMALLINT,
    s5_scqi_3_sub_1 TEXT,
    s5_scqi_3_sub_2 TEXT,
    s5_scqi_3_status SMALLINT,
    ap_cont_1 TEXT,
    ap_cont_2 TEXT,
    ap_cont_3 TEXT,
    ap_cont_4 TEXT,
    overall_status SMALLINT,
    extra_evd_elabo_status SMALLINT,
    is_extenstion_requested SMALLINT,
    extension_requested_date DOUBLE PRECISION,
    extension_requested_status SMALLINT,
    reason_for_extension TEXT,
    others_data TEXT,
    not_applicable TEXT,
    add_data_sub_date,
    created_by bigint,
    created_ip TEXT,
    updated_ip TEXT,
    created_app_id bigint,
    updated_app_id bigint,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (form_id),
    UNIQUE KEY tbl_form_appln_manager_form_unique_id (form_unique_id)
);

-- application manager mapping table --

CREATE TABLE IF NOT EXISTS tbl_app_mng_mapping(
    map_id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    user_id BIGINT,
    user_type SMALLINT,
    created_by bigint,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id bigint NOT NULL,
    updated_app_id bigint NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (map_id),
    UNIQUE KEY tbl_app_mng_mapping_unique_id (map_id)
);


CREATE TABLE IF NOT EXISTS tbl_ilep_panel(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    panel_id BIGINT NOT NULL,
    ilep_member_id BIGINT NOT NULL,
    is_head SMALLINT,
    is_dfo_approved SMALLINT,
    panel_status SMALLINT,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_ilep_panel_unique_id (id)
);


CREATE TABLE IF NOT EXISTS tbl_conflict_form(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    institution_conflict_data TEXT,
    ilep_conflict_data TEXT,
    institution_conflict_status SMALLINT,
    ilep_conflict_status SMALLINT,
    is_conflict_approved SMALLINT,
    is_history SMALLINT,
    panel_status SMALLINT,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_conflict_form_unique_id (id)
);

CREATE TABLE IF NOT EXISTS tbl_meeting(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    date_and_time DOUBLE PRECISION NOT NULL,
    mom_url TEXT,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_meeting_unique_id (id)
);

CREATE TABLE IF NOT EXISTS tbl_ilep_evaluation_form(
    evaluation_id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    s1_atp_1_status_comment TEXT,
    s1_atp_2_status_comment TEXT,
    s1_atp_3_status_comment TEXT,
    s1_atp_4_status_comment TEXT,
    s1_atp_5_status_comment TEXT,
    s1_atp_6_status_comment TEXT,
    s1_atp_condition TEXT,
    s1_atp_suggestion TEXT,
    s1_atp_judgement INTEGER,
    s2_qdar_1_status_comment TEXT,
    s2_qdar_2_status_comment TEXT,
    s2_qdar_3_status_comment TEXT,
    s2_qdar_4_status_comment TEXT,
    s2_qdar_5_status_comment TEXT,
    s2_qdar_6_status_comment TEXT,
    s2_qdar_condition TEXT,
    s2_qdar_suggestion TEXT,
    s2_qdar_judgement INTEGER,
    s3_adm_1_status_comment TEXT,
    s3_adm_2_status_comment TEXT,
    s3_adm_3_status_comment TEXT,
    s3_adm_4_status_comment TEXT,
    s3_adm_5_status_comment TEXT,
    s3_adm_6_status_comment TEXT,
    s3_adm_7_status_comment TEXT,
    s3_adm_8_status_comment TEXT,
    s3_adm_9_status_comment TEXT,
    s3_adm_condition TEXT,
    s3_adm_suggestion TEXT,
    s3_adm_judgement INTEGER,
    s4_c_1_status_comment TEXT,
    s4_c_2_status_comment TEXT,
    s4_c_3_status_comment TEXT,
    s4_condition TEXT,
    s4_suggestion TEXT,
    s4_judgement INTEGER,
    s5_scqi_1_status_comment TEXT,
    s5_scqi_2_status_comment TEXT,
    s5_scqi_3_status_comment TEXT,
    s5_scqi_condition TEXT,
    s5_scqi_suggestion TEXT,
    s5_scqi_judgement INTEGER,
    overall_judgement INTEGER,
    condition_fulfilment_date DOUBLE PRECISION,
    created_by bigint,
    created_ip TEXT,
    updated_ip TEXT,
    created_app_id bigint,
    updated_app_id bigint,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (evaluation_id),
    UNIQUE KEY tbl_ilep_evaluation_unique_id (evaluation_id),
    UNIQUE KEY tbl_ilep_evaluation_form_unique_id (form_unique_id)
);


CREATE TABLE IF NOT EXISTS tbl_institution_branches(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    building TEXT NOT NULL,
    road TEXT NOT NULL,
    block TEXT NOT NULL,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_site_visit(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    institute_name TEXT,
    person_involved TEXT,
    site_visit_required SMALLINT,
    visit_date DOUBLE PRECISION,
    date1 DOUBLE PRECISION,
    date2 DOUBLE PRECISION,
    date3 DOUBLE PRECISION,
    inst_rep_name TEXT,
    inst_rep_position TEXT,
    inst_rep_contact TEXT,
    inst_rep_email TEXT,
    agenda TEXT,
    evidence TEXT,
    question TEXT,
    protocol_feedback TEXT,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    confidentiality_status INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_site_date_change_requests(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    institute_name TEXT,
    ilep_member_names TEXT,
    licence_body TEXT,
    requested_date1 DOUBLE PRECISION,
    requested_date2 DOUBLE PRECISION,
    requested_date3 DOUBLE PRECISION,
    justification TEXT,
    am_approve SMALLINT,
    chief_approve SMALLINT,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_gdq_document(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    updated_form TEXT NOT NULL,
    form_updated_by BIGINT NOT NULL,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id)
);


ALTER TABLE tbl_site_visit
	ADD COLUMN confidentiality_status INTEGER;

ALTER TABLE `tbl_site_visit`
	ADD COLUMN `date1` DOUBLE NULL DEFAULT NULL AFTER `confidentiality_status`,
	ADD COLUMN `date2` DOUBLE NULL DEFAULT NULL AFTER `date1`,
	ADD COLUMN `date3` DOUBLE NULL DEFAULT NULL AFTER `date2`;




ALTER TABLE tbl_site_visit ADD COLUMN is_date_ext_req SMALLINT DEFAULT 0


ALTER TABLE tbl_ilep_evaluation_form
	ADD COLUMN partial_met_count INT NULL ,
	ADD COLUMN partial_met_date DOUBLE NULL DEFAULT NULL ,
	ADD COLUMN partial_met_comment TEXT NULL AFTER partial_met_date ,
	ADD COLUMN partial_met_status  INTEGER NULL AFTER partial_met_comment;


CREATE TABLE IF NOT EXISTS tbl_due_dates(
    id BIGINT NOT NULL AUTO_INCREMENT,
    `action` INTEGER NOT NULL,
    action_name INTEGER NOT NULL,
    `type` INTEGER NOT NULL,
    no_of_days INTEGER NOT NULL,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id)
);



ALTER TABLE tbl_doc_feedback
RENAME COLUMN qac_additional_info_status TO institution_additional_info_status;

ALTER TABLE tbl_institute_form
ADD COLUMN completed_status TEXT DEFAULT NULL  AFTER sub_status;


ALTER TABLE tbl_site_visit
	ADD COLUMN report_file TEXT NULL AFTER is_date_ext_req;

	ALTER TABLE tbl_ilep_evaluation_form
	ADD COLUMN am_feedback_comment TEXT NULL AFTER partial_met_status;

ALTER TABLE tbl_user_login_info
	ADD COLUMN aes_key_encrypted TEXT NULL AFTER private_key;

ALTER TABLE tbl_user_login_info
	ADD COLUMN aes_key TEXT NULL AFTER private_key;


ALTER TABLE tbl_ilep_panel
	ADD COLUMN grand_access INTEGER DEFAULT 0 AFTER panel_status;


ALTER TABLE tbl_ilep_evaluation_form
ADD COLUMN dfo_chief_eval_cmnt TEXT DEFAULT NULL AFTER partial_met_comment,
ADD COLUMN gdq_ac_eval_cmnt TEXT DEFAULT NULL AFTER dfo_chief_eval_cmnt,
ADD COLUMN qac_eval_cmnt TEXT DEFAULT NULL AFTER gdq_ac_eval_cmnt;



CREATE TABLE IF NOT EXISTS tbl_ilep(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    ilep_member_id BIGINT NOT NULL,
    is_head SMALLINT,
    is_dfo_approved SMALLINT,
    grand_access INTEGER DEFAULT 0,

    institution_conflict_data TEXT,
    ilep_conflict_data TEXT,
    institution_conflict_status SMALLINT,
    ilep_conflict_status SMALLINT,
    is_conflict_approved_am SMALLINT,
    is_conflict_approved_dfo SMALLINT,
    is_history SMALLINT,

    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_ilep_panel_unique_id (id)
);