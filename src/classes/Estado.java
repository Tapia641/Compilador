package classes;

import java.util.HashSet;

public class Estado {
    /* DATOS INICIALES DE UN ESTADO */
    private int ID = 0;
    private boolean EstadoAceptacion;

    /* TODAS LAS TRANSICIONES QUE TIENE EL ESTADO */
    //private HashSet<Transicion> conjuntoTransiciones;

    public Estado() {
        this.EstadoAceptacion = false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setEstadoAceptacion(boolean estadoAceptacion) {
        EstadoAceptacion = estadoAceptacion;
    }

    public boolean getEstadoAceptacion() {
        return EstadoAceptacion;
    }

    public boolean isEstadoAceptacion() {
        return EstadoAceptacion;
    }

}
