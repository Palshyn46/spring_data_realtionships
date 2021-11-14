CREATE TABLE IF NOT EXISTS create_user_information
(
    id BIGSERIAL PRIMARY KEY,
    enter_time varchar(255),
    ip varchar(255),
    body varchar(255),
    exit_time varchar(255),
    response varchar(255)
    );