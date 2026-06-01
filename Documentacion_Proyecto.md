# Documentación Técnica: API de Inventarios con Node.js y JWT

## Introducción
Este documento detalla paso a paso la construcción de una API RESTful para la gestión de inventarios, implementando autenticación y autorización con JSON Web Tokens (JWT). La arquitectura del proyecto está basada en el patrón Modelo-Controlador-Servicio, garantizando la separación de responsabilidades y aplicando las mejores prácticas de ingeniería de software.

---

## Paso 1: Configuración Inicial del Entorno

### 1.1 Limpieza y Estructura Base
**Propósito Técnico:** Iniciar con un entorno de trabajo limpio es fundamental. Se eliminan rastros de código de otros lenguajes o proyectos anteriores para evitar conflictos de dependencias y confusiones en la lógica. Al inicializar el proyecto con `npm init`, Node.js crea un archivo `package.json` que actuará como el "corazón" de nuestra configuración, rastreando todas las librerías de terceros que utilizaremos.

**Estructura de Carpetas:**
Implementamos una arquitectura limpia dividiendo el proyecto en capas:
- `src/config`: Alojará la configuración centralizada (como la conexión a la Base de Datos).
- `src/models`: Representa la Capa de Datos (Mapeo de la base de datos).
- `src/controllers`: Es la Capa de Presentación (Recibe la petición HTTP y retorna la respuesta).
- `src/routes`: Define las URLs de nuestra API.
- `src/middlewares`: Interceptores de seguridad (Validación de Tokens y Roles).

### 1.2 Dependencias Principales
Para este proyecto hemos instalado las siguientes librerías estratégicas:
- **express**: Framework rápido para construir la API y manejar las rutas HTTP.
- **mongoose**: Librería para modelar objetos en MongoDB, facilitando las consultas y validaciones de forma asíncrona.
- **cors**: Middleware que permite a nuestra API recibir peticiones desde otros dominios o aplicaciones Front-End.

### Análisis Técnico Profundo de Librerías de Seguridad
A petición de los requerimientos de seguridad y la arquitectura, detallamos el funcionamiento de tres pilares fundamentales de nuestra solución:

1. **bcryptjs (Seguridad de Contraseñas)**:
   * **¿Qué es?** Es una librería de encriptación basada en el potente algoritmo Blowfish.
   * **¿Por qué la usamos?** Por ley y buenas prácticas, **nunca** se debe guardar una contraseña en texto plano en la base de datos. Si la base de datos es comprometida, las cuentas estarían a salvo.
   * **¿Cómo funciona?** Aplica un proceso de *hashing* y *salting*. El *hash* transforma la clave ("1234") en una cadena ilegible y matemáticamente irreversible. El *salt* le añade caracteres aleatorios adicionales antes del hash, garantizando que si dos usuarios tienen la misma clave "1234", sus hashes finales en la base de datos sean completamente distintos, neutralizando ataques de diccionarios.

2. **jsonwebtoken o JWT (Autenticación sin Estado)**:
   * **¿Qué es?** Es un estándar abierto (RFC 7519) para transmitir información segura de forma encriptada entre el cliente y el servidor.
   * **¿Por qué lo usamos?** En las APIs modernas (REST), el servidor no guarda sesiones en memoria (es "stateless" o sin estado). JWT nos permite autenticar cada petición sin sobrecargar la base de datos buscando al usuario en cada clic.
   * **¿Cómo funciona?** Tras un Login exitoso, el servidor "firma" un token digital usando una Clave Secreta única y se lo entrega al usuario. Este token viaja con cada petición HTTP posterior (en el Header). El servidor solo debe hacer un cálculo matemático para comprobar que la firma del token no fue alterada, dándole acceso inmediato a las rutas protegidas (ej. visualizar inventarios).

3. **dotenv (Protección de Credenciales de Entorno)**:
   * **¿Qué es?** Un módulo que carga variables desde un archivo oculto local `.env` hacia la memoria del servidor (`process.env`).
   * **¿Por qué lo usamos?** Las URLs de conexión a la base de datos de producción y las Claves Secretas del JWT son información altamente confidencial. Si se suben a GitHub, el sistema quedaría expuesto a ataques globales.
   * **¿Cómo funciona?** Protege los secretos manteniéndolos estrictamente en el entorno de ejecución del servidor. El código fuente nunca revela las claves, solo invoca la variable abstracta, logrando máxima seguridad.

---

## Paso 2: Configuración de Base de Datos y Servidor

### 2.1 Archivo de Entorno (`.env`)
Se configuraron las variables de entorno críticas. La separación de estas configuraciones en un archivo `.env` es un estándar de seguridad de la industria que previene la filtración de credenciales sensibles, como la clave para firmar los tokens o la URL de la base de datos.

