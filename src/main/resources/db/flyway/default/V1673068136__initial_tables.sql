CREATE TABLE IF NOT EXISTS tbl_client_registration (
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
            bio TEXT NOT NULL,
			created_ip TEXT NOT NULL,
			updated_ip TEXT NOT NULL,
			created_app_id bigint NOT NULL,
			updated_app_id bigint NOT NULL,
			created_time DOUBLE PRECISION,
			last_updated_time DOUBLE PRECISION,
			PRIMARY KEY (user_id),
  			UNIQUE KEY user_id (user_id)
		);



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