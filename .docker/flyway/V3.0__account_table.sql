CREATE TYPE account_type AS ENUM ('SAVINGS', 'CHECKING', 'TRADING');

CREATE TABLE account (
    account_id BIGINT DEFAULT nextval('idx_seq') PRIMARY KEY,
    user_id BIGINT REFERENCES users(user_id),
    account_type account_type NOT NULL
);

INSERT INTO account (user_id, account_type) VALUES
(1, 'SAVINGS'),
(2, 'CHECKING'),
(3, 'TRADING');