package Clases.AnalizadorSintactico;

import Clases.AFD;
import Clases.AFN;
import Clases.Tokens;
import Dibujar.Draw;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class ExpresionesRegulares {

    private Stack<Integer> Pila;
    private HashSet<String> C;
    private Vector<Pair<String, Integer>> V;
    private boolean R;
    private AFN f;


    public void AnalizarSintacticamente(Vector<Pair<String, Integer>> V) {
        this.V = V;
        C = new HashSet<>();
        Stack<Integer> PilaAux = new Stack<>();
        this.Pila = new Stack<>();
        R = false;

        for (Pair<String, Integer> P : V) {
            System.out.println(P.getValue());
            PilaAux.push(P.getValue());
        }
        PilaAux.push(0);

        /* INVERTIMOS LA PILA PARA TRABAJAR CON COMODIDAD */
        for (int i = PilaAux.size() - 1; i > -1; i--) {
            this.Pila.push(PilaAux.get(i));
        }

        f = new AFN();
        //COMIENZA A EVALUAR
        if (E(f)) {
            System.out.println("CADENA SINTÁCTICAMENTE CORRECTA");
            System.out.println("EL RESULTADO DE LA EXPRESION REGULAR ES LA SIGUIENTE:");

            /* COMENZAMOS A CONVERTIR */
            Draw D = new Draw();
            D.Dibuja(f.DibujarAFN());
            System.out.println(f.ImprimeAFN());
            R = true;

        } else System.err.println("CADENA SINTÁCTICAMENTE INCORRECTO");

    }

    public boolean getR(){
        return R;
    }

    public AFN getResultado(){
        return f;
    }


    public boolean E(AFN f){
        if (T(f)) {
    		if(Ep(f))
    			return true;

        }
        return false;
    }

    public boolean Ep(AFN f){
        AFN f1 = new AFN();
        int TokenPedido = Pila.pop();
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.ER_OR) {
    		if(T(f1)){
				f = f.Unir(f1);
    			if(Ep(f))
    				return true;
    		}
    		return false;
    	}

        Pila.push(TokenPedido);
    	return true;
    }

    public boolean T(AFN f){
        if (C(f)) {
    		if(Tp(f))
    			return true;
        }

        return false;
    }

    public boolean Tp(AFN f){

        AFN f1 = new AFN();
        int TokenPedido = Pila.pop();
        if (TokenPedido == Tokens.ERROR) return false;

        if (TokenPedido == Tokens.ER_CONCA) {
    		if(C(f1)){
                f.Concatenar(f1);
                if (Tp(f))
					return true;
    		}
    		return false;
    	}

        Pila.push(TokenPedido);
    	return true;
    }

    public boolean C(AFN f){
        if (F(f)) {
            if (Cp(f))
    			return true;
        }

        return false;
    }

    public boolean Cp(AFN f){
        int TokenPedido = Pila.pop();
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.ER_THOMPSON) {
            f.CerraduraOpcional();
            if (Cp(f))
                return true;
            return false;
        }

        if (TokenPedido == Tokens.ER_CERRADURA_POSTIVIA) {
            f.CerraduraPositiva();
            if (Cp(f))
                return true;
            return false;
        }

        if (TokenPedido == Tokens.ER_CERRADURA_ESTRELLA) {
            f.CerraduraEstrella();
            if (Cp(f))
                return true;
            return false;
        }

        Pila.push(TokenPedido);
    	return true;
    }

    public boolean F(AFN f){

        int TokenPedido = Pila.pop();
        if (TokenPedido == Tokens.ERROR) return false;


        if (TokenPedido == Tokens.ER_PAR_I) {
            if (E(f)) {
                TokenPedido = Pila.pop();
                if (TokenPedido == Tokens.ER_PAR_D)
                    return true;
            }
            return false;
        }
        if (TokenPedido == Tokens.ER_SIMBOLO) {
            boolean creado = false;

            for (Pair<String, Integer> P : V) {
                //CORROBORAR REPETIDOS A + A O  5+5
                if (P.getValue() == Tokens.ER_SIMBOLO && !C.contains(P.getKey())) {
                    C.add(P.getKey());
                    System.out.println("Se creo el automata: " + P.getKey());
                    f.CrearBasico(P.getKey());
                    creado = true;
                    break;
                }
            }

            if (!creado) {
                for (Pair<String, Integer> P : V) {
                    //CORROBORAR REPETIDOS A + A O  5+5
                    if (P.getValue() == Tokens.ER_SIMBOLO) {
                        C.add(P.getKey());
                        System.out.println("Se creo el automata: " + P.getKey());
                        f.CrearBasico(P.getKey());
                        break;
                    }
                }
            }

            return true;
        }

    	return false;
    }
}