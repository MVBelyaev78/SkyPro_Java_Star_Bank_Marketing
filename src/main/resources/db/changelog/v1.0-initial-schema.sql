CREATE TABLE recommendation_products
(
    id           UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id   UUID         NOT NULL,
    product_text TEXT         NOT NULL
);

CREATE TABLE recommendation_rules
(
    id                UUID PRIMARY KEY,
    recommendation_id UUID         NOT NULL,
    query             VARCHAR(255) NOT NULL,
    arguments         TEXT[]       NOT NULL,
    negate            BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_rules_recommendation_id
        FOREIGN KEY (recommendation_id)
            REFERENCES recommendation_products (id)
            ON DELETE CASCADE
);

CREATE TABLE recommendation_products_stat
(
    id                UUID PRIMARY KEY,
    recommendation_id UUID NOT NULL,
    activation_date   TIMESTAMPTZ DEFAULT NOW(),
    CONSTRAINT fk_stat_recommendation_id
        FOREIGN KEY (recommendation_id)
            REFERENCES recommendation_products (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_recommendation_product_product_id ON recommendation_products (product_id);
