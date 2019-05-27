package Clases;

import Clases.AnalizadorSintactico.GramaticaDeGramaticas;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class LR0AUX {

    private char EPSILON = '&';
    private Vector<Vector<String>> Tabla;
    private Integer Filas, Columnas;
    private HashSet<String> NoTerminales;
    private HashSet<String> Terminales;
    private Vector<String> ConjuntoReglas;
    private static Vector<String> M;
    private static String[][] Memo;
    private HashSet<Vector<String>> Analizado;
    private String Gramatica;

    public LR0AUX(String archivo) throws Exception {

        /*INICIALIZAMOS LO QUE VAMOS A UTILIZAR*/
        Terminales = new HashSet<>();
        NoTerminales = new HashSet<>();
        Tabla = new Vector<>();
        String cadena, total = "";
        BufferedReader br = new BufferedReader(new FileReader(new File(archivo).getAbsolutePath()));

        /*DAMOS LECTURA AL ARCHVIO*/
        while ((cadena = br.readLine()) != null) {
            total += cadena;
        }

        /*REALIZAMOS EL ANÁLISIS SINTÁCTICO PARA PROCEDER*/
        AFD afd = new AFD();
        GramaticaDeGramaticas GG = new GramaticaDeGramaticas();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
        try {
            //IMPORTAMOS HASMAP DE GRAMATICA DE GRAMATICAS PREVIAMENTE REALIZADA
            afd.LeerObject("GramaticaDeGramaticas");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        AnalizarLexicamente.Lexico(total, afd.getMatriz());
        GG.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());

        /*PROCEDER A ARMAR LA TABLA LL1*/
        if (GG.Exito()) {

            /*LO QUE VAMOS A OCUPAR PARA OPERAR SOBRE EL STRING*/
            Vector<Pair<String, Integer>> TablaLexema = AnalizarLexicamente.getTablaLexema();
            HashSet<String> LadoIzquierdo = new HashSet<>();
            Vector<String> S = new Vector<>(), Aux = new Vector<>();

            for (Pair<String, Integer> P : TablaLexema) {

                if (!P.getKey().equals("->")) {
                    if (!P.getKey().equals(";")) {
                        if (S.isEmpty()) {
                            NoTerminales.add(P.getKey());
                        }

                        if (P.getKey().equals("|")) {
                            Aux.add(S.get(0));
                            Tabla.add(S);
                            S = new Vector<>();
                            S.addAll(Aux);
                            Aux = new Vector<>();
                        } else {
                            S.add(P.getKey());
                        }

                    } else {
                        Tabla.add(S);
                        S = new Vector<>();
                    }
                }
            }


            for (Pair<String, Integer> P : TablaLexema) {
                if (!P.getKey().equals("->") && !P.getKey().equals("|")) {
                    if (!P.getKey().equals(";")) {
                        if (!NoTerminales.contains(P.getKey())) {
                            Terminales.add(P.getKey());
                        }
                    }
                }
            }

            System.out.println("No terminales " + NoTerminales);
            System.out.println("Terminales " + Terminales);
            System.out.println(Tabla);
            this.Gramatica = total;
        }
    }

    public HashSet<Estado> CerraduraEpsilon(Estado E) { //Funciona

        /* CONJUNTO DE ESTADOS */
        HashSet<Estado> C = new HashSet<>();

        /* PILA PARA ALAMACENAR A LOS QUE ALCANZA CON EPSILON */
        Stack<Estado> S = new Stack<>();

        /* INGRESAMOS EL PRIMER ESTADO A ANALIZAR */
        S.add(E);

        Estado EstadoPrueba;

        while (!S.empty()) {

            /* TOMAMOS EL SIGUIENTE ELEMENTO */
            EstadoPrueba = S.pop();

            if (!C.contains(EstadoPrueba)) {

                /* AÑADIMOS EL ESTADO AL QUE SE LLEGÓ */
                C.add(EstadoPrueba);

                /* PREGUNTAMOS SI TIENE TRANSICIONES EPSILON */
                HashSet<Pair<Character, Estado>> t = EstadoPrueba.Transiciones.getTransiciones();
                Iterator it = t.iterator();
                Pair<Character, Estado> P;

                while (it.hasNext()) {

                    P = (Pair) it.next();

                    /* SI TIENE UNA TRANSICION EPSILON */
                    if (P.getKey() == EPSILON) {

                        /* AÑADIMOS EL ESTADO AL QUE ALCANZA */
                        S.add(P.getValue());
                    }
                }
            }
        }

        /* AÑADIMOS EL ESTADO CON EL QUE SE INICIÓ */
        C.add(E);

        return C;
    }

    public HashSet<Estado> Mover(Estado E, char S) {

        /* CONJUNTO DE ESTADOS A LOS CUALES HAY TRANSICIÓN CON E -> S */
        HashSet<Estado> C = new HashSet<>();

        /* ITERAMOS SOBRE LAS TRANSICIONES DE E */
        for (Pair<Character, Estado> P : E.getTransiciones()) {

            /* SI HAY UNA TRANSICIÓN A S */
            if (P.getKey().equals(S)) {

                /* AÑADIMOS EL ESTADO */
                C.add(P.getValue());
            }
        }

        return C;
    }

    public HashSet<Estado> Ir_A(HashSet<Estado> conjuntoEstados, char S) {

        /*IR_A({},S) : CerraduraEpsilon(Mover({i},S))*/

        /* CREAMOS LOS CONJUNTOS DE ALMACENAMIENTO */
        HashSet<Estado> C = new HashSet<>();
        HashSet<Estado> M = new HashSet<>();

        /* CALCULAMOS EL MOVER({i},S)*/
        int j = 0;
        for (Estado i : conjuntoEstados) {
            System.out.println("Mover(" + i.getID() + ", " + S + ")");
            M.addAll(Mover(i, S));
        }

        /* CALCULAMOS LA CERRADURAEPSILON(MOVER({i},S)) */
        for (Estado i : M) {
            System.out.println("C_E(" + i.getID() + ")");
            C.addAll(CerraduraEpsilon(i));
        }

        return C;
    }

}
