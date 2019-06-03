package Clases;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Pattern;

public class AnalizarTablaLL1 {
    //N+N*(N-N)$
    private String cadena;
    private Vector<Vector<String>> Tabla;
    private Stack<String> Pila;
    private Vector<String> Resultado;
    private HashSet<String> Terminales;
    private boolean R;
    private String Analisis;

    public AnalizarTablaLL1(String cadena, Vector<Vector<String>> tabla) {
        this.cadena = cadena;
        Tabla = tabla;
        Terminales = new HashSet<>();
        Resultado = new Vector<>();
        R = false;
        Analisis = "Pila: \tCadena: \tAcción\n";
    }

    public String getAnalisis() {
        return Analisis;
    }

    public void setAnalisis(String analisis) {
        Analisis = analisis;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Vector<Vector<String>> getTabla() {
        return Tabla;
    }

    public void setTabla(Vector<Vector<String>> tabla) {
        Tabla = tabla;
    }

    public void ObtenerTerminales() {
        for (int i = 1; i < Tabla.get(0).size(); i++) {
            Terminales.add(Tabla.get(0).get(i));
        }
    }

    public boolean getR(){
        return R;
    }

    public void Analiza() {
        Pila = new Stack<>();
        Pila.push("$");//Pesos
        Pila.push("E");//Estado Inicial
        Resultado = new Vector<>();
        Stack<Character> PilaCadena = new Stack<>();
        Stack<Character> PilaAux = new Stack<>();
        String accion = "";

        ObtenerTerminales();

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
        Analisis += Pila + "\t" + PilaCadena.toString() + "\t" + accion +"\n";

        /*COMENZAMOS CON EL ANÁLISIS*/
        while (!PilaCadena.isEmpty()) {

            /*SACAMOS UN ELEMENTO DE LA CADENA*/
            String Columna = String.valueOf(PilaCadena.pop());

            /*SACAMOS UN ELEMENTO DE LA PILA*/
            String Fila = Pila.pop();

            //System.err.println("Buscando: Fila:" + Fila + " Columna: " + Columna);

            /*BUSCAMOS EN LA TABLA*/

            /*OBTENEMOS LA FILA*/
            int f = -1;
            for (int i = 0; i < Tabla.size(); i++) {
                if (Tabla.get(i).get(0).equals(Fila)) {
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
            c = Tabla.get(0).indexOf(Columna);
            if (c == -1) {
                System.err.println("Error en obtener columna");
                break;
            }

            if (f != -1 && c != -1) {
                accion = Tabla.get(f).get(c);
                accion = accion.replaceAll("([{\\}])", "");
                String[] separador = accion.split("(?=[A-Z]|[A-Z]'|[\\,]|\\)|\\()");
                System.out.println("Encontramos: " + accion);
                if (accion.contains("ACEPTACION")){
                    R = true;
                    break;
                }

                if (accion.contains("EPSILON")) {
                    PilaCadena.push(Columna.charAt(0));
                    System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
                    Analisis += Pila + "\t" + PilaCadena.toString() + "\t" + accion +"\n";

                } else if (accion.contains("pop")) {
                    System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
                    Analisis += Pila + "\t" + PilaCadena.toString() + "\t" + accion +"\n";

                    //PilaCadena.pop();
                    //Pila.pop();

                } else if (!Terminales.contains(separador[0])) {
                    //Pila.push(Fila);

                    Stack<String> I = new Stack<>();
                    for (String i : separador) {
                        if (i.charAt(0) == 44) break;
                        else {
                            I.push(i);
                        }
                    }

                    /*LA INVERTIMOS*/
                    while (!I.isEmpty()) {
                        Pila.add(I.pop());
                    }

                    //Pila.push(separador[0]);
                    PilaCadena.push(Columna.charAt(0));

                    System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
                    Analisis += Pila + "\t" + PilaCadena.toString() + "\t" + accion +"\n";

                } else {
                    //Pila.push(Fila);
                    Stack<String> I = new Stack<>();
                    for (String i : separador) {
                        if (i.charAt(0) == 44) break;
                        else {
                            I.push(i);
                        }
                    }


                    /*LA INVERTIMOS*/
                    while (!I.isEmpty()) {
                        Pila.add(I.pop());
                    }

                    //Pila.push(separador[0]);
                    PilaCadena.push(Columna.charAt(0));
                    System.out.println("Pila: " + Pila.toString() + " Cadena " + PilaCadena.toString() + " Accion " + accion);
                    Analisis += Pila + "\t" + PilaCadena.toString() + "\t" + accion +"\n";

                }
            }

            //
        }


    }
}