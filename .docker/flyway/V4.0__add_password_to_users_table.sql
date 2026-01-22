-- Add password column to users table
-- Existing users will have a NULL password and must set a password through a secure flow
ALTER TABLE users ADD COLUMN password VARCHAR(255);

-- Remove the default constraint after adding the column so future inserts must provide a password
ALTER TABLE users ALTER COLUMN password DROP DEFAULT;
