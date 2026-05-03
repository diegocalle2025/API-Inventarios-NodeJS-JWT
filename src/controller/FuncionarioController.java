package controller;

import dao.CargoDAOImpl;
import dao.DependenciaDAOImpl;
import dao.FuncionarioDAOImpl;
import exception.AppException;
import exception.ValidationException;
import model.Cargo;
import model.Dependencia;
import model.Funcionario;

import java.util.List;

public class FuncionarioController {

    private FuncionarioDAOImpl funcionarioDAO;
    private CargoDAOImpl cargoDAO;
    private DependenciaDAOImpl dependenciaDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAOImpl();
        this.cargoDAO = new CargoDAOImpl();
        this.dependenciaDAO = new DependenciaDAOImpl();
    }

    public List<Funcionario> listarFuncionarios() throws AppException {
        return funcionarioDAO.listar();
    }

    public List<Cargo> listarCargos() throws AppException {
        return cargoDAO.listar();
    }

    public List<Dependencia> listarDependencias() throws AppException {
        return dependenciaDAO.listar();
    }

    public void guardarFuncionario(String nombre, String apellido, String documento, Cargo cargo, Dependencia dependencia) throws AppException {
        validarDatos(nombre, apellido, documento, cargo, dependencia);

        Funcionario f = new Funcionario();
        f.setNombre(nombre);
        f.setApellido(apellido);
        f.setDocumento(documento);
        f.setIdCargo(cargo.getId());
        f.setIdDependencia(dependencia.getId());

        funcionarioDAO.crear(f);
    }

    public void actualizarFuncionario(int id, String nombre, String apellido, String documento, Cargo cargo, Dependencia dependencia) throws AppException {
        validarDatos(nombre, apellido, documento, cargo, dependencia);

        Funcionario f = new Funcionario();
        f.setId(id);
        f.setNombre(nombre);
        f.setApellido(apellido);
        f.setDocumento(documento);
        f.setIdCargo(cargo.getId());
        f.setIdDependencia(dependencia.getId());

        funcionarioDAO.actualizar(f);
    }

    public void eliminarFuncionario(int id) throws AppException {
        funcionarioDAO.eliminar(id);
    }

    private void validarDatos(String nombre, String apellido, String documento, Cargo cargo, Dependencia dependencia) throws ValidationException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre es obligatorio.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ValidationException("El apellido es obligatorio.");
        }
        if (documento == null || documento.trim().isEmpty()) {
            throw new ValidationException("El documento es obligatorio.");
        }
        if (cargo == null) {
            throw new ValidationException("Debe seleccionar un cargo.");
        }
        if (dependencia == null) {
            throw new ValidationException("Debe seleccionar una dependencia.");
        }
    }
}
