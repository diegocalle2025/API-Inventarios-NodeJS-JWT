package dao;

import java.util.List;
import model.Funcionario;
import exception.DatabaseException;

public interface FuncionarioDAO {
    void crear(Funcionario f) throws DatabaseException;
    List<Funcionario> listar() throws DatabaseException;
    void actualizar(Funcionario f) throws DatabaseException;
    void eliminar(int id) throws DatabaseException;
}
