package Grafo;

import Lectura.Enlace;
import Lectura.Estacion;
import Lectura.Red;
import java.util.LinkedList;


public class DiGrafica {
    public LinkedList<Estacion> estaciones;
    public LinkedList<Enlace> enlaces;
    public LinkedList<Vertice> vertices;

    public DiGrafica(Red red) {
        this.estaciones = red.getListaEstaciones();
        this.enlaces = red.getListaEnlaces();
        this.vertices = new LinkedList<>();

        for (Estacion e : estaciones) {
            this.agrega(e);
        }

    }

    private void agrega(Estacion estacion) {
        vertices.add(new Vertice(estacion));
    }

    public void enlaza(int inicial, int fin) {
        
    }

}