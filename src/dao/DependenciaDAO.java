package dao;

import java.util.List;
import model.Dependencia;
import exception.DatabaseException;

public interface DependenciaDAO {
    List<Dependencia> listar() throws DatabaseException;
}
