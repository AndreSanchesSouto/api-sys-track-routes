CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE EXTENSION IF NOT EXISTS unaccent;
CREATE INDEX idx_deliveries_created_dt_char
ON deliveries (TO_CHAR(created_dt, 'YYYY-MM-DD'));

CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_url VARCHAR(500) NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    document VARCHAR(100) NULL UNIQUE,
    password TEXT NOT NULL,
    status VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL,
    general_manager_id VARCHAR(50) NULL
);