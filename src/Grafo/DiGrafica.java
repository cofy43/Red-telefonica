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

    public void enlaza(int inicial, int fin, int peso) {
        Vertice i = busquedaPorCodigo(inicial);
        Vertice f = busquedaPorCodigo(fin);

        if (i == null) {
            System.out.println("No se encontro ninguna estacion con codigo: " + inicial);
            return;
        }

        if (f == null) {
            System.out.println("No se encontro ninguna estacion con codigo: " + fin);
            return;
        }

        i.adyacentes.add(new Arista(f, peso));
    }

    private Vertice busquedaPorCodigo(int codigo) {
        for (Vertice v : vertices) {
            if (v.getCodigo() == codigo) {
                return v;
            }
        }
        return null;
    }

}