CREATE TABLE deliveries (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    status VARCHAR(50),
    created_dt DATE NOT NULL,
    inactivated_dt DATE NULL;
    client_id UUID,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
)