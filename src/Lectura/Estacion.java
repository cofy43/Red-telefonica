package Lectura;

import java.util.HashMap;

public class Estacion {
    private String nombre;
    private int codigo;
    private HashMap<String, Integer> listaClientes;

    public Estacion(String nombre, int codigo, HashMap<String, Integer> listaClientes) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.listaClientes = listaClientes;
    }

    @Override
    public String toString() {
        return "Nombre de estacion: " + this.nombre + 
                " \nCodigo: " + this.codigo + 
                "{\n\t" + this.listaClientes.toString() + "\t}\n";
    }
}