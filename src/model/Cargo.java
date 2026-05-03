package model;

public class Cargo {
    private int id;
    private String nombre;
    private double salarioBase;

    public Cargo() {}

    public Cargo(int id, String nombre, double salarioBase) {
        this.id = id;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    @Override
    public String toString() {
        return nombre; // Útil para el JComboBox
    }
}
