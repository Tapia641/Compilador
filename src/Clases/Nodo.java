package Clases;

public class Nodo {
    private boolean isTerminal;
    private String simbolo;

    public Nodo() {
        this.isTerminal = false;
        this.simbolo = "";
    }

    public Nodo(String n, boolean t) {
        this.isTerminal = t;
        this.simbolo = n;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setIsTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public boolean getIsTerminal() {
        return this.isTerminal;
    }
}