package classes;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class AnalizadorLexico {

    private HashMap<Integer, Vector<Integer>> Matriz;
    private Integer Inicio, Fin, PosActual;
    private String Delta;
    private Stack<Integer> P;
    private Integer Token;

    public void Lexico(String Delta, HashMap<Integer, Vector<Integer>> Matriz) {

        //Guardamos los datos preestablecidos
        this.Matriz = Matriz;
        this.Delta = Delta;

        P = new Stack<>();
        Inicio = 0;
        PosActual = 0;
        Fin = 0;
        Token = -1;

        //IMPRIMIMOS LA TABLA DEL AFN
        Matriz.forEach((k, v) -> System.out.println("\nS: " + k + ": Value: " + v));
    }

    public void Analizador() {

    }

    public Integer GetToken() {
        //Metemos a la pila la posicion actual //0
        P.push(Inicio);

        //Sincronizamos las posiciones
        Fin = PosActual = Inicio;
        Token = -1;

        return -1;
    }

    public void RegresarToken() {
        /* SACO UN ELEMENTO DE LA PILA */
        int pos = 0;
        if (!P.empty()) {
            pos = P.pop();
        }
    }

}
