package clases;

import java.util.HashSet;

public class Estado {
    //private int Siguiente = 0;
    private int IdEstado = 0;
    private boolean EstadoAceptacion;
    private final HashSet<Transicion> Transiciones = new HashSet<>();

    public Estado() {
        this.EstadoAceptacion = false;
        this.IdEstado = IdEstado++;
    }
}
