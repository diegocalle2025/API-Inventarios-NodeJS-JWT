const express = require('express');
const router = express.Router();
const { crearUsuario } = require('../controllers/usuarioController');

// POST /api/usuarios - Crear un nuevo usuario
router.post('/', crearUsuario);

module.exports = router;
