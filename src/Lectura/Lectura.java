package Lectura;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        // boolean flag = true;
        // try {
        // validacion("/home/martin/Documents/Proyecto-Datos/redat.xml");
        // } catch (ParserConfigurationException e) {
        // flag = false;
        // } catch (FileNotFoundException e) {
        // flag = false;
        // } catch (SAXException e) {
        // flag = false;
        // } catch (IOException e) {
        // flag = false;
        // }
        // String respuesta = "";
        // if (flag) {
        // respuesta = "valido";
        // } else {
        // respuesta = "invalido";
        // }
        // System.out.println("el archivo es " + respuesta);
        lec.validacionEtiquetas("<Red numEstaciones=\"5\" numEnlaces=\"8\">");
        System.out.println("El orden es: " + lec.getOrden() + " y el tamanio es: " + lec.getTamanio());
    }

    public static void validacion(String xmlString)
            throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
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
        db.parse(new FileInputStream(xmlString));
        String cadena, documento = "";
        FileReader fr = new FileReader(xmlString);
        BufferedReader br = new BufferedReader(fr);
        while ((cadena = br.readLine()) != null) {
            documento += cadena;
        }
        // validacionEtiquetas(documento);
    }

    public boolean validacionEtiquetas(String etiqueta) {
        String sinCaracteres = (((((etiqueta.replaceAll("\"", "")).replaceAll("<", "")).replaceAll("/", "")))
                .replaceAll(">", "")).replaceAll("\t", "");
        String[] propiedades = sinCaracteres.split(" ");
        String nombreEtiqueta = propiedades[0];
        switch (nombreEtiqueta) {
        case "Red":
            for (String s : propiedades) {
                if (!s.equals("Red")) {
                    int aux = s.indexOf("=");
                    String num = s.substring(aux + 1);
                    String parametro = s.substring(0, aux);
                    try {
                        aux = Integer.parseInt(num);
                    } catch (NumberFormatException nfe) {
                        System.out.println("error al intentar convertir una cadena a un numero");
                        return false;
                    }
                    switch (parametro) {
                    case "numEstaciones":
                        System.out.println("Entra en caso 1");
                        setOrden(aux);
                        break;
                    case "numEnlaces":
                        System.out.println("Entra en caso 2");
                        setTamano(aux);
                    default:
                        System.out.println("Error en la escritura de propiedades");
                        return false;
                    }
                }
            }
            break;
        case "Estacion":
            for (int i = 1; i <= 2; i++) {

            }
            break;
        case "Cliente":
            for (int i = 1; i <= 2; i++) {

            }
            break;
        case "Enlace":
            for (int i = 1; i <= 3; i++) {

            }
            break;
        }
        return true;
    }
}