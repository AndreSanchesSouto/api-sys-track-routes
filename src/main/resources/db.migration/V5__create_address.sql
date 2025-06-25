CREATE TABLE address (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    zip_code CHAR(8) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(100) NOT NULL,
    address_type VARCHAR(100) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state CHAR(2) NOT NULL,
    complement VARCHAR(100) NULL,
    reference_point VARCHAR(100) NULL,
    latitude DECIMAL(8,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL,
    client_id UUID,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);