### 2.2 Conexión a Base de Datos (`src/config/db.js`)
Se implementó un módulo de conexión a MongoDB utilizando Mongoose y funciones asíncronas (`async/await`). Se emplea el bloque `try/catch` para un **manejo de excepciones riguroso**: si la base de datos falla al conectarse, la ejecución de la API se detiene (`process.exit(1)`), previniendo que la aplicación opere en un estado inestable.

### 2.3 Servidor Express (`server.js`)
El punto de entrada principal de la aplicación. Se configuraron dos middlewares globales imprescindibles:
- `express.json()`: Para que la API sea capaz de interpretar y parsear los cuerpos (body) de las peticiones entrantes en formato JSON.
- `cors()`: Habilita el intercambio de recursos de origen cruzado, esencial si el Front-End estará alojado en un dominio diferente al de la API.

---

## Paso 3: Módulo de Usuarios (Modelos y Rutas)

### 3.1 Modelo de Datos (`Usuario.js`)
Se diseñó el esquema de la colección de usuarios utilizando Mongoose. Este esquema define la estructura que tendrán los documentos en la base de datos y añade validaciones estrictas a nivel de aplicación (ej. `required`, `unique`, `enum`).
- **Nuevos campos integrados**: Se incluyeron `password` y `rol` ('Administrador' o 'Recursos Humanos') en cumplimiento estricto con los requerimientos del sistema.
- **Timestamps**: Se habilitó la creación automática de marcas de tiempo (`createdAt`, `updatedAt`) para efectos de auditoría.

### 3.2 Controlador (`usuarioController.js`)
Aquí reside la Lógica de Negocio para el registro de usuarios.
- **Validación de pre-existencia**: Antes de crear un usuario, el controlador verifica si el correo electrónico ya existe en la base de datos, evitando registros duplicados.
- **Encriptación (Hashing)**: Utilizando `bcryptjs`, interceptamos la contraseña en texto plano recibida del cliente, generamos un "salt" dinámico (factor de aleatoriedad) y creamos el hash irreversible. Esta es la versión que finalmente se persiste en la base de datos.
- **Respuesta Segura**: Al devolver la confirmación de la creación, el controlador extrae y omite el campo `password` de la respuesta JSON, garantizando que el hash nunca se exponga al cliente Front-End.

### 3.3 Rutas (`usuarioRoutes.js`)
Se estableció el endpoint `POST /api/usuarios` empleando el enrutador modular de Express (`express.Router()`). Esto permite mantener el archivo principal `server.js` limpio, importando y delegando todas las rutas de este módulo de manera estructurada.

---

## Paso 4: Módulo de Autenticación (Login y JWT)

### 4.1 Controlador de Autenticación (`authController.js`)
Se desarrolló el servicio que permite la autenticación por *email* y *contraseña*, cumpliendo rigurosamente con los requisitos:
1. **Validación de Identidad**: El sistema primero verifica si el correo suministrado existe en la base de datos.
2. **Comparación Segura**: Se hace uso del método `bcrypt.compare()` el cual toma la contraseña en texto plano, la vuelve a encriptar con el mismo factor y la compara internamente con la de la base de datos sin extraer el hash. Si falla, el sistema retorna un código HTTP 401 (Unauthorized) sin dar pistas de si falló el correo o la contraseña.
3. **Firma del JWT**: Si las credenciales son válidas, se genera un Token digital incluyendo en su "Payload" el **ID y el Rol** del usuario. Este token tiene una caducidad de 8 horas por razones de seguridad.

### 4.2 Endpoint de Autenticación (`authRoutes.js`)
Se habilitó la ruta `POST /api/auth/login`. Esta es la única ruta del sistema que retorna el `token`, el cual actuará como la "llave maestra" para el resto de la aplicación en el siguiente paso.

---

## Paso 5: Middlewares de Seguridad (Autorización)

### 5.1 Arquitectura de Interceptores (`authMiddleware.js`)
En Express, un middleware es una función que intercepta la petición HTTP antes de que llegue a su destino final. Se desarrollaron tres barreras de seguridad principales:

1. **`validarToken`**: 
   Actúa como el primer escudo de la API. Extrae el token del encabezado `Authorization` (formato Bearer). Utilizando la clave secreta, `jwt.verify` desencripta la firma. Si el token ha sido alterado o expiró (pasaron las 8 horas), la ejecución se bloquea inmediatamente devolviendo un estado HTTP 401. Si es exitoso, inyecta la identidad del usuario (`req.usuario`) para el resto de la aplicación.
