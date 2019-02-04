package classes;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Iterator;

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

    public HashSet<Pair<Character, Estado>> getTransiciones() {
        return Transiciones;
    }

    public void imprimeTransiciones() {
        Iterator it = Transiciones.iterator();
        Pair<Character, Estado> P;
        while (it.hasNext()) {
            P = (Pair) it.next();
            System.out.print(P.getKey() + " -> " + P.getValue().getID() + "\n");
        }
    }
}
