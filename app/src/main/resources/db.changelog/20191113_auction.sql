

CREATE TABLE section(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);


CREATE TABLE category(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    section_id BIGINT,

    CONSTRAINT fk_section_id FOREIGN KEY (section_id) REFERENCES section(id)
);

CREATE TABLE auction(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR,
    section_id BIGINT NOT NULL,
    price DECIMAL NOT NULL,
    category_id BIGINT NOT NULL,

    CONSTRAINT fk_section_id FOREIGN KEY (section_id) REFERENCES section(id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE photo(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    auction_id BIGINT NOT NULL,
    url VARCHAR NOT NULL,

    CONSTRAINT fk_auction_id FOREIGN KEY (auction_id) REFERENCES auction(id)
);

CREATE TABLE parameter(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    key VARCHAR NOT NULL
);

CREATE TABLE auction_parameter(
    auction_id BIGINT NOT NULL,
    parameter_id BIGINT NOT NULL,
    value varchar,

    PRIMARY KEY(auction_id, parameter_id),
    CONSTRAINT fk_auction_id FOREIGN KEY (auction_id) REFERENCES auction(id),
    CONSTRAINT fk_parameter_id FOREIGN KEY (parameter_id) REFERENCES parameter(id)
);
