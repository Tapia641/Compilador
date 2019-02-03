package classes;

import javafx.util.Pair;
import java.util.HashSet;

public class Estado {
    private int IdEstado = 0;
    private boolean EstadoAceptacion;

    private HashSet<Pair<Integer, Character>> Transiciones;

    public Estado() {
        this.EstadoAceptacion = false;
        this.IdEstado = IdEstado++;
    }

    public void pushTransicion(int ID, char S) {
        Pair<Integer, Character> P = new Pair<>(ID, S);
        Transiciones = new HashSet<>();
        Transiciones.add(P);
    }

    public void setIdEstado(int idEstado) {
        IdEstado = idEstado;
    }

    public void setEstadoAceptacion(boolean estadoAceptacion) {
        EstadoAceptacion = estadoAceptacion;
    }

    public void setTransiciones(HashSet<Pair<Integer, Character>> transiciones) {
        Transiciones = transiciones;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public boolean isEstadoAceptacion() {
        return EstadoAceptacion;
    }

    public HashSet<Pair<Integer, Character>> getTransiciones() {
        return Transiciones;
    }
}
