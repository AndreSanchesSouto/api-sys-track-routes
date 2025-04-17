CREATE TABLE client (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    document VARCHAR(100) NOT NULL,
    cellphone VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL,
    general_manager_id VARCHAR(50) NULL
);