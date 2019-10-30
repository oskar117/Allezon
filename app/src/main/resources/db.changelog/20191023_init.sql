CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users
(
    id   BIGSERIAL NOT NULL,
    username VARCHAR NOT NULL,
    name VARCHAR   NOT NULL,
    surname VARCHAR   NOT NULL,
    password VARCHAR NOT NULL,
    birth_date DATE,
    email VARCHAR NOT NULL,

    PRIMARY KEY (id)
);