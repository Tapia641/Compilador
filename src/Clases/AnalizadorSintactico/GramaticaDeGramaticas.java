package Clases.AnalizadorSintactico;

import Clases.Tokens;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class GramaticaDeGramaticas {

    private Stack<Integer> Pila;
    private Vector<Pair<String, Integer>> V;
    private HashSet<String> C;

    public void AnalizarSintacticamente(Vector<Pair<String, Integer>> V) {
        this.V = V;
        C = new HashSet<>();
        Stack<Integer> PilaAux = new Stack<>();
        this.Pila = new Stack<>();

        for (Pair<String, Integer> P : V) {
            System.out.println(P.getValue());
            PilaAux.push(P.getValue());
        }
        PilaAux.push(Tokens.FIN);

        /* INVERTIMOS LA PILA PARA TRABAJAR CON COMODIDAD */
        for (int i = PilaAux.size() - 1; i > -1; i--) {
            this.Pila.push(PilaAux.get(i));
        }

        //COMIENZA A EVALUAR
        if (G()) {
            System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
        } else System.err.println("CADENA SINTÁCTICAMENTE INCORRECTO");

    }
    public boolean G(){
        if (ListaReglas()) {
            return true;
        }
        return false;
    }

    public boolean ListaReglas(){
        int TokenPedido;

        if(Regla()){
            TokenPedido = Pila.pop();

            if (TokenPedido == Tokens.ERROR) {
                return false;
            }

            if (TokenPedido == Tokens.FIN) {
                return true;
            }

            if (TokenPedido == Tokens.GG_PUNTO_COMA) {
                if (ListaReglasP())
                    return true;
            }
        }

        return false;
    }

    public boolean ListaReglasP() {
        int TokenPedido;
        Stack<Integer> Aux = Pila;

        if (Regla()) {
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.GG_PUNTO_COMA) {
                if (ListaReglasP())
                    return true;
            }
            return false;
        }

        /*POSIBLE SOLUCIÓN*/
        if (Pila.size() == 0) return true;

        Pila = Aux;
        return false;
    }

    public boolean Regla(){
        int TokenPedido;

        if(LadoIzquierdo()){
            TokenPedido = Pila.pop();
            if (TokenPedido == Tokens.GG_FLECHA) {
                if (ListaLadosDerechos())
                    return true;
            }
        }
        return false;
    }

    public boolean LadoIzquierdo(){
        int TokenPedido = Pila.pop();

        if (TokenPedido == Tokens.GG_SIMBOLO) {
            return true;
        }
        return false;
    }

    public boolean ListaLadosDerechos() {
        if(LadoDerecho()){
            if (ListaLadosDerechosP()) {
                return true;
            }
        }
        return false;
    }

    public boolean ListaLadosDerechosP() {
        int TokenPedido;
        TokenPedido = Pila.pop();

        if (TokenPedido == Tokens.GG_OR) {
            if (LadoDerecho()) {
                if (ListaLadosDerechosP()) {
                    return true;
                }
            }
            return false;
        }

        Pila.push(TokenPedido);
        return true;
    }

    public boolean LadoDerecho(){
        if (ListaSimbolos())
            return true;
        return false;
    }

    public boolean ListaSimbolos(){
        int TokenPedido = Pila.pop();

        if (TokenPedido == Tokens.GG_SIMBOLO) {
            if (ListaSimbolosP()) {
                return true;
            }
        }
        return false;
    }

    public boolean ListaSimbolosP() {
        int TokenPedido = Pila.pop();

        if (TokenPedido == Tokens.GG_SIMBOLO) {
            if (ListaSimbolosP()) {
                return true;
            }
            return false;
        }

        Pila.push(TokenPedido);
        return true;
    }

    public boolean LadoDerechoP() {
        int TokenPedido = Pila.pop();

        if (TokenPedido == Tokens.GG_SIMBOLO) {
            if (LadoDerechoP())
                return true;
            return false;
        }

        Pila.push(TokenPedido);
        return true;
    }
}
