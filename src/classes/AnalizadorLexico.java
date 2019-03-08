package classes;

import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class AnalizadorLexico {

    private HashMap<Integer, Vector<Integer>> Matriz;
    private Integer Inicio, Fin, PosActual;
    private String Delta;
    private Stack<Integer> P;
    private Integer TOKEN;

    public void Lexico(String Delta, HashMap<Integer, Vector<Integer>> Matriz) {

        /* GUARDAMOS LOS DATOS RECIBIDOS */
        this.Matriz = Matriz;
        this.Delta = Delta;

        P = new Stack<>();
        Inicio = PosActual = Fin = 0;
        TOKEN = -1;

        /* IMPRIMIMOS LA TABLA DEL AFN PARA VER CON LO QUE VAMOS A TRABAJAR */
        Matriz.forEach((k, v) -> System.out.println("\nS: " + k + ": Value: " + v));
    }

    public void Analizador() {
        //CODE
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

}
