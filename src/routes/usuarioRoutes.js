const Usuario = require('../models/Usuario');
const express = require('express');
const router = express.Router();
const { crearUsuario } = require('../controllers/usuarioController');

// 🔐 IMPORTAMOS TUS MIDDLEWARES REALES DESDE AUTHMIDDLEWARE
const { validarToken, esAdmin } = require('../middlewares/authMiddleware');

// 🚀 POST /api/usuarios - Crear un nuevo usuario (SOLO EL ADMIN PUEDE CREAR)
router.post('/', validarToken, esAdmin, crearUsuario);

// 🔍 GET /api/usuarios - Consultar todos los usuarios (SOLO EL ADMIN PUEDE VER LA LISTA)
router.get('/', validarToken, esAdmin, async function (req, res) {
    try {
        const usuarios = await Usuario.find();
        res.send(usuarios);
    } catch (error) {
        console.log("--- ERROR REAL AQUÍ DEBAJO ---");
        console.log(error);
        console.log("-------------------------------");
        res.status(500).send('Ocurrió un error al consultar los usuarios');
    }
});

module.exports = router;
