CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    status VARCHAR(100) NULL,
    role VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL
);