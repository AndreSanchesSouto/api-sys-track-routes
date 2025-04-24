CREATE TABLE client (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    document VARCHAR(100) NOT NULL UNIQUE,
    cellphone VARCHAR(100) NOT NULL,
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL
);