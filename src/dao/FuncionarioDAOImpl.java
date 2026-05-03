package dao;

import connection.Conexion;
import model.Funcionario;
import exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    @Override
    public void crear(Funcionario f) throws DatabaseException {
        String sql = "INSERT INTO funcionarios(nombre, apellido, documento, id_cargo, id_dependencia) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, f.getNombre());
            ps.setString(2, f.getApellido());
            ps.setString(3, f.getDocumento());
            ps.setInt(4, f.getIdCargo());
            ps.setInt(5, f.getIdDependencia());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al crear funcionario. Puede que el documento ya exista.", e);
        }
    }

    @Override
    public List<Funcionario> listar() throws DatabaseException {
        List<Funcionario> lista = new ArrayList<>();
        // Hacemos JOIN para traer los nombres
        String sql = "SELECT f.*, c.nombre_cargo, d.nombre_dependencia " +
                     "FROM funcionarios f " +
                     "INNER JOIN cargos c ON f.id_cargo = c.id_cargo " +
                     "INNER JOIN dependencias d ON f.id_dependencia = d.id_dependencia";

        try (Connection conn = Conexion.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id_funcionario"));
                f.setNombre(rs.getString("nombre"));
                f.setApellido(rs.getString("apellido"));
                f.setDocumento(rs.getString("documento"));
                f.setIdCargo(rs.getInt("id_cargo"));
                f.setIdDependencia(rs.getInt("id_dependencia"));
                // Extra del JOIN
                f.setNombreCargo(rs.getString("nombre_cargo"));
                f.setNombreDependencia(rs.getString("nombre_dependencia"));

                lista.add(f);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar funcionarios", e);
        }

        return lista;
    }

    @Override
    public void actualizar(Funcionario f) throws DatabaseException {
        String sql = "UPDATE funcionarios SET nombre=?, apellido=?, documento=?, id_cargo=?, id_dependencia=? WHERE id_funcionario=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, f.getNombre());
            ps.setString(2, f.getApellido());
            ps.setString(3, f.getDocumento());
            ps.setInt(4, f.getIdCargo());
            ps.setInt(5, f.getIdDependencia());
            ps.setInt(6, f.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar funcionario", e);
        }
    }

    @Override
    public void eliminar(int id) throws DatabaseException {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar funcionario", e);
        }
    }
}