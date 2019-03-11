package Clases;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class AnalizadorLexico {

    private HashMap<Integer, Vector<String>> Matriz;
    private Integer Inicio, Fin, PosActual;
    private char[] Cadena;
    private Stack<Integer> P;
    private Integer TOKEN;
    private int Memo[][];


    public void Lexico(String Delta, HashMap<Integer, Vector<String>> Matriz) {
        /* GUARDAMOS LOS DATOS RECIBIDOS */
        this.Matriz = Matriz;
        Memo = new int[Matriz.size()][Matriz.get(1).size()];
        /* CONVERTIMOS LA CADENA EN UN ARRAY, PARA ITERAR SOBRE ELLA */
        this.Cadena = Delta.toCharArray();
        P = new Stack<>();
        Inicio = PosActual = Fin = 0;
        TOKEN = -1;

        /* IMPRIMIMOS LA TABLA DEL AFN PARA VER CON LO QUE VAMOS A TRABAJAR */
        Matriz.forEach((k, v) -> System.out.println("S: " + k + ": Value: " + v));
        this.ConvertirMatriz();
    }

    public void Analizar() {
        Vector<Pair<String, Integer>> Resultado;
        Pair<String, Integer> P;

        while (Inicio != Fin) {

        }

    }

    public Integer GetToken() {
        //Metemos a la pila la posicion actual //0
        P.push(Inicio);

        //Sincronizamos las posiciones
        Fin = PosActual = Inicio;
        TOKEN = -1;

        return TOKEN;
    }

    public void RegresarToken() {
        /* SACO UN ELEMENTO DE LA PILA */
        int pos = 0;
        if (!P.empty()) {
            pos = P.pop();
        }
    }

    public void ConvertirMatriz() {

        for (String i : Matriz.get(-1)) {
            System.out.print(i + " ");

        }
        System.out.println();

        for (int i = 0; i < Matriz.size() - 1; i++) {
            for (int j = 0; j < Matriz.get(i).size(); j++) {
                Memo[i][j] = Integer.parseInt(Matriz.get(i).get(j));
                System.out.print("[" + Memo[i][j] + "]");
            }
            System.out.println();
        }
    }

}
