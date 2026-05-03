USE funcionarios_db;

-- Poblando la tabla de Cargos
INSERT INTO cargos (nombre_cargo, salario_base) VALUES 
('Desarrollador Junior', 2500.00),
('Desarrollador Semi-Senior', 4000.00),
('Desarrollador Senior', 6000.00),
('Arquitecto de Software', 8000.00),
('Gerente de Proyectos', 7500.00),
('Analista de QA', 3000.00);

-- Poblando la tabla de Dependencias
INSERT INTO dependencias (nombre_dependencia, ubicacion) VALUES 
('Tecnología', 'Piso 4 - Edificio Central'),
('Innovación y Desarrollo', 'Piso 5 - Edificio Central'),
('Calidad y Pruebas', 'Piso 3 - Torre B'),
('Operaciones IT', 'Piso 1 - Centro de Datos');

-- Insertando un funcionario de prueba (Opcional, pero util para ver la tabla con datos)
INSERT INTO funcionarios (nombre, apellido, documento, id_cargo, id_dependencia) VALUES 
('Juan', 'Pérez', '12345678', 3, 1),
('María', 'Gómez', '87654321', 1, 2);
