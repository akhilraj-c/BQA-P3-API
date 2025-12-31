ALTER TABLE tbl_ilep_evaluation_form
	ADD COLUMN am_feedback_comment TEXT NULL AFTER partial_met_status;


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


	ALTER TABLE tbl_ilep_evaluation_form
	ADD COLUMN dfo_chief_plain_cmnt TEXT NULL AFTER dfo_chief_eval_cmnt,
	ADD COLUMN gdq_ac_plain_cmnt TEXT NULL AFTER gdq_ac_eval_cmnt,
	ADD COLUMN qac_plain_cmnt TEXT NULL AFTER qac_eval_cmnt
	;



ALTER TABLE tbl_institute_form
    ADD COLUMN factual_accuracy_file TEXT DEFAULT NULL AFTER institution_id;

	ALTER TABLE tbl_ilep_evaluation_form
	ADD COLUMN inst_cmnt_bck_file TEXT NULL AFTER qac_plain_cmnt,
	ADD COLUMN inst_cmnt_bck TEXT NULL AFTER inst_cmnt_bck_file;


	ALTER TABLE tbl_institute_form
        ADD COLUMN inst_appeal SMALLINT  DEFAULT 0 AFTER completed_status,
           ADD COLUMN inst_appeal_approve SMALLINT  DEFAULT 0 AFTER inst_appeal,
        ADD COLUMN inst_appeal_exp DOUBLE PRECISION DEFAULT 0 AFTER inst_appeal_approve;



ALTER TABLE tbl_doc_feedback
    ADD COLUMN dfo_cmnt_mco TEXT DEFAULT NULL AFTER nac_feedback_file,
    ADD COLUMN dfo_file_mco TEXT DEFAULT NULL AFTER dfo_cmnt_mco,
    ADD COLUMN dfo_signed_file TEXT DEFAULT NULL AFTER dfo_file_mco,
    ADD COLUMN dfo_signed_status SMALLINT DEFAULT 0 AFTER  dfo_signed_file,
    ADD COLUMN dfo_shared_cabinet SMALLINT DEFAULT 0 AFTER dfo_signed_status,
    ADD COLUMN dfo_cabinet_approved SMALLINT DEFAULT 0 AFTER dfo_shared_cabinet,
    ADD COLUMN mcu_file_comment TEXT DEFAULT NULL  AFTER dfo_cabinet_approved;


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



ALTER TABLE tbl_ilep_evaluation_form
ADD COLUMN s1_atp_judgement_history TEXT DEFAULT NULL AFTER s1_atp_judgement,
ADD COLUMN s2_qdar_judgement_history TEXT DEFAULT NULL AFTER s2_qdar_judgement,
ADD COLUMN s3_adm_judgement_history TEXT DEFAULT NULL AFTER s3_adm_judgement,
ADD COLUMN s4_judgement_history TEXT DEFAULT NULL AFTER s4_judgement,
ADD COLUMN s5_scqi_judgement_history TEXT DEFAULT NULL AFTER s5_scqi_judgement,
ADD COLUMN overall_judgement_history TEXT DEFAULT NULL AFTER overall_judgement
;


ALTER TABLE tbl_institute_form
ADD COLUMN over_all_approve_history TEXT DEFAULT NULL AFTER inst_appeal_exp;




CREATE TABLE IF NOT EXISTS tbl_log(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    user_type SMALLINT,
    user_sub_type SMALLINT,
    user_name TEXT,
    previous_status INTEGER,
    previous_message TEXT,
    changed_status INTEGER,
    changed_message TEXT,

    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT DEFAULT NULL,
    updated_app_id BIGINT DEFAULT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_log_unique_id (id)
);


CREATE TABLE IF NOT EXISTS tbl_dues_count(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    application_status INTEGER NOT NULL,
    due_count INTEGER NOT NULL,

    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id)
	 );















CREATE TABLE IF NOT EXISTS tbl_application_status(
    status_id BIGINT NOT NULL AUTO_INCREMENT,
    status_number BIGINT NOT NULL,
    english_text TEXT,
    arab_text TEXT,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (status_id),
    UNIQUE KEY tbl_application_status_unique_id (status_id)
);



INSERT INTO tbl_application_status (status_number)
    VALUES(0),(1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),
    (33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(52),(53),(54),(55),(56),(57),(58),(59),(60),(61),(62),(63),
    (64),(65),(66),(67),(68),(69),(70),(71),(72),(73),(74),(75),(76),(77),(78),(79),(80),(81),(82),(83)
    ,(84),(85);