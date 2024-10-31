CREATE TABLE district(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    district VARCHAR(100) NOT NULL,
    city_fk UUID,
    FOREIGN KEY (city_fk) REFERENCES city(id)
);