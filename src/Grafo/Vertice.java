package Grafo;

import Lectura.Estacion;
import java.util.LinkedList;

public class Vertice {
    LinkedList<Arista> adyacentes;
    public Estacion elemento;
    public int distancia;
    public int indice;
    public Color color;

    public Vertice(Estacion elemento) {
        this.elemento = elemento;
        this.color = Color.NINGUNO;
        this.adyacentes = new LinkedList<>();
    }

    public int getCodigo() {
        return elemento.getCodigo();
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getGrado() {
        return this.adyacentes.size();
    }

    public int compareTo(Vertice v) {
        if (distancia > v.distancia) {
            return 1;
        } else if (distancia < v.distancia) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        String cadena = "";
        String vIni = "(" + elemento.getNombre() + ")-->";
        for (Arista edge : adyacentes) {
            cadena += vIni + "(" + edge.vecino.elemento.getNombre() + ")\n";
        }
        return cadena;
    }
}