
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
    first_date_and_time DOUBLE PRECISION NOT NULL,
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



