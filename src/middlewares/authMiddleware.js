const jwt = require('jsonwebtoken');

// 1. Interceptor para validar el Token
const validarToken = (req, res, next) => {
    // El token debe venir en el encabezado de autorización
    const token = req.header('Authorization');

    // Si no hay token, denegar acceso inmediatamente
    if (!token) {
        return res.status(401).json({ mensaje: 'Acceso denegado. No hay token de autenticación.' });
    }

    try {
        // Extraemos solo el token (Ignorando la palabra "Bearer ")
        const tokenExtraido = token.startsWith('Bearer ') ? token.slice(7, token.length) : token;

        // Verificar la firma del token usando la Clave Secreta
        const decodificado = jwt.verify(tokenExtraido, process.env.JWT_SECRET);
        
        // Inyectar los datos del usuario en la petición para que las rutas lo sepan
        req.usuario = decodificado.usuario;
        
        // Permitir que el flujo continúe hacia la ruta
        next();
    } catch (error) {
        res.status(401).json({ mensaje: 'Token inválido o expirado.' });
    }
};

// 2. Interceptor de Seguridad para Administradores
const esAdmin = (req, res, next) => {
    // Asegurarnos de que validarToken ya hizo su trabajo
    if (!req.usuario) {
        return res.status(500).json({ mensaje: 'Se intentó verificar el rol sin validar el token primero.' });
    }

    // Verificar si el rol coincide
    if (req.usuario.rol !== 'Administrador') {
        return res.status(403).json({ mensaje: 'Acceso denegado. Se requieren privilegios de Administrador.' });
    }

    // Dejar pasar si es Administrador
    next();
};

// 3. Interceptor de Seguridad para Recursos Humanos
const esRRHH = (req, res, next) => {
    if (!req.usuario) {
        return res.status(500).json({ mensaje: 'Se intentó verificar el rol sin validar el token primero.' });
    }

    if (req.usuario.rol !== 'Recursos Humanos') {
        return res.status(403).json({ mensaje: 'Acceso denegado. Exclusivo para Recursos Humanos.' });
    }

    next();
};

module.exports = {
    validarToken,
    esAdmin,
    esRRHH
};
