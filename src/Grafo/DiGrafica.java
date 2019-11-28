package Grafo;

import Lectura.Enlace;
import Lectura.Estacion;
import Lectura.Red;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DiGrafica {
    public LinkedList<Estacion> estaciones;
    public LinkedList<Enlace> enlaces;
    public LinkedList<Vertice> vertices;
    public PriorityQueue<Vertice> queue;

    public DiGrafica(Red red) {
        this.estaciones = red.getListaEstaciones();
        this.enlaces = red.getListaEnlaces();
        this.vertices = new LinkedList<>();
        this.queue = new PriorityQueue<>();

        // Creacion de vertices
        for (Estacion e : estaciones) {
            this.agrega(e);
        }

        // Creacion de las aristas
        for (Enlace enlace : enlaces) {
            int inicial = enlace.getPrimeraEstacion();
            int fin = enlace.getSegundaEstacion();
            int peso = enlace.getPeso();
            enlaza(inicial, fin, peso);
        }

        System.out.println(toString());
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
        // f.adyacentes.add(new Arista(i, peso));
    }

    public void desconecta(int inicial, int fin) {
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

        if (!existeAdyacencia(inicial, fin)) {
            System.out.println(
                    "Las estaciones " + i.elemento.getNombre() + " " + f.elemento.getNombre() + " no estan conectadas");
            return;
        }

        if (inicial == fin) {
            System.out.println("Error, estas intentando desconectar una arista entre el mismo vertice");
        }

        Vertice a = null;
        Vertice b = null;

        for (Arista edge : i.adyacentes) {
            if (edge.vecino.elemento.getNombre().equals(f.elemento.getNombre())) {
                a = edge.vecino;
            }
        }

        for (Arista edge : f.adyacentes) {
            if (edge.vecino.elemento.getNombre().equals(i.elemento.getNombre())) {
                b = edge.vecino;
            }
        }

        i.adyacentes.remove(b);
        f.adyacentes.remove(a);
    }

    private boolean existeAdyacencia(int ini, int fin) {
        Vertice i = busquedaPorCodigo(ini);
        Vertice f = busquedaPorCodigo(fin);

        if (i == null) {
            System.out.println("No se encontro ninguna estacion con codigo: " + ini);
            return false;
        }

        if (f == null) {
            System.out.println("No se encontro ninguna estacion con codigo: " + fin);
            return false;
        }

        for (Arista a : i.adyacentes) {
            if (a.vecino.elemento.getNombre().equals(f.elemento.getNombre())) {
                return true;
            }
        }

        return false;
    }

    public Vertice busquedaPorCodigo(int codigo) {
        for (Vertice v : vertices) {
            if (v.getCodigo() == codigo) {
                return v;
            }
        }
        return null;
    }

    public int busquedaPorTelefono(int telefono) {
        int codigo = -1;
        for (Vertice v : this.vertices) {
            if (v.elemento.contieneTelefono(telefono) != -1) {
                System.out.println(v.elemento.imprimeClientes());
                return v.elemento.getCodigo();
            }
        }
        return codigo;
    }

    @Override
    public String toString() {
        String cadena = "";
        for (Vertice v : vertices) {
            cadena += v.toString();
        }
        return cadena;
    }
}