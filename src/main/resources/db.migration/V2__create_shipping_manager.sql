CREATE TABLE shipping_manager (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_dt TIMESTAMP NOT NULL,
    inactivated_dt TIMESTAMP NULL
);