package dao;

import java.util.List;
import model.Cargo;
import exception.DatabaseException;

public interface CargoDAO {
    List<Cargo> listar() throws DatabaseException;
}
