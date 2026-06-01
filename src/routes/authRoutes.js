const express = require('express');
const router = express.Router();
const { loginUsuario } = require('../controllers/authController');

// POST /api/auth/login - Autenticar usuario y obtener JWT
router.post('/login', loginUsuario);

module.exports = router;
