-- Update the user if it exists, otherwise create it
CREATE USER IF NOT EXISTS 'spring_user'@'127.0.0.1' IDENTIFIED BY 'password';
ALTER USER 'spring_user'@'127.0.0.1' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `tp-spring`.* TO 'spring_user'@'127.0.0.1';
FLUSH PRIVILEGES;