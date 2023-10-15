-- https://spring.io/guides/gs/accessing-data-mysql/
-- https://stackoverflow.com/questions/6720050/foreign-key-constraints-when-to-use-on-update-and-on-delete

-- src/main/resources/application.properties
-- spring.jpa.hibernate.ddl-auto=update
-- spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/solosavings
-- spring.datasource.username=team2
-- spring.datasource.password=cs673
-- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
-- #spring.jpa.show-sql: true

-- CREATE DATABASE AND USER
CREATE DATABASE IF NOT EXISTS solosavings;
CREATE USER 'team2'@'localhost' IDENTIFIED BY 'cs673';
GRANT ALL PRIVILEGES ON * . * TO 'team2'@'localhost';
FLUSH PRIVILEGES;

-- CREATE TABLES

CREATE TABLE `comments` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `content` varchar(255) DEFAULT NULL,
                            `user_id` int(11) DEFAULT NULL,
                            `transaction_id` int(11) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS users (
 user_id INT NOT NULL AUTO_INCREMENT,
 username VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL,
 password_hash VARCHAR(255) NOT NULL,
 registration_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 balance_amount DECIMAL(18,2) NOT NULL DEFAULT 0.00,
 last_updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (user_id)
 ) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS transactions (
transaction_id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
source VARCHAR(255) NOT NULL,
transaction_type ENUM('CREDIT', 'DEBIT') NOT NULL,
amount DECIMAL(18,2) NOT NULL,
transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(transaction_id),
FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS budgetgoals (
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
budget_goal_type ENUM('SAVE','SPEND') NOT NULL,
source VARCHAR(255) NOT NULL,
target_amount DECIMAL(18,2) NOT NULL,
start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=INNODB;

CREATE OR REPLACE VIEW budgetgoaltracker AS
SELECT b.id, b.budget_goal_type, b.source, b.target_amount, SUM(t.amount) actual_amount, b.user_id
FROM budgetgoals b, transactions t
WHERE MONTH(b.start_date) = MONTH(t.transaction_date)
AND b.user_id = t.user_id
AND b.source = t.source
AND b.budget_goal_type = IF(t.transaction_type = 'CREDIT','SAVE','SPEND')
GROUP BY b.id, b.budget_goal_type, b.source, b.target_amount, b.user_id;

-- SELECT STATEMENTS
-- SELECT * FROM `users`
-- SELECT `user_id`, `username`, `email`, `password_hash`, `registration_date`, `balance_amount`, `last_updated` FROM `users`

-- SELECT * FROM `income`
-- SELECT `income_id`, `user_id`, `source`, `amount`, `income_date` FROM `income`

-- SELECT * FROM `expenses` 
-- SELECT `expense_id`, `user_id`, `category`, `amount`, `expense_date` FROM `expenses`

-- INSERT STATEMENTS
-- INSERT INTO `users`(`user_id`, `username`, `email`, `password_hash`, `registration_date`, `balance_amount`, `last_updated`) VALUES ('[value-1]','[value-2]','[value-3]','[value-4]','[value-5]','[value-6]','[value-7]')
-- INSERT INTO `income`(`income_id`, `user_id`, `source`, `amount`, `income_date`) VALUES ('[value-1]','[value-2]','[value-3]','[value-4]','[value-5]')
-- INSERT INTO `expenses`(`expense_id`, `user_id`, `category`, `amount`, `expense_date`) VALUES ('[value-1]','[value-2]','[value-3]','[value-4]','[value-5]')