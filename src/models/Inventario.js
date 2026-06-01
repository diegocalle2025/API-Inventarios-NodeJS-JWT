const mongoose = require('mongoose');

const inventarioSchema = new mongoose.Schema({
    serial: { type: String, required: true, unique: true },
    modelo: { type: String, required: true },
    descripcion: { type: String, required: true },
    foto: { type: String },
    color: { type: String, required: true },
    fechaCompra: { type: Date, required: true },
    precio: { type: Number, required: true },
    // Relaciones (Equivalente a Llaves Foráneas)
    usuarioCargo: { type: mongoose.Schema.Types.ObjectId, ref: 'Usuario', required: true },
    marca: { type: mongoose.Schema.Types.ObjectId, ref: 'Marca' },
    estadoEquipo: { type: mongoose.Schema.Types.ObjectId, ref: 'EstadoEquipo' },
    tipoEquipo: { type: mongoose.Schema.Types.ObjectId, ref: 'TipoEquipo' }
}, {
    timestamps: true
});

module.exports = mongoose.model('Inventario', inventarioSchema);
