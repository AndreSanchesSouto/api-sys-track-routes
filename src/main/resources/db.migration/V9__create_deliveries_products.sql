CREATE TABLE deliveries_products(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    delivery_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    unitValue NUMERIC(10, 2) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    measure VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    general_manager_id VARCHAR(50) NULL,
    inactivated_dt DATE NULL;
    FOREIGN KEY (delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
);