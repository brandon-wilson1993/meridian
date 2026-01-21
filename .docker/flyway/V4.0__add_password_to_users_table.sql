-- Add password column to users table
-- Using a BCrypt hashed version of "Password123!" as default for existing users
ALTER TABLE users ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy';

-- Remove the default constraint after adding the column so future inserts must provide a password
ALTER TABLE users ALTER COLUMN password DROP DEFAULT;
