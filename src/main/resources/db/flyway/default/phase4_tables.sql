CREATE TABLE IF NOT EXISTS tbl_doc_feedback(
    id BIGINT NOT NULL AUTO_INCREMENT,
    form_unique_id BIGINT NOT NULL,
    qac_feedback_description TEXT NULL,
    qac_feedback_file TEXT NULL,
    qac_additional_info_status SMALLINT NULL,
    institution_comment TEXT NULL,
    submitted_report_file TEXT NULL,
    nac_comment TEXT NULL,
    nac_additional_info_status SMALLINT NULL,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id)
);


ALTER TABLE tbl_doc_feedback ADD COLUMN serial_no VARCHAR(250);


ALTER TABLE tbl_site_date_change_requests
RENAME COLUMN requested_date TO requested_date_1,
ADD requested_date_2 DOUBLE PRECISION DEFAULT NULL AFTER requested_date_1,
ADD requested_date_3 DOUBLE PRECISION DEFAULT NULL AFTER requested_date_2
;

ALTER TABLE tbl_ilep_evaluation_form
	ADD COLUMN partial_met_count INT NULL ,
	ADD COLUMN partial_met_date DOUBLE NULL DEFAULT NULL ,
	ADD COLUMN partial_met_comment TEXT NULL AFTER partial_met_date ,
	ADD COLUMN partial_met_status  INTEGER NULL AFTER partial_met_comment;

ALTER TABLE tbl_institute_form
	ADD COLUMN sub_status SMALLINT NULL DEFAULT NULL;

ALTER TABLE tbl_doc_feedback
	ADD COLUMN mcu_scanned_file TEXT NULL AFTER serial_no;