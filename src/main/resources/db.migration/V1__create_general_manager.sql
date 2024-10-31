CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE general_manager (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(100) NULL,
    creation_dt DATE NOT NULL,
    inactivation_dt DATE NULL
);