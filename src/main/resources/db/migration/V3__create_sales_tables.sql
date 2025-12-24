CREATE TABLE sales
(
    id           BIGSERIAL PRIMARY KEY,
    code         VARCHAR(10) NOT NULL UNIQUE,
    total_amount NUMERIC(19, 2) DEFAULT 0,
    status       VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE sale_items
(
    id         BIGSERIAL PRIMARY KEY,
    sale_id    BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    quantity   NUMERIC(19, 3) NOT NULL,
    unit_price NUMERIC(19, 2) NOT NULL,
    subtotal   NUMERIC(19, 2) NOT NULL,

    CONSTRAINT fk_sale_items_sale
        FOREIGN KEY (sale_id) REFERENCES sales (id) ON DELETE CASCADE,
    CONSTRAINT fk_sale_items_product
        FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE INDEX idx_sales_code ON sales(code);
CREATE INDEX idx_sales_date ON sales(created_at);
CREATE INDEX idx_sales_items_sale ON sale_items(sale_id);