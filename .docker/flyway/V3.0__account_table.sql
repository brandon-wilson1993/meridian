CREATE TYPE account_type AS ENUM ('SAVINGS', 'CHECKING', 'CREDIT', 'TRADING');

CREATE TABLE account (
    id BIGINT DEFAULT nextval('idx_seq') PRIMARY KEY,
    user_id BIGINT REFERENCES users(user_id),
    account_type account_type NOT NULL
);

INSERT INTO account (user_id, account_type) VALUES
(1, 'SAVINGS'),
(2, 'CHECKING'),
(3, 'TRADING'),
(4, 'CREDIT'),;
(5, 'SAVINGS'),
(6, 'CHECKING'),
(7, 'TRADING'),
(8, 'CREDIT'),
(9, 'SAVINGS');