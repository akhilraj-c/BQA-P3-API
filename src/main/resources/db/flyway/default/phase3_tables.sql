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
    condition_fulfilment_date INTEGER,
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


CREATE TABLE IF NOT EXISTS tbl_site_date_change_requests(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    institute_name TEXT,
    ilep_member_names TEXT,
    licence_body TEXT,
    requested_date DOUBLE PRECISION,
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