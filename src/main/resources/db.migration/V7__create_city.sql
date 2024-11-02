CREATE TABLE city (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    st CHAR(2) NOT NULL,
    district VARCHAR(100) NULL,
);