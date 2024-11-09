CREATE TABLE vehicle (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    license_plate_number VARCHAR(100) NOT NULL UNIQUE,
    side_number VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    status VARCHAR(100) NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL,
    driver_fk UUID,
    FOREIGN KEY (driver_fk) REFERENCES driver(id)
);