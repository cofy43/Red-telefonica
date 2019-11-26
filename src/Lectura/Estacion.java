package Lectura;

import java.util.LinkedList;

public class Estacion {
    private String nombre;
    private int codigo;
    private LinkedList<Cliente> listaClientes;

    public Estacion(String nombre, int codigo, LinkedList<Cliente> listaClientes) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.listaClientes = listaClientes;
    }

    public String toStrong() {
        return "Nombre de estacion: " + this.nombre + 
                " \nCodigo: " + this.codigo + 
                "{\n\t" + this.listaClientes + "\t}";
    }
}