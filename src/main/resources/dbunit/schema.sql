CREATE TABLE IF NOT EXISTS user_table
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    department_id BIGINT
    );

CREATE TABLE IF NOT EXISTS department_table
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS group_table
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS users_groups_table
(
    user_id BIGINT,
    group_id BIGINT
    );