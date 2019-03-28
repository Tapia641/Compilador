package Clases;

import javafx.util.Pair;

import java.util.*;

public class AnalizadorLexico {

    public class Edo {
        private int IniLexema, FinLexema, Token, IndiceActual;
        private Estado EdoAcept;


        public int getIniLexema() {
            return IniLexema;
        }

        public void setIniLexema(int iniLexema) {
            IniLexema = iniLexema;
        }

        public int getFinLexema() {
            return FinLexema;
        }

        public void setFinLexema(int finLexema) {
            FinLexema = finLexema;
        }

        public int getToken() {
            return Token;
        }

        public void setToken(int token) {
            Token = token;
        }

        public int getIndiceActual() {
            return IndiceActual;
        }

        public void setIndiceActual(int indiceActual) {
            IndiceActual = indiceActual;
        }

        public Estado getEdoAcept() {
            return EdoAcept;
        }

        public void setEdoAcept(Estado edoAcept) {
            EdoAcept = edoAcept;
        }


    }

    public Edo GetEdo() {
        return Estado;

    }

    public void SetEdo(Edo aux) {
        Estado.EdoAcept = aux.EdoAcept;
        Estado.FinLexema = aux.FinLexema;
        Estado.IndiceActual = aux.IndiceActual;
        Estado.IniLexema = aux.IniLexema;
        Estado.Token = aux.Token;
    }

    private Edo Estado = new Edo();
    private HashMap<Integer, Vector<String>> Matriz;
    private Integer Inicio, Fin, PosActual;
    private char[] Cadena;
    private String Delta;
    private Queue<Integer> Cola;
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

        Cola = new ArrayDeque<>();
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
                    System.out.println("No hay transicion");
                    TOKEN = PrevioToken;
                    System.out.println("Se agregó " + Lexema + " con tok = " + TOKEN);

                    Cola.add(TOKEN);

                    if (TOKEN == -1) {
                        System.err.println("La cadena ingresada no pertenece al automata");
                        //break;
                    }
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
                //System.exit(-1);
                System.err.println("La cadena ingresada no pertenece al automata");
                break;
            }

            /* SI TERMINA DE ANALIZAR LA CADENA Y AGREGAMOS LO QE SE OBTUVO */
            if (Fin == Delta.length()) {
                Resultado.add(new Pair<>(Lexema, TOKEN));

                Cola.add(TOKEN);

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
            System.out.println("Lexema: [" + P.getKey() + "] TOKEN: " + P.getValue());

        }

    }

    public Integer GetToken() {
        if (Cola.size() != 0) {
            return Cola.remove();

        }
        return -1;
        //Metemos a la pila la posicion actual //0
        //Cola.add(Inicio);

        //Sincronizamos las posiciones
        //Fin = PosActual = Inicio;
        //TOKEN = -1;

        //return TOKEN;
    }

    public void RegresarToken(int token) {
        /* SACO UN ELEMENTO DE LA PILA */
        //int pos = 0;
        //if (Cola.size() == 0) {
        //pos = Cola.remove();
        //  Cola.remove();
        //}
        Cola.add(token);
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
