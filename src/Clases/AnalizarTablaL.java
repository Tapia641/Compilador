package Clases;

import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.runtime.regexp.RegExp;

import java.io.*;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Pattern;

public class AnalizarTablaL {
    //(N+N)*N$

    private String cadena;
    private Vector<Vector<String>> Tabla;
    private Stack<String> Pila;
    private Vector<String> Resultado;
    private HashSet<String> Terminales;
    private boolean R;
    private String Analisis;
    private Vector<String> Reglas;
    private Vector<String> TablaResultado;

    public Vector<String> getTablaResultado() {
        return TablaResultado;
    }

    public void setReglas(String archivo) throws IOException {
        Reglas = new Vector<>();
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            Reglas.add(cadena);
            System.out.println(cadena);
        }
        b.close();
    }

    public boolean getR() {
        return R;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena.toLowerCase();
    }

    public AnalizarTablaL(String cadena, Vector<Vector<String>> tabla) {
        this.cadena = cadena.toLowerCase();
        Tabla = tabla;
        Terminales = new HashSet<>();
        Resultado = new Vector<>();
        R = false;
        TablaResultado = new Vector<>();
        Analisis = "Pila \tCadena \tAcción\n";
        TablaResultado.add(Analisis);
    }

    public String getAnalisis() {
        return Analisis;
    }

    public void Analiza() {

        Pila = new Stack<>();
        Pila.push("0");//0
        Resultado = new Vector<>();
        Stack<Character> PilaCadena = new Stack<>();
        Stack<Character> PilaAux = new Stack<>();
        String accion = "";

        /*SEPARAMOS LA CADENA POR CARÁCTERES*/
        char[] cadenaChar = cadena.toCharArray();
        for (char i : cadenaChar) {
            PilaAux.push(i);
        }

        /*LA INVERTIMOS*/
        while (!PilaAux.isEmpty()) {
            PilaCadena.add(PilaAux.pop());
        }

        System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
        Analisis = Pila + " \t" + PilaCadena.toString() + " \t" + accion + "\n";
        TablaResultado.add(Analisis);


        /*COMENZAMOS CON EL ANÁLISIS*/
        while (!PilaCadena.isEmpty()) {

            /*SACAMOS UN ELEMENTO DE LA CADENA*/
            String Columna = String.valueOf(PilaCadena.pop());

            /*SACAMOS UN ELEMENTO DE LA PILA*/
            String Fila = Pila.pop();

            //System.out.println("Buscando: Fila:" + Fila + " Columna: " + Columna);

            /*BUSCAMOS EN LA TABLA*/

            /*OBTENEMOS LA FILA*/
            int f = -1;
            for (int i = 0; i < Tabla.size(); i++) {
                if (Tabla.get(i).get(Tabla.get(0).size() - 1).equals(Fila)) {
                    f = i;
                    break;
                }
            }
            if (f == -1) {
                System.err.println("Error en obtener fila");
                break;
            }

            /*OBTENEMOS LA COLUMNA*/
            int c = -1;

            System.out.println("Buscando en columna: " + Columna);
            for (int i = 0; i < Tabla.get(0).size(); i++) {
                //System.out.println(Tabla.get(0).get(i));
                if (Tabla.get(0).get(i).contains(Columna)) {
                    c = i;
                    break;
                }
            }

            if (c == -1) {
                System.err.println("Error en obtener columna");
                break;
            }

            if (f != -1 && c != -1) {

                accion = Tabla.get(f).get(c);
                accion = accion.replaceAll("([{\\}])", "");
                accion = accion.replaceAll("(['])", "");
                String[] separador = accion.split("(?=[A-Z]|[A-Z]'|[\\,]|\\)|\\(|\\')");
                System.out.println("Encontramos: " + accion);


                if (accion.contains("ACEPTACION")) {
                    R = true;
                    break;
                }


                if (Character.isLetter(separador[0].charAt(0)) && !separador[0].contains("r")) {
                    String[] Aux = separador[0].split("[a-z]");
                    //System.out.println("Acciones con d " + Aux[1]);
                    Pila.push(Fila);
                    Pila.push(Columna);
                    Pila.push(Aux[1]);
                    //Pila.push(separador[0]);
                    //PilaCadena.push(Columna.charAt(0));

                    System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
                    Analisis = Pila + " \t" + PilaCadena.toString() + " \t" + accion + "\n";
                    TablaResultado.add(Analisis);

                } else {
                    /*OBTENEMOS LA REGLA DEL LADO DERECHO PARA SACAR SU LONGITUD*/
                    PilaCadena.push(Columna.charAt(0));
                    String Element = Pila.pop();
                    String[] reduccion = {};

                    System.out.println("Buscando el elemento: " + Element + " de lado derecho");
                    for (int i = 1; i < Reglas.size(); i++) {
                        reduccion = Reglas.get(i).split(Pattern.quote("->"));
                        if (reduccion[1].equals(Element) || accion.contains(String.valueOf(i))) {
                            reduccion = Reglas.get(i).split(Pattern.quote("->"));
                            break;
                        }
                    }
                    System.out.println("se encontro en esta reduccion " + reduccion[1]);
                    reduccion[1] = reduccion[1].replaceAll("([{\\}])", "");
                    //reduccion[1] = reduccion[1].replaceAll("(['])", "");
                    int longitud = reduccion[1].split("(?=[A-Z]|[A-Z]'|[!-/]|[:-@]|[\\[-`]|[{-~])").length;

                    /*REMOVEMOS DE LA PILA ESA LONGITUD*/
                    System.out.println("Aqui removemos: " + 2 * longitud + " Para: " + reduccion[1]);
                    int rango = 2 * longitud;

                    while (rango > 2) {
                        Pila.pop();
                        rango--;
                    }

                    Pila.push(reduccion[0]);
                    String firstElement = Pila.pop();
                    String secondElement = Pila.peek();


                    /*BUSCANDO COLUMNA CON FIRSTELEMENT*/
                    int newc = -1, newf = -1;

                    System.out.println("Buscando en columna parte 2: " + firstElement);
                    for (int i = 0; i < Tabla.get(0).size(); i++) {
                        //System.out.println(Tabla.get(0).get(i));
                        if (Tabla.get(0).get(i).contains(firstElement)) {
                            newc = i;
                            break;
                        }
                    }
                    if (newc == -1) {
                        System.err.println("Error al obtener la columna parte 2");
                        break;
                    }

                    /*BUSCANDO FILA CON SECONDELEMENT*/
                    for (int i = 0; i < Tabla.size(); i++) {
                        if (Tabla.get(i).get(Tabla.get(0).size() - 1).equals(secondElement)) {
                            newf = i;
                            break;
                        }
                    }
                    if (newf == -1) {
                        System.err.println("Error en obtener fila parte 2");
                        break;
                    }

                    if (newf != -1 && newc != -1) {
                        Pila.push(firstElement);
                        String newElement = Tabla.get(newf).get(newc);
                        System.out.println("Encontramos en parte 2: " + newElement);
                        newElement = newElement.replaceAll("([\\{]|[\\}])", "");
                        Pila.push(newElement);
                    }


                    System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
                    Analisis = Pila + " \t" + PilaCadena.toString() + " \t" + accion + "\n";
                    TablaResultado.add(Analisis);

                }
            }

            //
        }

    }

}
