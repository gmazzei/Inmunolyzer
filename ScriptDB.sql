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
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `diagnosis_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE
);

INSERT INTO users 
(username, password, is_admin)
VALUES
('admin','proyecto',true),
('test','test',false)
;


INSERT INTO patients
(id, user_id, name, description, creation_date)
VALUES
(3, 2, 'Gabriel Medina', 'Tercer paciente del sistema INMUNOLyzer', '2015-11-08 21:28:43.0')
;

INSERT INTO diagnosis
(patient_id, name, description, technique, result, creation_date)
VALUES
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 12.0,   '2014-12-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 37.0,   '2014-12-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 21.0,   '2014-12-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 89.0,   '2015-01-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 75.0,   '2015-02-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 9.0,    '2015-02-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 55.0,   '2015-03-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 67.0,   '2015-03-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 36.0,   '2015-03-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 82.0,   '2015-06-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 4.0,    '2015-08-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 64.0,   '2015-09-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 19.0,   '2015-10-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 72.0,   '2015-10-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 64.0,   '2015-10-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 83.0,   '2015-11-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 35.0,   '2015-11-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 7.0,    '2015-11-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 5.0,    '2015-05-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 89.0,   '2015-04-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 92.0,   '2015-02-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 54.0,   '2015-03-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 13.0,   '2015-04-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 11.0,   '2015-08-08 21:30:06.0'),
(3, 'Ejemplo',   'Diagnóstico de prueba',  0, 7.0,    '2015-11-08 21:30:06.0')
;