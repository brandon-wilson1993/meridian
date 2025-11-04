CREATE SEQUENCE idx_seq START 1;

CREATE TABLE author (
    id BIGINT DEFAULT nextval('idx_seq') PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);