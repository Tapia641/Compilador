package LR;

public class Transicion {
    private Estado edo_destino = new Estado();
    private String simbolo;

    public Transicion(String simbolo, Estado edo_destino) {
        this.simbolo = simbolo;
        this.edo_destino = edo_destino;
    }

    public Estado getEdo_destino() {
        return edo_destino;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Estado getEstadoDestino() {
        return this.edo_destino;
    }

}
