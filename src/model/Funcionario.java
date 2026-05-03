package model;

public class Funcionario {
    private int id;
    private String nombre;
    private String apellido;
    private String documento;
    private int idCargo;
    private int idDependencia;
    
    // Campos auxiliares para mostrar en la Vista (resultado del JOIN)
    private String nombreCargo;
    private String nombreDependencia;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public int getIdCargo() { return idCargo; }
    public void setIdCargo(int idCargo) { this.idCargo = idCargo; }

    public int getIdDependencia() { return idDependencia; }
    public void setIdDependencia(int idDependencia) { this.idDependencia = idDependencia; }

    public String getNombreCargo() { return nombreCargo; }
    public void setNombreCargo(String nombreCargo) { this.nombreCargo = nombreCargo; }

    public String getNombreDependencia() { return nombreDependencia; }
    public void setNombreDependencia(String nombreDependencia) { this.nombreDependencia = nombreDependencia; }
}
