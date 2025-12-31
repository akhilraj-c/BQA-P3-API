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
	ADD COLUMN nac_feedback_description TEXT NULL AFTER mcu_scanned_file,
	ADD COLUMN nac_feedback_file TEXT NULL AFTER nac_feedback_description;


CREATE TABLE IF NOT EXISTS tbl_mail_template(
    id BIGINT NOT NULL AUTO_INCREMENT,
    template_name VARCHAR(100)  NOT NULL,
    template_code VARCHAR(100)  NOT NULL,
    template_description VARCHAR(200) NULL,
    template_body TEXT NULL,
    created_by BIGINT,
    created_ip TEXT NOT NULL,
    updated_ip TEXT NOT NULL,
    created_app_id BIGINT NOT NULL,
    updated_app_id BIGINT NOT NULL,
    created_time DOUBLE PRECISION,
    last_updated_time DOUBLE PRECISION,
    PRIMARY KEY (id),
    UNIQUE KEY tbl_mail_template_unique_id (template_code)
);


--INSERT INTO tbl_mail_template(template_name, template_code, template_description)
--    VALUES('On Dfo Assign AM Mail', 'DFO_ASSIGN_AM', 'To get details of APPLICATION MANAGER with contact details'),
--          ('On administrative check by application manager', 'AM_ADMINISTRATIVE_CHECK',
--           'On administrative check by application manager to institute to complete the incomplete section as additional task with a deadline'),
--          ('On Application Manager create ILEP Panel', 'ILEP_PANEL_CREATE', 'On Application Manager create ILEP Panel to DFO director to approve the ILEP panel'),
--          ('On DFO Director approves the ILEP panel', 'DFOA_APPROVE_ILEP_PANEL', 'On DFO Director approves the ILEP panel to INSTITUTE user to sign no conflict form'),
--          ('On Application manager not approving conflict', 'AM_NOT_APPROVE_CONFLICT',
--           'On Application manager not approving the conflict form against ILEP panel by institute to ILEP Panel to sign no conflict with institution details'),
--          ('On ILEP members signing the no conflict form', 'ILEP_SIGN_NON_CONFLICT',
--           'On ILEP members signing the no conflict form to Application manager to schedule meeting with GDQ/DFO director'),
--          ('On ILEP submit final summary', 'ILEP_SUBMIT_FINAL_SUMMARY', 'On ILEP member update outcome summary with final jufgement to Application manager to update site visit required status'),
--          ('On Application manager create site visit', 'AM_CREATE_SITE_VISIT',
--          'On Application manager creating site visit with given options to INSTITUTE user to request for date extension or accept the date'),
--          ('On Institute signs no confidentiality', 'INSTITUE_SIGN_NON_CONFIDENTIALITY', 'On Institute signs no confidentiality agreement to all ILEP members to update event documnet forms '),
--          ('On Application manage approves ILEP report', 'AM_APPROVE_ILEP_REPORT', 'On Application manage approves the report updated by ILEP Panel DFO Director fo review and approval'),
--          ('On DFO not approving the report', 'DFO_NOT_APPROVE_REPORT', 'On DFO not approving the report to Application Manager for additional comments '),
--          ('On QAC approving the report', 'QAC_APPROVE_REPORT', 'On QAC approving the report to Application manager/DFO to send document for factual check |'),
--          ('On QAC not approving the report ', 'QAC_NOT_APPROVE_REPORT', '.On QAC not approving the report DFO director to add additional comments');


ALTER TABLE tbl_planned_submission_dates
	ADD COLUMN created_by BIGINT NOT NULL,
	ADD COLUMN created_app_id BIGINT NOT NULL,
    ADD COLUMN updated_app_id BIGINT NOT NULL;


ALTER TABLE tbl_doc_feedback
RENAME COLUMN qac_additional_info_status TO institution_additional_info_status;


ALTER TABLE tbl_institute_form
ADD COLUMN completed_status TEXT DEFAULT NULL  AFTER sub_status;


ALTER TABLE tbl_user_login_info
	ADD COLUMN aes_key TEXT NULL AFTER private_key;