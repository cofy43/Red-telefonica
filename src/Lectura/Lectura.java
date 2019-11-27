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
    private LinkedList<Red> digrafica;
    private int orden = 0;
    private int tamano = 0;

    //getters y setters para las variables que representaran el orden y tamaño de la digrafica
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

    public LinkedList<Red> getGrafica() {
        return this.digrafica;
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

    /**
     * Método principal encargado de la validacion completa del archivo xml,
     * analizando la sintaxis correcta de las etiquetas, asi como el nombre de 
     * sus propiedades y parámetros.
     * @param xmlString Dirrecion de la ubicacion del archivo
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     * @throws SAXException
     * @throws IOException
     */
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
        this.digrafica = analizaRed(doc);
        System.out.println(this.digrafica.toString());
    }

    /**
     * Método auxiliar encargado de verificar que en una cadena se encuentre un número
     * @param numero Posible representacion en cadena
     * @return Retorna -1 si la cadena no es un número, y en otro caso regresa el valor
     * del número.
     */
    public int verificaEntero(String numero) {
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException nfe) {
            System.out.println("error, el parametro \"" + numero + "\" no es numerico");
        }

        return -1;
    }

    /**
     * Método auxiliar para verificar la existencia de alguna propiedad en la etiqueta
     * @param cadena Posible parámetro de una propiedad.
     * @param propiedad Propiedad a la cual se intentará encontrar en alguna etiqueta.
     * @return true en caso de que la propiedad exista, false en caso contrario.
     */
    public boolean validaCadena(String cadena, String propiedad) {
        if (cadena.equals("")) {
            System.out.println("Propiedad " + propiedad + " no fue encontrada");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método encargado de la validación de las propiedades y parámetros de la etiqueta
     * Red, además de la creación de una lista de estaciones y otra de enlaces, para la 
     * creación de una gráfica dirigida en donde las estaciones serán vértices y los
     * enlaces las aristas.
     * @param doc Documento xml donde se almacenará la información necesaria para crear 
     *            la gráfica
     * @return  Lista de un objeto Red, el cual almacena dos lista, una de estaciones y
     *          otra con enlaces.
     */
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

    /**
     * Método encargado de la validación de las propiedades y parámetros de la etiqueta
     * Estacion, además de la creación de una lista de Objetos Estacion la cual almacenará
     * el nombre y el código de la estación, y un diccionario que almacenará la información
     * de los clientes de dicha estación.
     * @param prop Elemento que contendrá la información de los nodos Estacion contenidos
     *             en las etiquetas de Red
     * @return Lista de objetos Estacion, el cual contendrán la información de los vértices de 
     *         la gráfica dirigida.
     */
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
        HashMap<Integer, String> listaClientes;
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

    /**
     * Método auxiliar que verifica las propiedades y parámetros de las etiquetas
     * Cliente, almacenando la informacion en un diccionario en donde la llave es
     * el nombre del cliente y el valor es el número telefónico.
     * @param prop Elemento que contendrá la información de los Clientes contenidos
     *             en las etiquetas de Estacion.
     * @return Diccionario con la información de los clientes de una estación.
     */
    public HashMap<Integer, String> analizaCliente(Element prop) {
        NodeList listaDeEtiquetas = prop.getElementsByTagName("Cliente");

        //verificacion de seguridad
        if (listaDeEtiquetas.getLength() == 0) {
            System.out.println("Lista de clientes vacia");
            return null;
        }

        //Creacion de un diccionario con el tamaño justo de clientes
        HashMap<Integer, String> listaDeClientes = new HashMap<>(listaDeEtiquetas.getLength());
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

                listaDeClientes.put(telefonoDelCliente, nombreDelCliente);
            }
        }
        return listaDeClientes;
    }

    /**
     * Método encargado de la validación de las propiedades y parámetros de las etiquetas
     * Enlace, 
     * @param prop Elemento que contendrá la información de los nodos Enlace contenidos
     *             en las etiquetas de Red
     * @return Lista de objetos Enlace, el cual contendrán la información de las aristas de 
     *         la gráfica dirigida.
     */
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