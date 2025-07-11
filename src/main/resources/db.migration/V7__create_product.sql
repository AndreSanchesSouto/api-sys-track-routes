CREATE TABLE product(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_url VARCHAR(500) NULL,
    description VARCHAR(1000),
    quantity INTEGER NOT NULL,
    unitValue NUMERIC(10, 2) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    measure VARCHAR(100) NOT NULL,
    general_manager_id VARCHAR(50) NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL;
)