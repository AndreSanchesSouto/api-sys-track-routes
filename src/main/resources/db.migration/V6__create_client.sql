CREATE TABLE client (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_type VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL,
    created_dt TIMESTAMP NOT NULL,
    inactivated_dt TIMESTAMP NULL
);