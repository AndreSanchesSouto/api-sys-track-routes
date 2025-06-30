CREATE TABLE logs_user_delivery_product (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    product_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    measure DOUBLE NOT NULL,
    unit VARCHAR(50) NOT NULL,
    log_id UUID NOT NULL REFERENCES logs_user_delivery(id) ON DELETE CASCADE
);