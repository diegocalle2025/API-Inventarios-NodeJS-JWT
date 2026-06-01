const Inventario = require('../models/Inventario');

// Obtener todos los inventarios (Accesible para Admin y RRHH)
const obtenerInventarios = async (req, res) => {
    try {
        // .populate() funciona como un JOIN en SQL
        const inventarios = await Inventario.find()
            .populate('usuarioCargo', 'email rol');
            
        res.json(inventarios);
    } catch (error) {
        console.error(error);
        res.status(500).json({ mensaje: 'Error interno al consultar inventarios' });
    }
};

// Crear un nuevo inventario (Exclusivo Administrador)
const crearInventario = async (req, res) => {
    try {
        const inventario = new Inventario(req.body);
        await inventario.save();
        res.status(201).json({ mensaje: 'Inventario creado exitosamente', inventario });
    } catch (error) {
        console.error(error);
        res.status(500).json({ mensaje: 'Error al crear el inventario', error: error.message });
    }
};

module.exports = {
    obtenerInventarios,
    crearInventario
};
