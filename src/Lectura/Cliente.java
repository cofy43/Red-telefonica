package Lectura;

public class Cliente {
    private String nombre;
    private int telefono;

    public Cliente(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.nombre + 
               " \nTelefono: " + this.telefono;
    }
}
