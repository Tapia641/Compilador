package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

public class TablaLL1 {

    private char EPSILON = '&';
    private Vector<Vector<String>> Tabla;
    private Integer Filas, Columnas;
    private HashSet<String> NoTerminales;
    private HashSet<String> Terminales;
    private Vector<String> ConjuntoReglas;
    private static Vector<String> M;
    private static String Memo[][];

    public TablaLL1(String archivo) throws IOException {
        Terminales = new HashSet<>();
        NoTerminales = new HashSet<>();
        Filas = 0;
        Columnas = 0;
        Tabla = new Vector<>();
        Memo = new String[100][100];
        M = new Vector<>();
        String cadena;
        BufferedReader br = new BufferedReader(new FileReader(new File(archivo).getAbsolutePath()));

        while ((cadena = br.readLine()) != null) {
            M = new Vector<>();
            for (String i : cadena.split(" ")) {
                M.add(i);
            }
            NoTerminales.add(M.get(0));
            Tabla.add(M);
        }

        for (Vector<String> i : Tabla) {
            for (String j : i) {
                if (!NoTerminales.contains(j) && !j.equals("->") && !j.equals("|") && !j.equals(";"))
                    Terminales.add(j);
            }
        }
        System.out.println("No terminales " + NoTerminales);
        System.out.println("Terminales " + Terminales);



        System.out.println("Gramatica leida:");
        for (int i = 0; i < 100; i++) {
            Memo[i][0] = Tabla.get(i).get(0);
            System.out.print("["+Memo[i][0]+"]");
            for (int j = 0; j < Tabla.get(i).size() - 1; j++) {
                if (Tabla.get(i).get(j+1).equals("|")){
                    Memo[i+1][0] = Tabla.get(i).get(i-1);
                    System.out.print("["+Memo[i+1][0]+"]");
                    Memo[i][0] = Tabla.get(i).get(0);
                    Memo[i][j + 1] = Tabla.get(i).get(j+1);
                }else{
                    Memo[i][0] = Tabla.get(i).get(j);
                    Memo[i][j + 1] = Tabla.get(i).get(j+1);
                }
                System.out.print(Memo[i][j+1]);
            }
            System.out.println();
        }

    }

    //Hacer split en reglas
    public HashSet First(String Regla) {
        HashSet<String> C = new HashSet<>();

        for (Vector<String> i : Tabla) {
            if (i.get(0).equals(Regla)) {

                for (String j : i) {
                    if (Terminales.contains(j))
                        C.add(j);

                }
            }
        }

        /*

        if (Terminales.contains(Regla) || !Regla.equals(EPSILON)) {
            C.add(Regla);
            return C;
        }

        for (String i : NoTerminales) {
            C.addAll(First(i));
        }

        if (C.contains(EPSILON) && Regla.length() == 1) {
            C.remove(EPSILON);
            C.addAll(First(Regla));
        }
        */
        return C;

    }

/*
    public void Follow(String SimbNT) {
        HashSet<String> C = new HashSet<>();

        if (SimbNT)
            C.add("$");

        for (String i :) {
            C.add(First());
        }

        if (C.contains(EPSILON)) {
            C.remove(EPSILON);
            C.add(Follow());
        }

        for (String j :) {
            if (SimbNT ==)
                // return

                C.add(Follow());
        }
        return C;
    }

    */
}
