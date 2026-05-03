-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS funcionarios_db;
USE funcionarios_db;

-- Eliminar tablas si existen (orden inverso para no violar llaves foráneas)
DROP TABLE IF EXISTS funcionarios;
DROP TABLE IF EXISTS dependencias;
DROP TABLE IF EXISTS cargos;

-- Tabla de Cargos
CREATE TABLE cargos (
    id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cargo VARCHAR(100) NOT NULL,
    salario_base DECIMAL(10, 2) NOT NULL
);

-- Tabla de Dependencias
CREATE TABLE dependencias (
    id_dependencia INT AUTO_INCREMENT PRIMARY KEY,
    nombre_dependencia VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(150) NOT NULL
);

-- Tabla de Funcionarios (CRUD Principal)
CREATE TABLE funcionarios (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    id_cargo INT NOT NULL,
    id_dependencia INT NOT NULL,
    FOREIGN KEY (id_cargo) REFERENCES cargos(id_cargo) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_dependencia) REFERENCES dependencias(id_dependencia) ON DELETE RESTRICT ON UPDATE CASCADE
);
