const Usuario = require('../models/Usuario');
const express = require('express');
const router = express.Router();
const { crearUsuario } = require('../controllers/usuarioController');

// POST /api/usuarios - Crear un nuevo usuario
router.post('/', crearUsuario);

router.get('/', async function (req, res) {
    try {
        const usuarios = await Usuario.find();
        res.send(usuarios);
    } catch (error) {
        console.log("--- ERROR REAL AQUÍ DEBAJO ---");
        console.log(error); // Esto imprimirá el problema real en tu terminal
        console.log("-------------------------------");
        res.status(500).send('Ocurrió un error al consultar los usuarios');
    }
});


module.exports = router;
