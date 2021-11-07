CREATE TABLE IF NOT EXISTS user_table
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    department_id BIGINT
);