CREATE TABLE token(
    id SERIAL PRIMARY KEY,
    access_token VARCHAR(1024),
    refresh_token VARCHAR(1024),
    is_logged_out BOOLEAN NOT NULL DEFAULT FALSE,
    user_id UUID NOT NULL,
    CONSTRAINT fk_user_token FOREIGN KEY (user_id) REFERENCES users(id)
)