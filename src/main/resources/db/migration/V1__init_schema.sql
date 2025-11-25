CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    active BOOLEAN DEFAULT true
);

CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    barcode VARCHAR(20) UNIQUE,
    sku VARCHAR(50),
    cost_price DECIMAL(19, 2),
    sale_price DECIMAL(19, 2),
    stock_quantity DECIMAL (19, 3) DEFAULT 0,
    unit_of_measure VARCHAR(5) NOT NULL DEFAULT 'UN',
    ncm_code VARCHAR(8),
    category_id BIGINT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id)
);