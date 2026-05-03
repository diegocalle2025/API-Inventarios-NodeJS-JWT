package connection;

import exception.DatabaseException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static Connection connection = null;
    private static Properties properties = new Properties();

    // Bloque estático para cargar las propiedades una sola vez
    static {
        try (InputStream is = Conexion.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo config.properties");
            }
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar configuración de BD", e);
        }
    }

    // Constructor privado para evitar instancias (Patrón Singleton)
    private Conexion() {}

    public static Connection getConnection() throws DatabaseException {
        try {
            if (connection == null || connection.isClosed()) {
                String url = properties.getProperty("db.url");
                String user = properties.getProperty("db.user");
                String pass = properties.getProperty("db.password");
                
                connection = DriverManager.getConnection(url, user, pass);
            }
            return connection;
        } catch (SQLException e) {
            throw new DatabaseException("Error de conexión a la base de datos.", e);
        }
    }

    public static void closeConnection() throws DatabaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al cerrar la conexión.", e);
        }
    }
}