package Clases;

import java.util.HashSet;
import java.util.Vector;

public class TablaLL1 {

    private char EPSILON = '&';
    private Vector<Vector<String>> Tabla;
    private Integer Filas, Columnas;
    private HashSet<String> NoTerminales;
    private HashSet<String> Terminales;


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

    //Hacer split en reglas
    public HashSet First(String Reglas) {
        HashSet<String> C = new HashSet<>();

        if (NoTerminales.contains(Reglas) || !Reglas.equals(EPSILON)) {
            C.add(Reglas);
            return C;
        }

        for (String i : NoTerminales) {
            C.addAll(First(i));
        }

        if (C.contains(EPSILON) && Reglas.length() == 1) {
            C.remove(EPSILON);
            C.addAll(First(Reglas));
        }

        return C;
    }


    public void Follow(String SimbNT) {
        HashSet<String> C = new HashSet<>();

        if (SimbNT)
            C.add("$");

        for (String i:            ) {
            C.add(First());
        }

        if(C.contains(EPSILON)) {
            C.remove(EPSILON);
            C.add(Follow());
        }

        for (String j: ) {
            if(SimbNT == )
                // return

            C.add(Follow());
        }
        return C;
    }
}