2. **`esAdmin`**: 
   Una vez que sabemos *quién* es el usuario, este middleware evalúa sus privilegios. Aplica el Principio de Mínimo Privilegio, verificando si `req.usuario.rol` es estrictamente igual a "Administrador". Caso contrario, aborta la operación con un estado HTTP 403 (Forbidden).
3. **`esRRHH`**: 
   Opera de manera homóloga a `esAdmin`, pero garantiza que el acceso sea exclusivo para la división de "Recursos Humanos".

---

## Paso 6: Módulo de Inventarios y Relaciones (Aplicación de Reglas de Negocio)

### 6.1 Modelo de Inventario (`Inventario.js`)
Se estructuró la colección principal del sistema. En este esquema se evidencia el diseño del **Modelo Relacional en MongoDB**, utilizando `mongoose.Schema.Types.ObjectId` y la propiedad `ref`. Esto simula las "Llaves Foráneas" (Foreign Keys) de las bases de datos relacionales, conectando un equipo de cómputo específico con la colección de `Usuarios` (responsable), `Marcas`, `Estados` y `Tipos`.

### 6.2 Controlador de Inventarios (`inventarioController.js`)
Se encapsuló la lógica CRUD bajo un estricto control de excepciones (try/catch):
- **Lectura (`obtenerInventarios`)**: Utiliza el método `.populate()` de Mongoose. Esto es el equivalente a un `JOIN` en SQL, permitiendo traer los datos anidados del usuario responsable (email y rol) en lugar de solo traer un ID abstracto.
- **Creación (`crearInventario`)**: Recibe el payload del cliente y lo persiste en la base de datos de manera asíncrona.

### 6.3 Rutas Protegidas (`inventarioRoutes.js`)
Aquí se materializan estrictamente los requerimientos de seguridad del profesor utilizando los middlewares desarrollados en el Paso 5:
- **Visualización (Recursos Humanos y Admin)**: La ruta `GET /api/inventarios` fue inyectada con el middleware `validarToken`. Ambos roles pueden acceder si tienen un JWT vigente.
- **Creación (Exclusivo Administrador)**: La ruta `POST /api/inventarios` fue inyectada con un arreglo de middlewares: `[validarToken, esAdmin]`. Si un usuario de Recursos Humanos intenta enviar un POST a esta ruta, será detenido en la segunda barrera y recibirá un "Acceso Denegado 403", protegiendo absolutamente la integridad del sistema.

---

## Paso 7: Guía de Pruebas (Testing de la API)

Para probar la funcionalidad y la seguridad de la API, recomendamos usar **Postman** o **Thunder Client** (extensión de VS Code) siguiendo este flujo lógico:

### 1. Iniciar el Servidor
Asegúrese de que el motor de base de datos **MongoDB** esté instalado y en ejecución en su computadora. Luego, inicie la API ejecutando en la terminal:
```bash
node server.js
```
*Si la consola muestra "MongoDB Conectado" y "Servidor ejecutándose en el puerto 4000", el sistema está listo.*

### 2. Registrar Usuarios (POST `/api/usuarios`)
Cree dos usuarios distintos para probar la seguridad de roles.
- **Body (JSON)**: `{"email": "admin@empresa.com", "password": "1234", "rol": "Administrador"}`
- **Body (JSON)**: `{"email": "rrhh@empresa.com", "password": "1234", "rol": "Recursos Humanos"}`

### 3. Autenticación y Obtención del Token (POST `/api/auth/login`)
- Envíe el correo y contraseña del Administrador.
- **Resultado:** La API retornará un largo texto encriptado (`token`). Cópielo.

### 4. Prueba de Creación Segura (POST `/api/inventarios`)
- **Headers**: En Postman, vaya a la pestaña Headers y agregue la llave `Authorization` con el valor `Bearer <TU_TOKEN_ADMIN_AQUI>`.
- **Body (JSON)**: Envíe los datos del equipo (`serial`, `modelo`, `descripcion`, `color`, `fechaCompra`, `precio`, y `usuarioCargo` con el ID del admin).
- **Resultado Esperado**: Estado `201 Created` (Inventario guardado con éxito).

### 5. Prueba de Bloqueo de Seguridad (Autorización Fallida)
- Haga login de nuevo, pero ahora con el usuario de *Recursos Humanos* y copie su nuevo token.
- Repita el paso anterior de intentar crear un inventario, pegando este nuevo token en el Header.
- **Resultado Esperado**: Nuestro interceptor `esAdmin` detectará el rol inferior, bloqueará el ataque y devolverá: `"Acceso denegado. Se requieren privilegios de Administrador"` (Estado `403 Forbidden`).

---

## Conclusión: ¿Para qué sirve lo que construimos y cómo me ayuda en el futuro?

