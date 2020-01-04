create table password_reset(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    email_id BIGINT NOT NULL,
    token VARCHAR NOT NULL,
    expiration_date timestamp NOT NULL
);