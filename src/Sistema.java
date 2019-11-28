import Grafo.DiGrafica;
import Grafo.Vertice;
import Lectura.Lectura;
import Lectura.Red;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Sistema {

    private DiGrafica grafo;
    private Lectura lec;
    private Scanner sc;

    public Sistema() {
        lec = new Lectura();
        sc = new Scanner(System.in);
        cargaArchivo();
    }

    private void imprimeMenuOpciones() {
        System.out.println("Opciones:");
        System.out.println("1) ruta minima entre dos numeros telefonicos");
        System.out.println("2) buscar enlace entre dos codigos de area");
        System.out.println("3) Finalizar programa");
    }

    private void cargaArchivo() {
        boolean todoBien = true;
        try {
            lec.validacion("src/redat.xml");
        } catch (ParserConfigurationException e) {
            todoBien = false;
        } catch (FileNotFoundException e) {
            todoBien = false;
        } catch (SAXException e) {
            todoBien = false;
        } catch (IOException e) {
            todoBien = false;
        }
        String respuesta = "";
        if (todoBien) {
            respuesta = "valido";
            System.out.println("el archivo es " + respuesta);
        } else {
            respuesta = "invalido";
            System.out.println("el archivo es " + respuesta);
            System.exit(-1);
        }
        Red red = lec.getGrafica().getFirst();
        grafo = new DiGrafica(red);
    }

    private int verificaOpcion(int imput) {
        if (imput < 1 || imput > 3) {
            return -1;
        }
        return imput;
    }

    private int verificaImput() {
        int imput = -1;
        boolean entrada = true;
        while (entrada) {
            try {
                imput = sc.nextInt();
                entrada = false;
            } catch (InputMismatchException ime) {
                System.out.println("Opcion no numerica");
                imput = 0;
                sc = new Scanner(System.in);
            }
        }
        return imput;
    }

    public void ejecutaSistema() {
        boolean ejecucion = true;
        int imput;
        /**
         * int telefono = 55443322; int codigo =
         * this.grafo.busquedaPorTelefono(telefono); if (codigo != -1) {
         * System.out.println("Codigo de estacion: " + codigo); } else {
         * System.out.println("No se encontro el codigo de estacion"); }
         */
        while (ejecucion) {
            imprimeMenuOpciones();
            imput = verificaImput();
            imput = verificaOpcion(imput);
            if (imput == -1) {
                System.out.println("Opcion invalida");
            } else {
                switch (imput) {
                case 1:
                    int inicial = grafo.busquedaPorTelefono(56581111);
                    System.out.println("inicial: " + inicial);
                    Vertice vo = grafo.busquedaPorCodigo(inicial);
                    int cliente = grafo.busquedaPorTelefono(55443322);
                    System.out.println("final: " + cliente);
                    Vertice v = grafo.busquedaPorCodigo(cliente);
                    break;
                case 2:
                    System.out.println("Elegiste busqueda de conexidad");
                    break;
                case 3:
                    System.out.println("Elegiste terminar con la ejecucion del programa");
                    ejecucion = false;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Sistema system = new Sistema();
        system.ejecutaSistema();
    }
}