package Clases.AnalizadorSintactico;

/* IMPORTAMOS LOS TOKENS */

import Clases.Tokens;

import java.util.Queue;
import java.util.Stack;

public class AritmeticaBasica {

    /*GRAMATICA PARA REALIZAR ARITMÉTICA BÁSICA +, -, *, /, ()*/

    private Stack<Integer> Pila;

    public void AnalizarSintacticamente(Stack<Integer> Pila) {
        this.Pila = new Stack<>();

        /* INVERTIMOS LA PILA PARA TRABAJAR CON COMODIDAD */
        for (int i = Pila.size() - 1; i > -1; i--) {
            this.Pila.push(Pila.get(i));
        }

        System.out.println("Elementos de la pila: " + this.Pila.size());
        for (int i : Pila) System.out.println(i);

        //COMIENZA A EVALUAR
        System.out.println("E->");
        if (E()) System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
        else System.err.println("CADENA SINTÁCTICAMENTE INCORRECTO");

    }

    public boolean E() {
        System.out.print("T->");
        if (T()) {
            System.out.print("Ep->");
            if (Ep()) {
                System.out.print("true");
                return true;
            }
        }

        System.out.print("false");
        return false;
    }

    public boolean Ep() {
        System.out.println();

        /* PEDIMOS UN TOKEN A LA COLA QUE SE OBTUVO DEL LÉXICO*/

        int TokenPedido = Pila.pop();
        System.out.println("Token" + TokenPedido + " Lexema: ");
        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.MAS || TokenPedido == Tokens.MENOS) {
            System.out.print("T->");
            if (T()) {
                System.out.print("Ep->");
                if (Ep()) {
                    System.out.print("true");
                    return true;
                }

            }
            System.out.print("false");
            return false;
        }

        /* REGRESAMOS EL TOKEN PEDIDO */
        System.out.println("Regresa a la cola: " + TokenPedido);
        Pila.push(TokenPedido);

        System.out.print("true");
        return true;
    }


    public boolean T() {
        System.out.println();
        //Duda
        System.out.print("F->");
        if (F()) {
            System.out.print("Tp->");
            if (Tp()) {
                System.out.println("true");
                return true;
            }

        }
        System.out.print("false");
        return false;
    }

    public boolean Tp() {
        System.out.println();

        /* PEDIMOS UN TOKEN A LA COLA QUE SE OBTUVO DEL LÉXICO*/
        int TokenPedido = Pila.pop();
        System.out.println("Token" + TokenPedido + " Lexema: ");
        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.PROD || TokenPedido == Tokens.DIV) {
            System.out.print("F->");
            if (F()) {
                System.out.print("Tp->");
                if (Tp()) {
                    System.out.print("true");
                    return true;
                }
            }
            System.out.print("false");
            return false;
        }

        /* REGRESAMOS EL TOKEN PEDIDO */
        System.out.print("Regresa a la cola: " + TokenPedido);
        Pila.push(TokenPedido);

        System.out.print("true");
        return true;
    }

    public boolean F() {
        System.out.println();

        /* PEDIMOS UN TOKEN A LA COLA QUE SE OBTUVO DEL LÉXICO*/
        int TokenPedido = Pila.pop();
        System.out.println("Token" + TokenPedido + " Lexema: ");
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.PAR_I) {
            System.out.print("E->");
            if (E()) {
                TokenPedido = Pila.pop();
                System.out.println("Token" + TokenPedido + " Lexema: ");
                if (TokenPedido == Tokens.PAR_D) {
                    System.out.print("true");
                    return true;
                }
            }
            System.out.print("false");
            return false;

        } else if (TokenPedido == Tokens.NUM) {
            System.out.print("true");
            return true;
        }

        System.out.print("false");
        return false;
    }
}
