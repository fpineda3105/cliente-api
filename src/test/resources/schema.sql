DROP TABLE IF EXISTS CLIENTE;

CREATE TABLE CLIENTE (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    edad INTEGER NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_posible_muerte DATE NOT NULL,
    PRIMARY KEY (id)
);