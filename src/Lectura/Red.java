package Lectura;

import java.util.LinkedList;

public class Red {
    LinkedList<Estacion> listaEstacion;
    LinkedList<Enlace> listaEnlace;

    public Red(LinkedList<Estacion> listaEstacion, LinkedList<Enlace> listaEnlace) {
        this.listaEnlace = listaEnlace;
        this.listaEstacion = listaEstacion;
    }

    @Override
    public String toString() {
        return "{\n" + this.listaEstacion + "\n" + this.listaEnlace + "\n}";
    }
}