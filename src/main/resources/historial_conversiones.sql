CREATE TABLE historial_conversiones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    moneda_origen VARCHAR(3) NOT NULL,
    moneda_destino VARCHAR(3) NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    monto_convertido DECIMAL(15,2) NOT NULL,
    tasa DECIMAL(15,6) NOT NULL,
    fecha_consulta DATETIME NOT NULL
);