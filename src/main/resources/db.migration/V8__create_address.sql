CREATE TABLE address (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    address_type VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    number VARCHAR(100) NOT NULL,
    zip_code CHAR(8) NULL,
    reference VARCHAR(100) NULL,
    client_fk UUID,
    city_fk UUID,
    FOREIGN KEY (client_fk) REFERENCES client(id),
    FOREIGN KEY (city_fk) REFERENCES city(id)
);