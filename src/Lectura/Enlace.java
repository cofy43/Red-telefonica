package Lectura;

public class Enlace {
    private int primeraEstacion;
    private int segundaEstacion;
    private int pesoDeEnlace;

    public Enlace(int primeraEstacion, int segundaEstacion, int pesoDeEnlace) {
        this.primeraEstacion = primeraEstacion;
        this.segundaEstacion = segundaEstacion;
        this.pesoDeEnlace = pesoDeEnlace;
    }

    public int getPrimeraEstacion() {
        return this.primeraEstacion;
    }

    public int getSegundaEstacion() {
        return this.segundaEstacion;
    }

    public int getPeso() {
        return this.pesoDeEnlace;
    }

    @Override
    public String toString() {
        return this.primeraEstacion + " -> " + this.segundaEstacion + " (" + this.pesoDeEnlace + ")";
    }
}