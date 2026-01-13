ALTER TYPE account_type ADD VALUE 'DEBIT';

CREATE TYPE card_type AS ENUM ('CREDIT', 'DEBIT');

CREATE TABLE cards (
    id BIGINT DEFAULT nextval('idx_seq') PRIMARY KEY,
    account_id BIGINT REFERENCES account(account_id),
    user_id BIGINT REFERENCES users(user_id),
    card_number_hash VARCHAR(64) NOT NULL,
    card_type card_type NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
