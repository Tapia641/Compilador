package LR;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class AFD {
    private static int idSig = 0;
    private int idAFN;
    private Estado estadoInicial;
    private HashSet<Estado> estadosAceptacion;
    private LinkedHashSet<String> alfabeto;
    private LinkedList<Estado> conjuntoEstados;
    private Object Matriz[][];
    private String[] header;
    private LinkedList<Object> snResultSet;

    public AFD() {
        snResultSet = new LinkedList();
        idAFN = idSig;
        estadoInicial = new Estado();
        estadosAceptacion = new HashSet<>();
        alfabeto = new LinkedHashSet<>();
        conjuntoEstados = new LinkedList<>();
        this.conjuntoEstados.add(estadoInicial);
        idSig++;
    }

    public void addEstadosAceptacion(Estado e) {
        this.estadosAceptacion.add(e);
    }

    public void addAlfabeto(HashSet<String> A) {
        this.alfabeto.addAll(A);
    }

    public void addConjuntoEstados(Estado e) {
        this.conjuntoEstados.addLast(e);
    }

    public int getIdAFN() {
        return idAFN;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public HashSet<Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public HashSet<String> getAlfabeto() {
        return alfabeto;
    }

    public LinkedList<Estado> getConjuntoEstados() {
        return conjuntoEstados;
    }

    public void printAFD() {
        this.conjuntoEstados.forEach((e) -> {
            e.getTransitionTo().forEach(t -> {
                System.out.println("Estado  : " + e.getIdEdo() + " transita al estado " + t.getEdo_destino().getIdEdo() + " con el simbolo " + t.getSimbolo());
            });
        });
    }

    public String[] getColumnNames() {
        return this.header;
    }

    public Object[][] getTableAFD() {
        return this.Matriz;
    }

    public Object[][] getMatriz() {
        return this.Matriz;
    }

}


