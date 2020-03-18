package Grafo;

public class Arista {
    Vertice vecino;
    int peso;

    public Arista(Vertice vecino, int peso) {
        this.peso = peso;
        this.vecino = vecino;
    }

    public int getElemento() {
        return vecino.getCodigo();
    }

    public int getGrado() {
        return vecino.getGrado();
    }

    public Color getColor() {
        return vecino.getColor();
    }

    public void setColor(Color color) {
        vecino.setColor(color);
    }

    public String toString() {
        return vecino.toString();
    }
}