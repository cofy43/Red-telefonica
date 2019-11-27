import Lectura.Lectura;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;




public class Sistema {
    public static void main(String[] args) {
        Lectura lec = new Lectura();
        boolean todoBien = true;
        try {
            lec.validacion("/home/martin/Documents/Proyecto-Datos/redat.xml");
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
    }
}