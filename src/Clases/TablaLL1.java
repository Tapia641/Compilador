package Clases;

import java.util.Vector;

public class TablaLL1 {

    private Vector<Vector<String>> Tabla;
    private Integer Filas, Columnas;


    //
    public TablaLL1() {

        Tabla = new Vector<>();

        for (int i = 0; i < Columnas; i++) {
            Vector<String> Izquierda = new Vector<>();
            Izquierda.add("T");//Cadena[i]
            Tabla.add(Izquierda);
            for (int j = 0; i < Filas; j++) {
                Tabla.get(i).add("+T");
            }
        }
    }

    public void First() {

    }


    public void Follow() {

    }
}
