CREATE TABLE vehicle (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    license_plate_number VARCHAR(100) NOT NULL UNIQUE,
    image_url VARCHAR(500) NULL,
    side_number VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    year_dt VARCHAR(100) NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL,
    general_manager_id VARCHAR(50) NULL,
    user_fk UUID,
    FOREIGN KEY (user_fk) REFERENCES users(id)
);