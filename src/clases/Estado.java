package clases;

import java.util.HashSet;

public class Estado {
    private int Siguiente = 0;
    private int IdEstado;
    private boolean EstadoAceptacion;

    public Estado() {
        this.EstadoAceptacion = false;
        this.IdEstado = Siguiente;
        Siguiente++;
    }
}
