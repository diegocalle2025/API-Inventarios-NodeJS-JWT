package dao;

import connection.Conexion;
import model.Dependencia;
import exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DependenciaDAOImpl implements DependenciaDAO {

    @Override
    public List<Dependencia> listar() throws DatabaseException {
        List<Dependencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM dependencias";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dependencia d = new Dependencia();
                d.setId(rs.getInt("id_dependencia"));
                d.setNombre(rs.getString("nombre_dependencia"));
                d.setUbicacion(rs.getString("ubicacion"));
                lista.add(d);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar dependencias", e);
        }

        return lista;
    }
}
