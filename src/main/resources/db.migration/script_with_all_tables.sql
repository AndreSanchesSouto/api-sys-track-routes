CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE general_manager (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    creation_dt DATE NOT NULL,
    inactivation_dt DATE NULL
);

CREATE TABLE shipping_manager (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    creation_dt DATE NOT NULL,
    inactivation_dt DATE NULL
);

CREATE TABLE driver (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(100) NULL,
    creation_dt DATE NOT NULL,
    inactivation_dt DATE NULL
);

CREATE TABLE vehicle (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    license_plate_number VARCHAR(100) NOT NULL,
    side_number VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    driver_fk UUID,
    FOREIGN KEY (driver_fk) REFERENCES driver(id)
);

CREATE TABLE checklist (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    tire VARCHAR(100) NOT NULL,
    license_plate VARCHAR(100) NOT NULL,
    spare_tire VARCHAR(100) NOT NULL,
    kilometers_number VARCHAR(100) NOT NULL,
    fuel_level VARCHAR(100) NOT NULL,
    oil_level VARCHAR(100) NOT NULL,
    water_level VARCHAR(100) NOT NULL,
    suspension VARCHAR(100) NOT NULL,
    brakes VARCHAR(100) NOT NULL,
    lights VARCHAR(100) NOT NULL,
    glasses VARCHAR(100) NOT NULL,
    windshield_wipers VARCHAR(100) NOT NULL,
    jack VARCHAR(100) NOT NULL,
    tollBox VARCHAR(100) NOT NULL,
    documentation VARCHAR(100) NOT NULL,
    observation_notes VARCHAR(255) NULL,
    vehicle_fk UUID,
    FOREIGN KEY (vehicle_fk) REFERENCES vehicle(id)
);

CREATE TABLE client (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_type VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL
);

CREATE TABLE state (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    st CHAR(2) NOT NULL
);

CREATE TABLE city (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    state_fk UUID,
    FOREIGN KEY (state_fk) REFERENCES state(id)
);

CREATE TABLE district(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    district VARCHAR(100) NULL,
    city_fk UUID,
    FOREIGN KEY (city_fk) REFERENCES city(id)
);

CREATE TABLE address (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    address_type VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    number VARCHAR(100) NOT NULL,
    zip_code CHAR(8) NULL,
    client_fk UUID,
    state_fk UUID,
    FOREIGN KEY (client_fk) REFERENCES client(id),
    FOREIGN KEY (state_fk) REFERENCES state(id)
);