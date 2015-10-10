DROP TABLE `diagnosis`;
DROP TABLE `patients`;
DROP TABLE `users`;


CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `patients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);


CREATE TABLE `diagnosis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `technique` int(11) NOT NULL,
  `result` double NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `diagnosis_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE
);

INSERT INTO users 
(username, password, is_admin)
VALUES
('admin','admin',true),
('test','test',false)
;
