package Lectura;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Lectura {
    public static void main(String[] args) {
        boolean flag = true;
        try {
            validate("/home/martin/Documents/Proyecto-Datos/redat.xml");
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
        } else {
            respuesta = "invalido";
        }
        System.out.println("el archivo es " + respuesta);
    }

    public static void validate(String xmlString)
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
    }
}