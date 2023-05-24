# Script MySQL para la creación de la base de datos del instituto, con sus respectivas tablas
DROP DATABASE IF EXISTS instituto;
CREATE DATABASE IF NOT EXISTS instituto CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# Creación de la tabla daw1
USE instituto;
CREATE TABLE IF NOT EXISTS daw1 (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    alumno VARCHAR(35) NOT NULL,
    intervenciones TINYINT DEFAULT 0,
    ultima_intervencion DATE
);