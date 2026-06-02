const Usuario = require('../models/Usuario');
const bcrypt = require('bcryptjs');

// Crear un nuevo usuario
const crearUsuario = async (req, res) => {
    try {
        // 1. Recibimos también el nombre desde el cuerpo de la petición
        const { nombre, email, password, rol } = req.body;

        // Validar si el usuario ya existe
        const usuarioExistente = await Usuario.findOne({ email });
        if (usuarioExistente) {
            return res.status(400).json({ mensaje: 'El usuario ya está registrado' });
        }

        // 2. Pasamos el nombre a la instancia del modelo
        const usuario = new Usuario({
            nombre,
            email,
            password,
            rol
        });

        // Encriptar la contraseña (Hashing)
        const salt = await bcrypt.genSalt(10);
        usuario.password = await bcrypt.hash(password, salt);

        // Guardar en la base de datos
        await usuario.save();

        // 3. Incluimos el nombre en la respuesta por estética y seguridad
        res.status(201).json({
            mensaje: 'Usuario creado exitosamente',
            usuario: {
                id: usuario._id,
                nombre: usuario.nombre,
                email: usuario.email,
                rol: usuario.rol
            }
        });

    } catch (error) {
        console.error('Error al crear usuario:', error);
        res.status(500).json({ mensaje: 'Error interno del servidor al crear usuario', error: error.message });
    }
};

module.exports = {
    crearUsuario
};