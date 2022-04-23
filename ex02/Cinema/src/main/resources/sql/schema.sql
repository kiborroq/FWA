CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS user_account (
    id BIGSERIAL NOT NULL,
    first_name VARCHAR(127) NOT NULL,
    last_name VARCHAR(127) NOT NULL,
    email VARCHAR(320) NOT NULL UNIQUE,
    password VARCHAR(72),
    avatar_id BIGINT,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS file (
    id BIGSERIAL NOT NULL,
    uuid UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    mime VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    date TIMESTAMP without time zone NOT NULL ,
    PRIMARY KEY(id),

    CONSTRAINT fk_user_id
    FOREIGN KEY(user_id)
    REFERENCES user_account(id)
);

CREATE TABLE IF NOT EXISTS session (
    id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    ip VARCHAR(40) NOT NULL,
    date TIMESTAMP without time zone NOT NULL ,
    PRIMARY KEY(id),

    CONSTRAINT fk_session_user_id
    FOREIGN KEY(user_id)
    REFERENCES user_account(id)
);


