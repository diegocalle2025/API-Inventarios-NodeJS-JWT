# Guion para Video Explicativo del Proyecto (CRUD Funcionarios)
**Tiempo estimado:** 4-5 minutos
**Estructura sugerida para el video:**

---

### 1. Introducción (0:00 - 0:45)
**[Acción en pantalla: Mostrar la portada del proyecto o el menú principal de la aplicación corriendo]**

*Guion sugerido:*
"Hola, mi nombre es [Tu Nombre] y en este video voy a presentar la arquitectura técnica y el funcionamiento del proyecto de gestión de funcionarios, desarrollado en Java de escritorio. 
El reto principal era modernizar el registro y control de empleados de una entidad. Para lograrlo y asegurar que el código sea escalable, profesional y mantenible, implementé una arquitectura basada en el patrón de diseño MVC (Modelo-Vista-Controlador) combinado con el patrón DAO (Data Access Object) y un manejo robusto de excepciones personalizadas."

### 2. Base de Datos y Modelo Relacional (0:45 - 1:30)
**[Acción en pantalla: Mostrar el script SQL o el diagrama de la base de datos MySQL]**

*Guion sugerido:*
"Primero, diseñé la base de datos relacional en MySQL. No solo tenemos la tabla 'funcionarios', sino que, para cumplir con buenas prácticas de normalización, integré tablas de 'cargos' y 'dependencias'. 
En el modelo relacional, la tabla principal de funcionarios está conectada mediante claves foráneas. Esto garantiza la integridad referencial, asegurando que a un funcionario no se le asigne un cargo o dependencia que no exista. Todo esto fue documentado y construido mediante scripts DDL (creación) y DML (inserción de datos)."

### 3. Arquitectura del Código: El Patrón DAO (1:30 - 2:30)
**[Acción en pantalla: Mostrar el código de la interfaz FuncionarioDAO y FuncionarioDAOImpl]**

*Guion sugerido:*
"En el lado del backend, utilicé el patrón DAO. Este patrón es fundamental porque separa por completo la lógica de acceso a datos de la lógica de la interfaz de usuario.
Como pueden ver aquí, creé interfaces que definen las operaciones CRUD (Crear, Leer, Actualizar y Eliminar), y sus respectivas implementaciones que ejecutan consultas SQL mediante `PreparedStatement`. Esto previene ataques de inyección SQL. 
Además, externalicé las credenciales de la base de datos usando un archivo de propiedades y apliqué el patrón Singleton para gestionar la conexión, evitando sobrecargar la base de datos con conexiones innecesarias."

### 4. Manejo de Excepciones y Controlador (2:30 - 3:30)
**[Acción en pantalla: Mostrar el paquete 'exception' y luego un bloque Try-Catch en el Controlador]**

*Guion sugerido:*
"Un aspecto clave de este desarrollo es el manejo de excepciones. En lugar de usar excepciones genéricas de Java, implementé una jerarquía de excepciones personalizadas como `DatabaseException` y `ValidationException`.
El Controlador actúa como el cerebro de la aplicación. Recibe los datos de la Vista, realiza validaciones (como verificar que no haya campos vacíos), y si todo es correcto, invoca al DAO. Si ocurre un error, por ejemplo, que la base de datos esté caída, el sistema lanza la excepción personalizada y muestra un mensaje amigable al usuario en lugar de simplemente fallar."

### 5. Demostración de la Vista y Funcionamiento (3:30 - 4:45)
**[Acción en pantalla: Usar la aplicación: crear un funcionario, editarlo, listarlo y eliminarlo]**

*Guion sugerido:*
"Finalmente, en la capa de presentación utilicé Java Swing. Refactoricé el diseño para evitar el posicionamiento absoluto, usando Layouts que hacen que la interfaz sea escalable y profesional.
Vamos a ver una demostración en vivo. 
* Aquí puedo registrar un nuevo funcionario seleccionando su cargo y dependencia desde un menú desplegable (ComboBox), que se carga dinámicamente desde la base de datos.
* Al presionar guardar, se refleja inmediatamente en la tabla.
* También podemos seleccionar un registro, cargar sus datos, modificarlos (Actualizar) o Eliminar el registro completamente."

### 6. Conclusión (4:45 - 5:00)
**[Acción en pantalla: Pantalla final o código principal]**

*Guion sugerido:*
"En conclusión, esta aplicación demuestra cómo la separación de responsabilidades a través de MVC y DAO, combinada con un diseño de base de datos sólido y un manejo de errores riguroso, resulta en un software seguro, escalable y listo para un entorno de producción. Gracias por su atención."
