CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);