CREATE TABLE platform (
    id BIGINT PRIMARY KEY,
    platform_name VARCHAR NOT NULL,
    manufacturer VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);