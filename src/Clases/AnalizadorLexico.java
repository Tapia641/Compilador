package Clases;

import com.sun.java.accessibility.util.AWTEventMonitor;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private Stack<Integer> Pila;
    private int TOKEN;
    private int[][] Memo;
    private Vector<Character> Alfabeto;
    private Vector<Pair<String, Integer>> Resultado;


    public void Lexico(String Delta, HashMap<Integer, Vector<String>> Matriz) {
        /* GUARDAMOS LOS DATOS RECIBIDOS */
        Alfabeto = new Stack<>();
        this.Matriz = Matriz;
        Memo = new int[Matriz.size()][Matriz.get(1).size()];

        /* CONVERTIMOS LA CADENA EN UN ARRAY, PARA ITERAR SOBRE ELLA */
        this.Delta = Delta.toUpperCase();
        this.Cadena = this.Delta.toCharArray();

        Cola = new ArrayDeque<>();
        Pila = new Stack<>();
        Resultado = new Stack<>();

        Inicio = PosActual = Fin = 0;
        TOKEN = -1;

        /* IMPRIMIMOS LA TABLA DEL AFN PARA VER CON LO QUE VAMOS A TRABAJAR */
        Matriz.forEach((k, v) -> System.out.println("S: " + k + ": Value: " + v));
        this.ConvertirMatriz();
        this.Analizar();
    }

    public void Analizar() {
        int IndiceColumna = 0, IndiceFila = 0, PrevioToken = 0;
        String Lexema = "", LexemaError = "";

        /* COMENZAMOS EL ANÁLISIS */
        while (Fin != Delta.length()) {

            System.out.println("Analizando " + Cadena[Fin]);


            /* SI EL CARÁCTER ESTA EN EL ALFABETO */
            if (Alfabeto.contains(Cadena[Fin])) {

                IndiceColumna = Alfabeto.indexOf(Cadena[Fin]);

                if (!LexemaError.equals("")) {
                    System.out.println("Se agrega Lexema Error a los datos");
                    Resultado.add(new Pair<>(LexemaError, -1));
                    LexemaError = "";
                    Cola.add(-1);
                    Pila.push(-1);
                }


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

                    if (TOKEN == -1) {
                        LexemaError += Cadena[Fin];
                        //System.out.println("Se agregó LexemaError " + LexemaError + " con tok = " + TOKEN);
                        //Resultado.add(new Pair<>(Lexema, TOKEN));
                        Fin++;
                    } else {
                        System.out.println("Se agregó " + Lexema + " con tok = " + TOKEN);
                        Cola.add(TOKEN);
                        Pila.push(TOKEN);
                        Resultado.add(new Pair<>(Lexema, TOKEN));
                        Lexema = "";
                        Fin = Fin - 1;
                        IndiceFila = 0;
                        PrevioToken = -1;
                        Inicio = Fin;
                        Fin++;
                    }


                }
            } else {
                LexemaError += Cadena[Fin];
                System.out.println("Lexema error " + LexemaError);
                Fin++;
            }

            /* SI TERMINA DE ANALIZAR LA CADENA Y AGREGAMOS LO QE SE OBTUVO */
            if (Fin == Delta.length()) {

                Resultado.add(new Pair<>(Lexema, TOKEN));

                Cola.add(TOKEN);
                Pila.push(TOKEN);

                Lexema = "";
                Fin = Fin - 1;
                IndiceFila = 0;
                PrevioToken = -1;
                Inicio = Fin;
                Fin++;

                if (!LexemaError.equals("")) {
                    System.out.println("Se agrega Lexema Error a los datos");
                    Resultado.add(new Pair<>(LexemaError, -1));
                    Cola.add(-1);
                    Pila.push(-1);
                    LexemaError = "";
                }

            }
        }

        //Agregamos el token de fin
        Pila.push(0);
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

    public Queue<Integer> getCola() {
        return Cola;
    }

    public Stack<Integer> getPila() {
        return Pila;
    }

    public Vector<Pair<String, Integer>> getTablaLexema() {
        return Resultado;
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

    public String getStringTabla(){
        /*CADENA QUE OBTIENE TODOS LOS DATOS DEL VECTOR DE PAIR*/
        String Total = "";

        for (Pair<String, Integer> X: Resultado){
            Total += "Lexema: " + X.getKey() + " \tToken: " + X.getValue() + "\n";
        }

        return Total;
    }

    public void ConvertirMatriz() {
        /*PARA EL JTABLE*/
        DefaultTableModel datos = new DefaultTableModel();


        /* OBTENEMOS LAS POSICIONES DE LAS COLUMNAS DEL ALFABETO */
        char[] Temp;
        for (String i : Matriz.get(-1)) {
            Temp = i.toCharArray();
            Alfabeto.add(Temp[0]);
        }

        /* LA CONVERTIMOS A UNA MEMORIA MÁS EFICIENTE DE ACCEDER */
        //datos.addColumn("Estado");
        for (char i : this.Alfabeto)
            datos.addColumn(i);

        for (int i = 0; i < Matriz.size() - 1; i++) {
            //System.out.print("S" + i);
            for (int j = 0; j < Matriz.get(i).size(); j++) {
                Memo[i][j] = Integer.parseInt(Matriz.get(i).get(j));
                //System.out.println(Matriz.get(i));
                //datos.setDataVector(Matriz.get(i),Alfabeto);
                //System.out.print("[" + Memo[i][j] + "]");
            }
            datos.addRow(Matriz.get(i));
            System.out.println();
        }

        JTable tabla = new JTable(datos);

        tabla.setPreferredScrollableViewportSize(new Dimension(450, 200));
        JScrollPane scrollPane = new JScrollPane(tabla);
        JFrame jf = new JFrame();
        jf.add(scrollPane);
        jf.setTitle("Tabla de transiciones");
        jf.setSize(Matriz.get(0).size()*50,Matriz.size()*19);
        jf.setVisible(false);
    }

}
