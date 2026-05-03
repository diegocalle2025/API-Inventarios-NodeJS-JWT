package dao;

import connection.Conexion;
import model.Cargo;
import exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoDAOImpl implements CargoDAO {

    @Override
    public List<Cargo> listar() throws DatabaseException {
        List<Cargo> lista = new ArrayList<>();
        String sql = "SELECT * FROM cargos";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cargo c = new Cargo();
                c.setId(rs.getInt("id_cargo"));
                c.setNombre(rs.getString("nombre_cargo"));
                c.setSalarioBase(rs.getDouble("salario_base"));
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar cargos", e);
        }

        return lista;
    }
}
