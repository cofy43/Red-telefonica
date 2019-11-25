package Lectura;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lectura {

    public void leeArchivo(String direccion) throws FileNotFoundException, IOException{
        String l;
        FileReader fr = new FileReader(direccion);
        BufferedReader br = new BufferedReader(fr);
        while ((l = br.readLine()) != null) {
            System.out.println(l);
        }
        br.close();
    }

    public static void main(String[] args) throws IOException{
        Lectura lec = new Lectura();
        lec.leeArchivo("/home/martin/Documents/Proyecto-Datos/redat.xml");
    }
}