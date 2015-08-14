DROP TABLE diagnosis;
DROP TABLE users;

CREATE TABLE users 
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	username VARCHAR(100) NOT NULL, 
	password VARCHAR(200) NOT NULL,
  is_admin boolean NOT NULL DEFAULT false,
	creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE diagnosis
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
  	user_id INT NOT NULL,
	name VARCHAR(200) UNIQUE NOT NULL, 
	description VARCHAR(2000), 
	technique INT NOT NULL, 
	result DOUBLE NOT NULL,
	creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) 
	REFERENCES users(id)
	ON DELETE CASCADE
);

INSERT INTO users
(username, password, is_admin)
VALUES
('test','test', false),
('admin', 'admin', true);
