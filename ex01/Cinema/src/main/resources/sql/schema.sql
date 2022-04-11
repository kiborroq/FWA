CREATE TABLE IF NOT EXISTS user_account (
    id BIGSERIAL NOT NULL,
    first_name VARCHAR(127) NOT NULL,
    last_name VARCHAR(127) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    password VARCHAR(72)
);
