const Usuario = require('../models/Usuario');
const bcrypt = require('bcryptjs');

// Crear un nuevo usuario
const crearUsuario = async (req, res) => {
    try {
        const { email, password, rol } = req.body;

        // Validar si el usuario ya existe
        const usuarioExistente = await Usuario.findOne({ email });
        if (usuarioExistente) {
            return res.status(400).json({ mensaje: 'El usuario ya está registrado' });
        }

        // Crear la instancia del usuario
        const usuario = new Usuario({
            email,
            password,
            rol
        });

        // Encriptar la contraseña (Hashing)
        const salt = await bcrypt.genSalt(10);
        usuario.password = await bcrypt.hash(password, salt);

        // Guardar en la base de datos
        await usuario.save();

        // Responder omitiendo la contraseña por seguridad
        res.status(201).json({
            mensaje: 'Usuario creado exitosamente',
            usuario: {
                id: usuario._id,
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
