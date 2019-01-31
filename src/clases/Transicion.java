package clases;

/* LA TRANSICIÓN DEFINE EL MOVIMIENTO DE UN ESTADO A OTRO, CON UN SÍMBOLO Y UN DESTINO*/
public class Transicion {
    private char simbolo;
    private Estado estadoDestino;

    public Transicion(char simbolo, Estado estadoDestino) {
        this.simbolo = simbolo;
        this.estadoDestino = estadoDestino;
    }

    public Estado getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(Estado estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }
}
