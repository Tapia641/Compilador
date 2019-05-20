package Clases;

import Clases.AnalizadorSintactico.GramaticaDeGramaticas;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

public class LL1 {

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

    public LL1(String archivo) throws IOException {

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


        } else {
            System.err.println("No se pudo inicializar la Tabla LL1 por el AS");
        }

    }

    public HashSet First(Vector<String> ListaReglas) {
        HashSet<String> C = new HashSet<>();
        for (int i = 1; i < ListaReglas.size(); i++) {

            /*PREGUNTAMOS SI ES UN TERMINAL*/
            if (Terminales.contains(ListaReglas.get(i))) {

                /*SE CALCULA EL FOLLOW DEL LADO IZQUIEDO*/
                if (ListaReglas.get(i).equals(" ")) {
                    C.addAll(Follow(ListaReglas.get(0)));
                    return C;
                }

                C.add(ListaReglas.get(i));
                return C;

            } else {

                /*BUSCAMOS EL ELEMENTO DEL LADO IZQUIEDO*/
                for (Vector<String> V : Tabla) {
                    if (V.get(0).equals(ListaReglas.get(i)) && !Analizado.contains(V)) {
                        Analizado.add(V);
                        C.addAll(First(V));
                    }
                }

                return C;
            }
        }

        return C;

    }

    public HashSet<String> Follow(String SimbNT) {
        HashSet<String> C = new HashSet<>();

        /*AGREGAMOS A PESOS*/
        if (NoTerminales.contains(SimbNT)) {
            C.add("$");
        }

        /*BUSCAMOS AL SIMBNT DEL LADO DERECHO*/
        for (Vector<String> S : Tabla) {
            for (int i = 1; i < S.size(); i++) {

                if (S.get(i).equals(SimbNT)) {

                    /*SI ES EL MISMO DEL LADO IZQUIEDO OMITIMOS PARA NO CAER EN LA RECURSIVIDAD*/
                    if (S.get(0).equals(SimbNT)) {
                        //No hacer nada
                    } else {
                        C.addAll(FollowReverse(S.get(0)));
                        return C;
                    }
                }
            }
        }


        return C;
    }

    public HashSet<String> FollowReverse(String S) {
        HashSet<String> C = new HashSet<>();
        Vector<String> Aux = new Vector<>();
        boolean activado = false;
        for (Vector<String> V : Tabla) {
            for (int i = 1; i < V.size(); i++) {
                if (activado) {
                    Aux.add(V.get(i));
                }
                if (V.get(i).equals(S)) {
                    activado = true;
                    Aux.add(V.get(i));
                }
            }
            if (!Aux.isEmpty()) {
                break;
            }
        }
        C.addAll(First(Aux));
        return C;
    }

    public void ConstruirTabla() {

        /*ELEMENTOS NECESARIOS PARA LA TABLA*/
        Vector<Vector<String>> Matriz = new Vector<>();
        Vector<String> AuxTerminales = new Vector<>(), AuxNoTerminales = new Vector<>();
        String[][] Matrix = new String[NoTerminales.size() + Terminales.size() + 1][Terminales.size() + 1];

        for (String n : Terminales)
            AuxTerminales.add(n);
        for (String x : NoTerminales)
            AuxNoTerminales.add(x);

        /*LA INICIALIZAMOS CON TODOS EN ERROR*/
        int pos = 0, cont = 0;
        boolean creado = false;
        for (int i = 0; i < NoTerminales.size() + Terminales.size() + 1; i++) {
            Vector<String> Aux = new Vector<>();
            for (int j = 0; j < Terminales.size() + 1; j++) {
                Matrix[i][j] = "Error";

                if (i == 0) {
                    AuxTerminales.add("$");
                    Matriz.add(AuxTerminales);
                    break;
                } else {
                    if (j == 0) {
                        if (pos < NoTerminales.size()) {
                            Aux.add(AuxNoTerminales.get(pos++));
                        } else {
                            if (AuxTerminales.get(cont).equals(" ")) {
                                Aux.add("$");
                                cont++;
                            } else {
                                Aux.add(AuxTerminales.get(cont++));
                            }
                        }
                    }

                    Aux.add("Error");
                }
            }

            if (!Aux.isEmpty()) {
                Matriz.add(Aux);
            }
        }

        /*CALCULAMOS EL LL1*/
        int i = 1;
        Analizado = new HashSet<>();
        Vector<Pair<HashSet<String>, Integer>> Datos = new Vector<>();
        for (Vector<String> V : Tabla) {
            Datos.add(new Pair(First(V), i++));
            //System.out.println(Datos.get(i-2));
            Analizado.clear();
        }

        /*OBTENEMOS INDICES*/
        int col = 0, fila = 0;
        Vector<String> Aux = new Vector<>();
        Vector<Pair<String, String>> Final = new Vector<>();
        for (Pair<HashSet<String>, Integer> P : Datos) {

            /*TRATAMOS AL CONJUNTO COMO UN VECTOR*/
            for (String cad : P.getKey()) {
                Aux.add(cad);
            }

            /*PONEMOS LOS VALORES*/

            for (int j = 0; j < Aux.size(); j++) {
                col = Matriz.get(0).indexOf(Aux.get(j));
                for (Vector<String> V : Matriz) {
                    String regla = "";
                    for (int x = 1; x < Tabla.get(P.getValue() - 1).size(); x++) {
                        regla += Tabla.get(P.getValue() - 1).get(x);
                    }
                    if (regla.equals(" ")) {
                        regla += "EPSILON";
                    }
                    String u = "";
                    u += regla + "," + P.getValue();
                    //System.out.println("En: " + Tabla.get(P.getValue() - 1).get(0) + " col: " + col + " poner " + regla + "," + P.getValue());
                    int x = 0;
                    for (int k = 0; k < Matriz.size(); k++) {
                        if (Matriz.get(k).get(0).equals(Tabla.get(P.getValue() - 1).get(0))) {
                            x = k;
                            break;
                        }
                    }
                    //System.err.println(x);
                    Matriz.get(x).set(col, u);

                    //Matrix[fila][col] = "1111111111";
                    //System.out.println(fila + ", " + col);
                    //Matriz.get(p).set(col,u);
                    Final.add(new Pair(Tabla.get(P.getValue() - 1).get(0), u));
                    break;
                }
            }

            Aux = new Vector<>();
        }

        /*PONEMOS LOS POP*/
        for (int j = 0; j < Matriz.size(); j++) {
            for (int k = 0; k < Matriz.get(0).size(); k++) {
                if (Matriz.get(j).get(0).equals(Matriz.get(0).get(k))) {
                    Matriz.get(j).set(k, "Pop");
                }
                //System.out.println(Matriz.get(j).get(0).equals(Matriz.get(0).get(k)));
            }

        }

        /*IMPRIMIMOS*/
        for (Vector<String> c : Matriz) {
            System.out.println(c);
        }

    }

    public HashSet<String> getTerminales() {
        return Terminales;
    }


    public HashSet<String> getNoTerminales() {
        return NoTerminales;
    }

    public String getGramatica() {
        return Gramatica;
    }
}
