package Lectura;

import java.util.HashMap;

public class Estacion {
    private String nombre;
    private int codigo;
    private HashMap<Integer, String> listaClientes;

    public Estacion(String nombre, int codigo, HashMap<Integer, String> listaClientes) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.listaClientes = listaClientes;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int contieneTelefono(int telefono) {
        if (listaClientes.containsKey(telefono)) {
            return telefono;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Nombre de estacion: " + this.nombre + " \nCodigo: " + this.codigo + "{\n\t"
                + this.listaClientes.toString() + "\t}\n";
    }
}