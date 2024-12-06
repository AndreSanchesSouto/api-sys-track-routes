CREATE TABLE vehicle (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    license_plate_number VARCHAR(100) NOT NULL UNIQUE,
    side_number VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    year_dt VARCHAR(100) NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_dt TIMESTAMP NOT NULL,
    inactivated_dt TIMESTAMP NULL,
    driver_fk UUID,
    shipping_manager_fk UUID,
    general_manager_fk UUID,
    FOREIGN KEY (driver_fk) REFERENCES driver(id),
    FOREIGN KEY (shipping_manager_fk) REFERENCES shipping_manager(id),
    FOREIGN KEY (general_manager_fk) REFERENCES general_manager(id)
);