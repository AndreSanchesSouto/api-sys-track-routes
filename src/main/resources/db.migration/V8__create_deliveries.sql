CREATE TABLE deliveries (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    delivery_request INT NOT NULL,
    status VARCHAR(50),
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL,
    general_manager_id VARCHAR(50) NULL,
    latitude DECIMAL(8,6) NULL,
    longitude DECIMAL(9,6) NULL,
    client_id UUID,
    vehicle_id UUID,
    address_id UUID,
    driver_id UUID,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES users(id) ON DELETE CASCADE

);
