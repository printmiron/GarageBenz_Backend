DROP DATABASE IF EXISTS garageBenz;
CREATE DATABASE garageBenz;
USE garageBenz;

---
--- ESTRUCTURA DE TABLAS
---

CREATE TABLE Rol (
    id_rol CHAR(36) NOT NULL,
    nombre_rol ENUM('Cliente', 'Trabajador', 'Administrador') NOT NULL,
    PRIMARY KEY(id_rol)
);

CREATE TABLE Cliente (
    id_cliente CHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100) NOT NULL,
    apellido2 VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    id_rol CHAR(36) NOT NULL,
    PRIMARY KEY(id_cliente),
    CONSTRAINT fk_rolCliente FOREIGN KEY (id_rol) REFERENCES Rol(id_rol)
);

CREATE TABLE Trabajador (
    id_trabajador CHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100) NOT NULL,
    apellido2 VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    id_rol CHAR(36) NOT NULL,
    PRIMARY KEY(id_trabajador),
    CONSTRAINT fk_rolTrabajador FOREIGN KEY (id_rol) REFERENCES Rol(id_rol)
);

CREATE TABLE Administrador (
    id_admin CHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100) NOT NULL,
    apellido2 VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    usuario VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    id_rol CHAR(36) NOT NULL,
    PRIMARY KEY(id_admin),
    CONSTRAINT fk_rolAdministrador FOREIGN KEY (id_rol) REFERENCES Rol(id_rol)
);

CREATE TABLE Vehiculos (
    id_vehiculo CHAR(36) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    vin VARCHAR(17) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    anio INT NOT NULL, 
    id_cliente CHAR(36) NOT NULL,
    PRIMARY KEY(id_vehiculo),
    CONSTRAINT fk_clienteVehiculo FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Cita (
    id_cita CHAR(36) NOT NULL,
    fecha_cita DATE NOT NULL,
    hora_cita TIME NOT NULL,
    descripcion TEXT,
    estado ENUM('Pendiente', 'Confirmada', 'Completada', 'Cancelada') NOT NULL DEFAULT 'Pendiente',
    id_cliente CHAR(36) NOT NULL,
    id_vehiculo CHAR(36) NOT NULL,
    PRIMARY KEY(id_cita),
    CONSTRAINT fk_clienteCita FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    CONSTRAINT fk_vehiculoCita FOREIGN KEY (id_vehiculo) REFERENCES Vehiculos(id_vehiculo)
);

CREATE TABLE Ordenes_Reparacion (
    id_or CHAR(36) NOT NULL,
    id_cita CHAR(36) NOT NULL,
    id_vehiculo CHAR(36) NOT NULL,
    id_trabajador CHAR(36) NOT NULL,
    diagnostico TEXT,
    horas DECIMAL(5,2),
    fecha_inicio DATE,
    fecha_fin DATE,
    estado_rep ENUM('En proceso', 'Completada', 'Pausada', 'Cancelada') NOT NULL DEFAULT 'En proceso',
    PRIMARY KEY(id_or),
    CONSTRAINT fk_citaOrden FOREIGN KEY (id_cita) REFERENCES Cita(id_cita),
    CONSTRAINT fk_vehiculoOrden FOREIGN KEY (id_vehiculo) REFERENCES Vehiculos(id_vehiculo),
    CONSTRAINT fk_trabajadorOrden FOREIGN KEY (id_trabajador) REFERENCES Trabajador(id_trabajador)
);

CREATE TABLE Piezas (
    id_pieza CHAR(36) NOT NULL,
    Nombre VARCHAR(150) NOT NULL,
    Descripcion TEXT,  
    PRIMARY KEY(id_pieza)
);

CREATE TABLE Stock (
    id_stock CHAR(36) NOT NULL,
    id_pieza CHAR(36) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    PRIMARY KEY(id_stock),
    CONSTRAINT fk_piezaStock FOREIGN KEY (id_pieza) REFERENCES Piezas(id_pieza)
);

CREATE TABLE Ordenes_Pieza (
    id_or CHAR(36) NOT NULL,
    id_pieza CHAR(36) NOT NULL,
    cantidad_usada INT NOT NULL,
    PRIMARY KEY(id_or, id_pieza),
    CONSTRAINT fk_ordenPieza FOREIGN KEY (id_or) REFERENCES Ordenes_Reparacion(id_or),
    CONSTRAINT fk_piezaOrden FOREIGN KEY (id_pieza) REFERENCES Piezas(id_pieza)
);




SET FOREIGN_KEY_CHECKS = 0; -- Desactiva temporalmente las restricciones de llave foránea

TRUNCATE TABLE Ordenes_Pieza;
TRUNCATE TABLE Stock;
TRUNCATE TABLE Piezas;
TRUNCATE TABLE Ordenes_Reparacion;
TRUNCATE TABLE Cita;
TRUNCATE TABLE Vehiculos;
TRUNCATE TABLE Administrador;
TRUNCATE TABLE Trabajador;
TRUNCATE TABLE Cliente;
TRUNCATE TABLE Rol;

SET FOREIGN_KEY_CHECKS = 1; -- Vuelve a activar las restricciones

INSERT INTO Rol (id_rol, nombre_rol) VALUES 
('e3b0c442-98fc-11ee-b9d1-0242ac120002', 'Cliente'),
('e3b0c442-98fc-11ee-b9d1-0242ac120003', 'Trabajador'),
('e3b0c442-98fc-11ee-b9d1-0242ac120004', 'Administrador');



