package Lectura;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileReader;


public class Lectura {
    // final static String[] ETIQUETASRED = { "Red", "numEstaciones", "numEnlaces"
    // };
    // final static String[] ETIQUETASESTACION = { "Estacion", "nombreEstacion",
    // "codigo" };
    // final static String[] ETIQUETASCliente = { "Cliente", "nombreCliente",
    // "telefono" };
    // final static String[] ETIQUETASENLACE = { "Enlace", "primeraEstacion",
    // "segundaEstacion", "distancia" };
    private int orden = 0;
    private int tamano = 0;
    LinkedList<Cliente> listClient;
    LinkedList<Estacion> listStation;
    LinkedList<Red> grafica;
    LinkedList<Enlace> listLinks;

    public int getOrden() {
        return this.orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getTamanio() {
        return this.tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public static void main(String[] args) {
        Lectura lec = new Lectura();
        boolean flag = true;
        try {
            lec.validacion("/home/martin/Documents/Proyecto-Datos/redat.xml");
        } catch (ParserConfigurationException e) {
            flag = false;
        } catch (FileNotFoundException e) {
            flag = false;
        } catch (SAXException e) {
            flag = false;
        } catch (IOException e) {
            flag = false;
        }
        String respuesta = "";
        if (flag) {
            respuesta = "valido";
            System.out.println("el archivo es " + respuesta);
            //System.out.println("la lista de enlaces es:\n " + lec.listLinks.toString());
            //System.out.println("la lista de estaciones es:\n " + lec.listStation.toString());
            //System.out.println("la lista de clientes es:\n " + lec.listClient.toString());
        } else {
            respuesta = "invalido";
            System.out.println("el archivo es " + respuesta);
            System.exit(-1);
        }

    }

    public void validacion(String xmlString)
            throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        File archivoxml = new File(xmlString);
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(new org.xml.sax.ErrorHandler() {
            @Override
            public void error(SAXParseException arg0) throws SAXException {
            }

            @Override
            public void fatalError(SAXParseException arg0) throws SAXException {
            }

            @Override
            public void warning(SAXParseException arg0) throws SAXException {
            }
        });
        Document doc = db.parse(archivoxml);
        doc.getDocumentElement().normalize();

        grafica = new LinkedList<>();
        NodeList listaDeRedes = doc.getElementsByTagName("Red");
        for (int i = 0; i < listaDeRedes.getLength(); i++) {
            Node red = listaDeRedes.item(i);

            if (red.getNodeType() == Node.ELEMENT_NODE) {
                Element atributosDeRed = (Element) red;
                this.setOrden(verificaEntero(atributosDeRed.getAttribute("numEstaciones")));
                this.setTamano(verificaEntero(atributosDeRed.getAttribute("numEnlaces")));

                if (this.getOrden() < 0 || this.getTamanio() < 0) {
                    return;
                }

                System.out.println("Numero de estaciones: " + this.getOrden());
                System.out.println("Numero de enlaces: " + this.getTamanio());

                NodeList listaDeEstaciones = atributosDeRed.getElementsByTagName("Estacion");
                System.out.println("\tNumero de estaciones: " + listaDeEstaciones.getLength());
                this.listStation = new LinkedList<>();
                for (int k = 0; k < this.getOrden(); k++) {
                    Node estacion = listaDeEstaciones.item(k);

                    if (estacion.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) estacion;
                        String nombreDeEstacion = elemento.getAttribute("nombreEstacion");
                        int codigo = verificaEntero(elemento.getAttribute("codigo"));
                        System.out.println("\t\tEstacion name: " + nombreDeEstacion);
                        System.out.println("\t\tEstacion code: " + codigo);

                        NodeList listaClientes = elemento.getElementsByTagName("Cliente");

                        this.listClient = new LinkedList<>();
                        for (int j = 0; j < listaClientes.getLength(); j++) {
                            Node cliente = listaClientes.item(j);

                            if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                                Element elemento1 = (Element) cliente;
                                String nombreDelCliente = elemento1.getAttribute("nombreCliente");
                                int telefonoDelCliente = verificaEntero(elemento1.getAttribute("telefono"));
                                System.out.println("\t\t\tCliente name: " + nombreDelCliente);
                                System.out.println("\t\t\tCliente phone number: " + telefonoDelCliente);
                                this.listClient.add(new Cliente(nombreDelCliente, telefonoDelCliente));
                                System.out.println("\t\t\tLista de clientes: " + this.listClient.toString());
                            }
                        }
                        listStation.add(new Estacion(nombreDeEstacion, codigo, listClient));
                        System.out.println("\t\tLista de estaciones es: " + listStation.toString());
                    }
                }

                NodeList listaDeEnlaces = atributosDeRed.getElementsByTagName("Enlace");
                System.out.println("\tNumero de enlaces: " + this.getTamanio());
                if (this.getTamanio() < listaDeEnlaces.getLength()
                        || this.getTamanio() > listaDeEstaciones.getLength()) {
                    System.out.println("error, el numero de enlaces no coincide con el numero de etiquetas de enlace");
                    return;
                }
                this.listLinks = new LinkedList<>();
                for (int j = 0; j < this.getTamanio(); j++) {
                    Node enlace = listaDeEnlaces.item(j);

                    if (enlace.getNodeType() == Node.ELEMENT_NODE) {
                        Element propiedadesDeEnlace = (Element) enlace;
                        int primerEnlace = verificaEntero(propiedadesDeEnlace.getAttribute("primeraEstacion"));
                        int segungoEnlace = verificaEntero(propiedadesDeEnlace.getAttribute("segundaEstacion"));
                        int peso = verificaEntero(propiedadesDeEnlace.getAttribute("distancia"));
                        System.out.println("\t\tPrimer enlace: " + primerEnlace);
                        System.out.println("\t\tSegundo enlace enlace: " + segungoEnlace);
                        System.out.println("\t\tPeso enlace: " + peso);
                        this.listLinks.add(new Enlace(primerEnlace, segungoEnlace, peso));
                        System.out.println("\t\tLista de enlaces: " + this.listStation.toString());
                    }
                }
                this.grafica.add(new Red(listStation, listLinks));
                System.out.println("Lista de Redes: " + this.grafica.toString());
            }
        }
    }

    public int verificaEntero(String numero) {
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException nfe) {
            System.out.println("error, el parametro \"" + numero + "\" no es numerico");
        }

        return -1;

    }

    /*
     * public boolean validacionEtiquetas(String etiqueta) { String sinCaracteres =
     * (((((etiqueta.replaceAll("\"", "")).replaceAll("<", "")).replaceAll("/",
     * ""))) .replaceAll(">", "")).replaceAll("\t", ""); String[] propiedades =
     * sinCaracteres.split(" "); String nombreEtiqueta = propiedades[0]; switch
     * (nombreEtiqueta) { case "Red": for (String s : propiedades) { if
     * (!s.equals("Red")) { int aux = s.indexOf("="); String num = s.substring(aux +
     * 1); String parametro = s.substring(0, aux); try { aux =
     * Integer.parseInt(num); } catch (NumberFormatException nfe) {
     * System.out.println("error al intentar convertir una cadena a un numero");
     * return false; } switch (parametro) { case "numEstaciones":
     * System.out.println("Entra en caso 1"); setOrden(aux); break; case
     * "numEnlaces": System.out.println("Entra en caso 2"); setTamano(aux); default:
     * System.out.println("Error en la escritura de propiedades"); return false; } }
     * } break; case "Estacion": for (int i = 1; i <= 2; i++) {
     * 
     * } break; case "Cliente": for (int i = 1; i <= 2; i++) {
     * 
     * } break; case "Enlace": for (int i = 1; i <= 3; i++) {
     * 
     * } break; } return true; }
     */
}