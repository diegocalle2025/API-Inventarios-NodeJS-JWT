const express = require('express');
const dotenv = require('dotenv');
const cors = require('cors');
const connectDB = require('./src/config/db');

// Cargar variables de entorno
dotenv.config();

// Conectar a la base de datos
connectDB();

const app = express();

// Middlewares globales
app.use(express.json()); // Permite aceptar peticiones en JSON
app.use(cors()); // Permite peticiones desde otros dominios

// Rutas
app.use('/api/auth', require('./src/routes/authRoutes'));
app.use('/api/usuarios', require('./src/routes/usuarioRoutes'));
app.use('/api/inventarios', require('./src/routes/inventarioRoutes'));

// Ruta inicial de prueba
app.get('/', (req, res) => {
    res.send('API de Inventarios Funcional');
});

const PORT = process.env.PORT || 4000;

app.listen(PORT, () => {
    console.log(`Servidor ejecutándose en el puerto ${PORT}`);
});
