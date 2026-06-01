# 📦 API RESTful de Inventarios con Node.js, Express, MongoDB y JWT

API backend profesional para la gestión de inventarios de equipos tecnológicos, con autenticación segura y control de acceso basado en roles (RBAC).

---

## 🚀 Tecnologías Utilizadas

| Tecnología | Propósito |
|---|---|
| **Node.js** | Entorno de ejecución del servidor |
| **Express.js** | Framework para construir la API REST |
| **MongoDB Atlas** | Base de datos NoSQL en la nube |
| **Mongoose** | Modelado de datos y validaciones |
| **bcryptjs** | Encriptación de contraseñas (algoritmo Blowfish) |
| **jsonwebtoken** | Autenticación sin estado mediante tokens JWT |
| **dotenv** | Protección de credenciales y variables de entorno |
| **cors** | Habilitación de peticiones desde dominios externos |

---

## 🏗️ Arquitectura del Proyecto

```
/src
  ├── config/
  │   └── db.js                   → Conexión a MongoDB con manejo de excepciones
  ├── controllers/
  │   ├── authController.js       → Lógica de Login y generación de JWT
  │   ├── inventarioController.js → Lógica CRUD de inventarios
  │   └── usuarioController.js    → Registro y encriptación de contraseñas
  ├── middlewares/
  │   └── authMiddleware.js       → Interceptores: validarToken, esAdmin, esRRHH
  ├── models/
  │   ├── Inventario.js           → Esquema con referencias (llaves foráneas)
  │   └── Usuario.js              → Esquema con roles y validaciones
  └── routes/
      ├── authRoutes.js           → POST /api/auth/login
      ├── inventarioRoutes.js     → GET y POST /api/inventarios (protegidas)
      └── usuarioRoutes.js        → POST /api/usuarios
.env                              → Variables de entorno (NO subir a GitHub)
server.js                         → Punto de entrada de la aplicación
```

---

## 🔐 Endpoints y Control de Acceso

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/api/usuarios` | Público | Crear un usuario (contraseña encriptada automáticamente) |
| `POST` | `/api/auth/login` | Público | Autenticar usuario y obtener Token JWT |
| `GET` | `/api/inventarios` | Admin + RRHH | Listar todos los inventarios |
| `POST` | `/api/inventarios` | **Solo Admin** | Crear un nuevo inventario |

---

## ⚙️ Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DE_LA_CARPETA>
```

### 2. Instalar dependencias
```bash
npm install
```

### 3. Configurar variables de entorno
Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:
```env
PORT=4000
MONGO_URI=mongodb+srv://<usuario>:<contraseña>@<cluster>.mongodb.net/inventarios_db
JWT_SECRET=TuClaveSecretaMuySegura
```

### 4. Iniciar el servidor
```bash
node server.js
```
Si todo está bien configurado, verás en la consola:
```
Servidor ejecutándose en el puerto 4000
MongoDB Conectado: <host>
```

---

## 🧪 Guía de Pruebas con Postman

### Paso 1: Crear un usuario Administrador
- **Método:** `POST`
- **URL:** `http://localhost:4000/api/usuarios`
- **Body (raw JSON):**
```json
{
  "email": "admin@empresa.com",
  "password": "1234",
  "rol": "Administrador"
}
```

### Paso 2: Hacer Login y obtener el Token
- **Método:** `POST`
- **URL:** `http://localhost:4000/api/auth/login`
- **Body (raw JSON):**
```json
{
  "email": "admin@empresa.com",
  "password": "1234"
}
```
**Copia el valor del campo `token` de la respuesta.**

### Paso 3: Crear un Inventario (con autorización)
- **Método:** `POST`
- **URL:** `http://localhost:4000/api/inventarios`
- **Headers:** `Authorization: Bearer <TU_TOKEN_AQUI>`
- **Body (raw JSON):**
```json
{
  "serial": "12345XYZ",
  "modelo": "ThinkPad T14",
  "descripcion": "Laptop para desarrollo",
  "color": "Negro",
  "fechaCompra": "2024-01-15",
  "precio": 1200,
  "usuarioCargo": "<ID_DEL_USUARIO>"
}
```

---

## 👨‍💻 Autor

Desarrollado como proyecto académico aplicando patrones de arquitectura limpia, seguridad con JWT y buenas prácticas de ingeniería de software.
