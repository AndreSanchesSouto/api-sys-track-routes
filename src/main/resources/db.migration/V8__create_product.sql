CREATE TABLE product(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    quantity INTEGER NOT NULL,
    value NUMERIC(10, 2) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    scale VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL;
)