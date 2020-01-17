CREATE TABLE cart(
    id BIGSERIAL,
    user_id BIGINT,
    expiration_date TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE cart_items(
    id BIGSERIAL,
    cart_id BIGINT,
    auction_id BIGINT,

    PRIMARY KEY (id),
    CONSTRAINT fk_cart_id FOREIGN KEY (cart_id) REFERENCES cart(id),
    CONSTRAINT fk_auction_id FOREIGN KEY (auction_id) REFERENCES auction(id)

);