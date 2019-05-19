package Clases.AnalizadorSintactico;

import Clases.TablaLL1;
import Clases.Tokens;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class GramaticaDeGramaticas {

    private Stack<Integer> Pila;
    private Vector<Pair<String, Integer>> V;
    private HashSet<String> C;
    private HashSet<String> Terminales, NoTerminales;
    private Vector<Vector<String>> Tabla;
    private boolean Correcto = false;

    public void AnalizarSintacticamente(Vector<Pair<String, Integer>> V) {
        Terminales = new HashSet<>();
        NoTerminales = new HashSet<>();
        Tabla = new Stack<>();
        this.V = V;
        C = new HashSet<>();
        Stack<Integer> PilaAux = new Stack<>();
        this.Pila = new Stack<>();

        for (Pair<String, Integer> P : V) {
            PilaAux.push(P.getValue());
        }
        PilaAux.push(Tokens.FIN);

        /* INVERTIMOS LA PILA PARA TRABAJAR CON COMODIDAD */
        for (int i = PilaAux.size() - 1; i > -1; i--) {
            this.Pila.push(PilaAux.get(i));
        }

        //COMIENZA A EVALUAR
        if (G()) {
            Correcto = true;
            System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
        } else {
            System.err.println("CADENA SINTÁCTICAMENTE INCORRECTO");
            Correcto = false;
        }

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
            boolean creado = false;

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


    public boolean Exito() {
        return Correcto;
    }

    public Vector<Vector<String>> getTaba() {
        return Tabla;
    }


}
