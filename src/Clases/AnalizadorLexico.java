package Clases;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class AnalizadorLexico {

    private HashMap<Integer, Vector<String>> Matriz;
    private Integer Inicio, Fin, PosActual;
    private char[] Cadena;
    private String Delta;
    private Stack<Integer> P;
    private int TOKEN;
    private int Memo[][];
    private Vector<Character> Alfabeto;


    public void Lexico(String Delta, HashMap<Integer, Vector<String>> Matriz) {
        /* GUARDAMOS LOS DATOS RECIBIDOS */
        Alfabeto = new Stack<>();
        this.Matriz = Matriz;
        Memo = new int[Matriz.size()][Matriz.get(1).size()];
        /* CONVERTIMOS LA CADENA EN UN ARRAY, PARA ITERAR SOBRE ELLA */
        this.Delta = Delta.toUpperCase();
        this.Cadena = this.Delta.toCharArray();
        P = new Stack<>();
        Inicio = PosActual = Fin = 0;
        TOKEN = -1;

        /* IMPRIMIMOS LA TABLA DEL AFN PARA VER CON LO QUE VAMOS A TRABAJAR */
        Matriz.forEach((k, v) -> System.out.println("S: " + k + ": Value: " + v));
        this.ConvertirMatriz();
        this.Analizar();
    }

    public void Analizar() {
        Vector<Pair<String, Integer>> Resultado = new Stack<>();
        int IndiceColumna = 0, IndiceFila = 0, PrevioToken = 0;
        String Lexema = "";

        /* COMENZAMOS EL ANÁLISIS */
        while (Fin != Delta.length()) {

            System.out.println("Analizando " + Cadena[Fin]);

            /* SI EL CARÁCTER ESTA EN EL ALFABETO */
            if (Alfabeto.contains(Cadena[Fin])) {

                IndiceColumna = Alfabeto.indexOf(Cadena[Fin]);

                /* SI HAY TRANSICIÓN CON ESE CARÁCTER */
                if (Memo[IndiceFila][IndiceColumna] != -1) {

                    /* ESTADO AL QUE PASA */
                    IndiceFila = Memo[IndiceFila][IndiceColumna];

                    /* CONCATENAMOS A NUESTRO LEXEMA */
                    Lexema += Cadena[Fin];
                    System.out.println("Lexema acomulado " + Lexema);

                    /* AVANZAMOS EN LA CADENA */
                    Fin++;


                    /* PREGUTAMOS SI TIENE EDO ACEPT */
                    TOKEN = Memo[IndiceFila][Matriz.get(1).size() - 1];

                    if (TOKEN != -1) PrevioToken = TOKEN;

                } else {// CUANDO NO HAY TRANSICIÓN
                    //System.out.println("No hay trans");
                    TOKEN = PrevioToken;
                    System.out.println("Se agregó " + Lexema + " con tok = " + TOKEN);
                    Resultado.add(new Pair<>(Lexema, TOKEN));

                    Lexema = "";
                    Fin = Fin - 1;
                    IndiceFila = 0;
                    PrevioToken = -1;
                    Inicio = Fin;
                    Fin++;
                }
            } else {
                /* SOLO FINALIZAMOS */
                System.exit(-1);
                System.err.println("La cadena ingresada no pertenece al automata");
            }

            /* SI TERMINA DE ANALIZAR LA CADENA Y AGREGAMOS LO QE SE OBTUVO */
            if (Fin == Delta.length()) {
                Resultado.add(new Pair<>(Lexema, TOKEN));

                Lexema = "";
                Fin = Fin - 1;
                IndiceFila = 0;
                PrevioToken = -1;
                Inicio = Fin;
                Fin++;
            }
        }

        /* IMPRIMIMOS LOS RESULTADOS */
        Pair<String, Integer> P;
        Iterator it = Resultado.iterator();
        System.out.println("\n\nResultados del Analizador Lexico:\n");
        while (it.hasNext()) {
            P = (Pair<String, Integer>) it.next();
            System.out.println(P.getKey() + " : " + P.getValue());
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

        /* OBTENEMOS LAS POSICIONES DE LAS COLUMNAS DEL ALFABETO */
        char[] Temp;
        for (String i : Matriz.get(-1)) {
            Temp = i.toCharArray();
            Alfabeto.add(Temp[0]);
        }

        /* LA CONVERTIMOS A UNA MEMORIA MÁS EFICIENTE DE ACCEDER */

        for (int i = 0; i < Matriz.size() - 1; i++) {
            for (int j = 0; j < Matriz.get(i).size(); j++) {
                Memo[i][j] = Integer.parseInt(Matriz.get(i).get(j));
                System.out.print("[" + Memo[i][j] + "]");
            }
            System.out.println();
        }
    }


}
