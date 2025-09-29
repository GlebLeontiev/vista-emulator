CREATE TABLE alerts
(
    id          BIGSERIAL PRIMARY KEY,
    date        BIGINT       NOT NULL,
    card_id     BIGINT,
    type        VARCHAR(255) NOT NULL,
    message     TEXT,
    customer_id BIGINT REFERENCES  customers (id)
);
