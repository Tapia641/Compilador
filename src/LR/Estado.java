package LR;

import java.util.HashSet;

public class Estado {
    private static int sig = 0;
    private final int idEdo;
    private boolean aceptacion;
    private final HashSet<Transicion> conjuntoTransiciones = new HashSet<>();
    private int Token = -1;

    public Estado() {
        this.aceptacion = false;
        this.idEdo = sig;
        sig++;
    }

    public Estado(int i) {
        this.idEdo = i;
    }

    public void setToken(int t) {
        this.Token = t;
    }

    public void setEdoAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    public void SetTransicion(Transicion t) {
        conjuntoTransiciones.add(t);
    }

    public int getIdEdo() {
        return idEdo;
    }

    public boolean getAceptacion() {
        return aceptacion;
    }

    public HashSet<Transicion> getTransitionTo() {
        return conjuntoTransiciones;
    }


    public Estado getEstado() {
        return this;
    }

    public int getToken() {
        return this.Token;
    }

}
