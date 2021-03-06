package Clases;

import javafx.util.Pair;

import java.util.HashSet;

public class Transicion {

    /* CONJUNTO DE TRANSICIONES */
    private HashSet<Pair<Character, Estado>> Transiciones;

    public Transicion() {
        Transiciones = new HashSet<>();
    }

    public void pushTransicion(char S, Estado E) {
        Pair<Character, Estado> P = new Pair<>(S, E);
        Transiciones.add(P);
    }

    public HashSet<Pair<Character, Estado>> getTransiciones() {
        return Transiciones;
    }

    public void clearTransiciones() {
        this.Transiciones.clear();
    }

    public void uneTransiciones(Transicion t) {
        this.Transiciones.addAll(t.Transiciones);
    }

}
