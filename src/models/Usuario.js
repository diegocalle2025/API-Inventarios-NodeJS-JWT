const mongoose = require('mongoose');

const usuarioSchema = new mongoose.Schema({
    nombre: {
        type: String,
        required: [true, 'El nombre es obligatorio'],
        trim: true
    },
    email: {
        type: String,
        required: [true, 'El email es obligatorio'],
        unique: true,
        trim: true,
        lowercase: true
    },
    password: {
        type: String,
        required: [true, 'La contraseña es obligatoria']
    },
    rol: {
        type: String,
        required: [true, 'El rol es obligatorio'],
        enum: ['Administrador', 'Recursos Humanos']
    }
}, {
    timestamps: true // Crea automáticamente createdAt y updatedAt
});

module.exports = mongoose.model('Usuario', usuarioSchema);