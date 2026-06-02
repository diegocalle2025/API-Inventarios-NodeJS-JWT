require('dotenv').config();
const mongoose = require('mongoose');
const Usuario = require('./src/models/Usuario'); // ⚠️ Verifica que la ruta a tu modelo sea la correcta
const bcrypt = require('bcryptjs');

async function sembrarAdmin() {
    try {
        // 1. Conectar a la base de datos usando tu URI de las variables de entorno
        // (Revisa si en tu .env se llama MONGO_URI, DB_CONNECTION o DATABASE_URL)
        const mongoUri = process.env.MONGO_URI || process.env.DB_CONNECTION;

        await mongoose.connect(mongoUri);
        console.log("🌱 Conectado a la base de datos para crear el Admin...");

        // 2. Encriptar la contraseña del administrador inicial
        const salt = await bcrypt.genSalt(10);
        const passwordEncriptado = await bcrypt.hash('TuContraseñaSecreta123', salt);

        // 3. Definir los datos del Administrador Semilla (Ahora con NOMBRE)
        const nuevoAdmin = new Usuario({
            nombre: "Admin Inicial",
            email: "admin_inicial@empresa.com", // El correo que usarás para tu primer login en Postman
            password: passwordEncriptado,
            rol: "Administrador"
        });

        // 4. Guardarlo en la base de datos
        await nuevoAdmin.save();
        console.log("✅ ¡Administrador inicial creado con éxito desde cero!");

    } catch (error) {
        console.error("❌ Error al crear el administrador semilla:", error.message);
    } finally {
        // 5. Desconectar de la base de datos de forma limpia
        await mongoose.disconnect();
        console.log("🔌 Conexión cerrada.");
    }
}

sembrarAdmin();