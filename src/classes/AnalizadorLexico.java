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

        //IMPRIMIMOS LA TABLA DEL AFN
        Matriz.forEach((k, v) -> System.out.println("\nS: " + k + ": Value: " + v));
    }

    public void Analizador() {

    }

}
