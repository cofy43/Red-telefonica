package Lectura;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
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
        LinkedList<Red> listaDeRedes = analizaRed(doc);
        System.out.println(listaDeRedes.toString());
    }

    public int verificaEntero(String numero) {
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException nfe) {
            System.out.println("error, el parametro \"" + numero + "\" no es numerico");
        }

        return -1;
    }

    public boolean validaCadena(String cadena, String propiedad) {
        if (cadena.equals("")) {
            System.out.println("Propiedad " + propiedad + " no fue encontrada");
            return false;
        } else {
            return true;
        }
    }

    public LinkedList<Red> analizaRed(Document doc) {
        LinkedList<Red> listRedes = new LinkedList<>();
        LinkedList<Estacion> listaDeEstaciones;
        LinkedList<Enlace> listaDeEnlaces;
        NodeList listaDeRedes = doc.getElementsByTagName("Red");
        Node red;
        //Recorremos la lista de redes.
        for (int i = 0; i < listaDeRedes.getLength(); i++) {
            red = listaDeRedes.item(i);

            if (red.getNodeType() == Node.ELEMENT_NODE) {
                Element atributosDeRed = (Element) red;
                this.setOrden(verificaEntero(atributosDeRed.getAttribute("numEstaciones")));
                this.setTamano(verificaEntero(atributosDeRed.getAttribute("numEnlaces")));

                //Condicion que verifica la conversion a entero.
                if (this.getOrden() < 0 || this.getTamanio() < 0) {
                    System.out.println("error en los parametros de las propiedades de la etiqueta Red numero: " + i);
                    System.exit(-1);
                }

                //Creacion de estaciones
                listaDeEstaciones = analizaEstacion(atributosDeRed);
                //Creacion de enlaces
                listaDeEnlaces = analizaEnlace(atributosDeRed);
                listRedes.add(new Red(listaDeEstaciones, listaDeEnlaces));
            }
        }
        return listRedes;
    }

    public LinkedList<Estacion> analizaEstacion(Element prop) {
        NodeList listaDeEtiquetas = prop.getElementsByTagName("Estacion");

        //Condicion que verifica la validez del parametro numEstaciones
        //con el número de etiquetas
        if (this.getOrden() != listaDeEtiquetas.getLength()) {
            System.out.println("Error, no coincide el numero de estaciones en la etiqueta Red con el numero de Etiquetas Estacion");
            System.exit(-1);
        }

        //Condicion que verifica que existan estaciones
        if (listaDeEtiquetas.getLength() == 0) {
            System.out.println("No se encontraron estaciones registradas");
            return null;
        }

        LinkedList<Estacion> listaDeEstaciones = new LinkedList<>();
        Node estacion;
        HashMap<String, Integer> listaClientes;
        Element atributosDeEstacion;


        for (int i = 0; i < this.getOrden(); i++) {
            estacion = listaDeEtiquetas.item(i);

            if (estacion.getNodeType() == Node.ELEMENT_NODE) {
                atributosDeEstacion = (Element) estacion;
                String nombreDeEstacion = atributosDeEstacion.getAttribute("nombreEstacion");
                int codigo = verificaEntero(atributosDeEstacion.getAttribute("codigo"));

                if (codigo < 0 || !(validaCadena(nombreDeEstacion, "nombreEstacion"))) {
                    System.out.println("Error en las propiedades de la etiqueta Estacion numero: " + i);
                    System.exit(-1);
                }

                //Creacion de clientes
                listaClientes = analizaCliente(atributosDeEstacion);
                
                listaDeEstaciones.add(new Estacion(nombreDeEstacion, codigo, listaClientes));

            }
        }   
        return listaDeEstaciones;
    }

    public HashMap<String, Integer> analizaCliente(Element prop) {
        NodeList listaDeEtiquetas = prop.getElementsByTagName("Cliente");

        //verificacion de seguridad
        if (listaDeEtiquetas.getLength() == 0) {
            System.out.println("Lista de clientes vacia");
            return null;
        }

        //Creacion de un diccionario con el tamaño justo de clientes
        HashMap<String, Integer> listaDeClientes = new HashMap<>(listaDeEtiquetas.getLength());
        Node cliente;
        Element atributosDeCliente;
        for (int i = 0; i < listaDeEtiquetas.getLength(); i++) {
            cliente  = listaDeEtiquetas.item(i);

            if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                atributosDeCliente = (Element) cliente;
                String nombreDelCliente = atributosDeCliente.getAttribute("nombreCliente");
                int telefonoDelCliente = verificaEntero(atributosDeCliente.getAttribute("telefono"));

                if (telefonoDelCliente < 0 || !validaCadena(nombreDelCliente, "nombreCliente")) {
                    System.out.println("Error en las propiedades de una etiqueta Cliente");
                    System.exit(-1);
                }

                listaDeClientes.put(nombreDelCliente, telefonoDelCliente);
            }
        }
        return listaDeClientes;
    }

    public LinkedList<Enlace> analizaEnlace(Element prop) {
        NodeList listaDeEtiquetas = prop.getElementsByTagName("Enlace");

        //Condicion que verifica la validez del parametro numEstaciones
        //con el número de etiquetas
        if (this.getTamanio() != listaDeEtiquetas.getLength()) {
            System.out.println("Error, el numero de enlaces no coincide con el numero de etiquetas de Enlaces");
            System.exit(-1);
        }

        //Condicion que verifica que existan enlaces
        if (listaDeEtiquetas.getLength() == 0) {
            System.out.println("No se encontraron enlaces registrados");
        }

        LinkedList<Enlace> listaDeEnlace = new LinkedList<>();
        Node enlace;
        Element atributosDeEnlace;

        for (int i = 0; i < listaDeEtiquetas.getLength(); i++) {
            enlace = listaDeEtiquetas.item(i);

            if (enlace.getNodeType() == Node.ELEMENT_NODE) {
                atributosDeEnlace = (Element) enlace;
                int primeraEstacion = verificaEntero(atributosDeEnlace.getAttribute("primeraEstacion"));
                int segundaEstacion = verificaEntero(atributosDeEnlace.getAttribute("segundaEstacion"));
                int distacia = verificaEntero(atributosDeEnlace.getAttribute("distancia"));

                if (primeraEstacion < 0 || segundaEstacion < 0 || distacia < 0) {
                    System.out.println("Error en las propiedades de la etiqueta Enlace numero: " + i);
                    System.exit(-1);
                }

                listaDeEnlace.add(new Enlace(primeraEstacion, segundaEstacion, distacia));
            }
        }

        return listaDeEnlace;
    }
}