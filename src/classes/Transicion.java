package classes;

import javafx.util.Pair;

import java.util.HashSet;

public class Transicion {

    private HashSet<Pair<Character, Estado>> Transiciones;

    public Transicion() {
        Transiciones = new HashSet<>();
    }

    public void pushTransicion(char S, Estado E) {
        Pair<Character, Estado> P = new Pair<>(S, E);
        Transiciones = new HashSet<>();
        Transiciones.add(P);
    }
}
