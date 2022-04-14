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
    uuid UUID NOT NULL,
    name VARCHAR(127) NOT NULL,
    mime VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY(id),

    CONSTRAINT fk_user_id
    FOREIGN KEY(user_id)
    REFERENCES user_account(id)
);

ALTER TABLE user_account
ADD CONSTRAINT fk_avatar_id
FOREIGN KEY (avatar_id)
REFERENCES file(id);

