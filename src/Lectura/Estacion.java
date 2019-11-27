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

    public boolean contieneTelefono(int telefono) {
        return listaClientes.containsKey(telefono);
    }

    @Override
    public String toString() {
        return "Nombre de estacion: " + this.nombre + 
                " \nCodigo: " + this.codigo + 
                "{\n\t" + this.listaClientes.toString() + "\t}\n";
    }
}