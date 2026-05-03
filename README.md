# Sistema CRUD de Funcionarios (Java)

Aplicación de escritorio desarrollada en **Java Swing** para la gestión de funcionarios de una entidad, implementando una arquitectura limpia y profesional.

## 🚀 Tecnologías y Arquitectura

*   **Lenguaje:** Java 17+
*   **Interfaz Gráfica:** Java Swing
*   **Base de Datos:** MySQL
*   **Patrones de Diseño:** 
    *   **MVC (Modelo-Vista-Controlador):** Para separar la lógica de negocio de la interfaz de usuario.
    *   **DAO (Data Access Object):** Para independizar la lógica de acceso a datos.
    *   **Singleton:** Para la gestión eficiente de la conexión a la base de datos.
*   **Gestión de Errores:** Jerarquía de excepciones personalizadas (`AppException`, `DatabaseException`, `ValidationException`).

## ⚙️ Características Principales

*   ✅ **Operaciones CRUD:** Crear, Leer, Actualizar y Eliminar funcionarios.
*   ✅ **Integridad Referencial:** Relaciones estructuradas con tablas de `Cargos` y `Dependencias`.
*   ✅ **Seguridad SQL:** Uso exclusivo de `PreparedStatement` para prevenir inyección SQL.
*   ✅ **Listas Dinámicas:** Los desplegables (ComboBox) de cargos y dependencias se cargan directamente desde la base de datos.

## 🛠️ Instalación y Configuración

1. **Base de Datos:**
   * Abre tu gestor de MySQL (Workbench, phpMyAdmin, etc.).
   * Ejecuta el archivo `schema.sql` para crear la base de datos `funcionarios_db` y sus tablas.
   * Ejecuta el archivo `data.sql` para poblar la base de datos con los cargos y dependencias iniciales.
2. **Configuración de Conexión:**
   * En el archivo `src/config.properties`, verifica que las credenciales coincidan con las de tu entorno local de MySQL:
     ```properties
     db.url=jdbc:mysql://localhost:3306/funcionarios_db
     db.user=root
     db.password=1234
     ```
3. **Ejecución:**
   * Abre el proyecto en tu IDE favorito (VS Code, Eclipse, IntelliJ).
   * Ejecuta la clase principal ubicada en `src/Main.java`.
