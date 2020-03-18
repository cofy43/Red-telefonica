package Lectura;

import java.util.LinkedList;

public class Red {
    private LinkedList<Estacion> listaEstacion;
    private LinkedList<Enlace> listaEnlace;

    public Red(LinkedList<Estacion> listaEstacion, LinkedList<Enlace> listaEnlace) {
        this.listaEnlace = listaEnlace;
        this.listaEstacion = listaEstacion;
    }

    public LinkedList<Estacion> getListaEstaciones() {
        return this.listaEstacion;
    }

    public LinkedList<Enlace> getListaEnlaces() {
        return this.listaEnlace;
    }

    @Override
    public String toString() {
        return "Estaciones:\n" +this.listaEstacion.toString() + "\nEnlaces:\n" + this.listaEnlace.toString();
    }
}