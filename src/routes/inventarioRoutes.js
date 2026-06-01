const express = require('express');
const router = express.Router();
const { obtenerInventarios, crearInventario } = require('../controllers/inventarioController');
const { validarToken, esAdmin } = require('../middlewares/authMiddleware');

// GET /api/inventarios - Accesible para Admin y RRHH
// Solo requiere validarToken porque ambos roles pueden visualizar
router.get('/', validarToken, obtenerInventarios);

// POST /api/inventarios - Exclusivo para Administradores
// Requiere primero validar el Token, y luego pasar por el filtro esAdmin
router.post('/', [validarToken, esAdmin], crearInventario);

module.exports = router;
