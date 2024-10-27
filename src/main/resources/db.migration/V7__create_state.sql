CREATE TABLE state (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    st CHAR(2) NOT NULL
);