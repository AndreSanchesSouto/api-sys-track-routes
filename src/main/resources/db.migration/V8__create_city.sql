CREATE TABLE city (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    state_fk UUID,
    FOREIGN KEY (state_fk) REFERENCES state(id)
);