INSERT INTO users (user_id, username, email, password_hash, registration_date, balance_amount, last_updated) VALUES
(1, 'user1', 'email1', 'password1', '2023-09-22', 100.00, '2023-09-22'),
(2, 'user2', 'email2', 'password2', '2023-09-22', 200.00, '2023-09-22'),
(3, 'user3', 'email3', 'password3', '2023-08-22', 300.00, '2023-08-22');

INSERT INTO transactions (transaction_id, user_id, source, transaction_type, amount, transaction_date) VALUES
(1, 1, 'stealing', 'CREDIT', 100.00, '2023-09-22'),
(2, 1, 'bet', 'DEBIT', 50.00, '2023-09-22'),
(3, 2, 'stealing', 'CREDIT', 100.00, '2023-08-22');