### ¿Qué construimos exactamente?
Construimos lo que la industria denomina un **Backend API con Autenticación Stateless y Control de Acceso Basado en Roles (RBAC)**. En términos sencillos: el "cerebro" y "sistema de seguridad" de una aplicación real. Cualquier aplicación moderna (web o móvil) que tenga usuarios, roles y datos protegidos, funciona exactamente con esta arquitectura por debajo.

### ¿Para qué sirve en el mundo real?
Este mismo patrón arquitectónico que acabamos de implementar es el que utilizan aplicaciones como:
- **Sistemas ERP empresariales** (SAP, Odoo): Donde un Gerente puede aprobrar facturas pero un Auxiliar Contable solo puede crearlas.
- **Plataformas de salud** (historiales clínicos): Donde un Médico puede escribir diagnósticos pero una Recepcionista solo puede agendar citas.
- **Aplicaciones bancarias**: Donde un Cajero puede consultar saldos pero solo un Supervisor puede autorizar transferencias grandes.

### ¿Cómo te ayuda en tu futuro como desarrollador?
Haber construido esta API desde cero te da ventajas competitivas concretas:

1. **Lenguaje Universal:** Node.js, Express, MongoDB y JWT son el stack tecnológico más demandado a nivel mundial en el mercado laboral de desarrollo web (Full Stack). Dominar la autenticación con JWT te convierte en un candidato mucho más atractivo para cualquier empresa de tecnología.

2. **Transferencia de Conocimiento:** La lógica que implementamos (Modelo → Controlador → Ruta → Middleware) es un patrón que encontrarás en *cualquier* lenguaje o framework: Django (Python), Laravel (PHP), Spring Boot (Java). Haberlo interiorizado aquí significa que podrás aprenderlos mucho más rápido.

3. **Seguridad como Hábito:** La mayoría de los desarrolladores junior cometen el error de pensar en la seguridad al final. Tú aprendiste a construirla desde el primer día: contraseñas hasheadas, tokens firmados, variables de entorno protegidas. Eso te diferencia y te hace más profesional desde etapas tempranas.

4. **Base para proyectos más grandes:** Esta API es completamente escalable. En el futuro, si necesitas agregar un módulo de Reportes, de Nómina o de Clientes, solo tendrías que agregar un nuevo Modelo, Controlador y Ruta siguiendo exactamente el mismo patrón que ya conoces. El sistema de seguridad (Middlewares) ya está listo para protegerlos automáticamente.

---

## Paso 7: Guía de Pruebas (Testing de la API)

Para probar la funcionalidad y la seguridad de la API, recomendamos usar **Postman** o **Thunder Client** (extensión de VS Code) siguiendo este flujo lógico:

### 1. Iniciar el Servidor
Asegúrese de que el motor de base de datos **MongoDB** esté instalado y en ejecución en su computadora. Luego, inicie la API ejecutando en la terminal:
```bash
node server.js
```
*Si la consola muestra "MongoDB Conectado" y "Servidor ejecutándose en el puerto 4000", el sistema está listo.*

### 2. Registrar Usuarios (POST `/api/usuarios`)
Cree dos usuarios distintos para probar la seguridad de roles.
- **Body (JSON)**: `{"email": "admin@empresa.com", "password": "1234", "rol": "Administrador"}`
- **Body (JSON)**: `{"email": "rrhh@empresa.com", "password": "1234", "rol": "Recursos Humanos"}`

### 3. Autenticación y Obtención del Token (POST `/api/auth/login`)
- Envíe el correo y contraseña del Administrador.
- **Resultado:** La API retornará un largo texto encriptado (`token`). Cópielo.

### 4. Prueba de Creación Segura (POST `/api/inventarios`)
- **Headers**: En Postman, vaya a la pestaña Headers y agregue la llave `Authorization` con el valor `Bearer <TU_TOKEN_ADMIN_AQUI>`.
- **Body (JSON)**: Envíe los datos del equipo (`serial`, `modelo`, `descripcion`, `color`, `fechaCompra`, `precio`, y `usuarioCargo` con el ID del admin).
- **Resultado Esperado**: Estado `201 Created` (Inventario guardado con éxito).

### 5. Prueba de Bloqueo de Seguridad (Autorización Fallida)
- Haga login de nuevo, pero ahora con el usuario de *Recursos Humanos* y copie su nuevo token.
- Repita el paso anterior de intentar crear un inventario, pegando este nuevo token en el Header.
- **Resultado Esperado**: Nuestro interceptor `esAdmin` detectará el rol inferior, bloqueará el ataque y devolverá: `"Acceso denegado. Se requieren privilegios de Administrador"` (Estado `403 Forbidden`).
