package Clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LenguajeGramaticas {

    private static Vector<Vector<String>> M;
    private static int Filas, Columnas;

    public LenguajeGramaticas() {
        Filas = 0;
        Columnas = 0;
    }

    public static void Iniciar() {


        //DAMOS LECTURA AL ARCHIVO QUE CONTIENE
        try {
            LeerArchivo("Gramatica1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LeerArchivo(String archivo) throws IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            System.out.println(cadena);
            Filas += 1;
        }
        b.close();


        for (int i = 0; i < Columnas; i++) {
            Vector<String> Aux = new Vector<>();
            M.add(Aux);
            for (int j = 0; j < Filas; j++) {
            }
        }
    }


}
