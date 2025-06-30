CREATE TABLE logs_user_checklist (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY ,
    user_id UUID NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    action VARCHAR(50) NOT NULL,
    checklist_id UUID NOT NULL,
    vehicle_id UUID NOT NULL,
    action_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description TEXT NOT NULL
);