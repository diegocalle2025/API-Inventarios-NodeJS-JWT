const Usuario = require('../models/Usuario');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

// Autenticación de Usuarios (Login)
const loginUsuario = async (req, res) => {
    try {
        const { email, password } = req.body;

        // 1. Verificar si el usuario existe
        const usuario = await Usuario.findOne({ email });
        if (!usuario) {
            return res.status(401).json({ mensaje: 'Credenciales inválidas' });
        }

        // 2. Verificar la contraseña
        const passwordCorrecto = await bcrypt.compare(password, usuario.password);
        if (!passwordCorrecto) {
            return res.status(401).json({ mensaje: 'Credenciales inválidas' });
        }

        // 3. Generar el Token JWT
        // El "payload" contiene los datos que viajan dentro del token
        const payload = {
            usuario: {
                id: usuario._id,
                rol: usuario.rol
            }
        };

        // Firmar el token usando nuestra clave secreta del archivo .env
        jwt.sign(
            payload,
            process.env.JWT_SECRET,
            { expiresIn: '8h' }, // El token expira en 8 horas
            (error, token) => {
                if (error) throw error;
                res.json({
                    mensaje: 'Autenticación exitosa',
                    token
                });
            }
        );

    } catch (error) {
        console.error('Error en el login:', error);
        res.status(500).json({ mensaje: 'Error interno del servidor en la autenticación' });
    }
};

module.exports = {
    loginUsuario
};
