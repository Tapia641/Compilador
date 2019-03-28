package Clases.DescensoRecursivo;

import Clases.AnalizadorLexico;
import Clases.Tokens;

import java.util.HashMap;
import java.util.Vector;

/* ANÁLISIS DE ARITMÉTICA BÁSICA PARA UNA CADENA DADA */

public class Calculadora {

    /* PARA MANEJAR REFERENCIA EN JAVA */
    private class Numero {

        public float Num;

        public Numero() {
        }

        public float getNum() {
            return Num;
        }

        public void setNum(float num) {
            Num = num;
        }
    }

    /* DATOS CON LOS QUE VAMOS A TRABAJAR */
    private Numero N = new Numero();
    private AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
    private Tokens ListaTokens = new Tokens();

    public void Pertenece(String cadena, HashMap<Integer, Vector<String>> Matriz) {

        AnalizarLexicamente = new AnalizadorLexico();
        AnalizarLexicamente.Lexico(cadena, Matriz);

        System.out.println("\nResultado del analizador sintáctico:\n");

        /* COMENZAMOS CON EL ANÁLISIS SINTÁCTICO*/
        Numero v = new Numero();
        //Asociada = E(v);
/*
        if (Asociada) {
            System.out.println("La cadena " + cadena + " pertenece al lenguaje");
        } else System.out.println("La cadena " + cadena + " no pertenece al lenguaje");
        */
    }
/*
     //INICIAMOS CON EL ANÁLISIS SINTÁCTICO
    public boolean Epsilon(Numero v) {
    	if(E(v)){
            int NumTok;
            NumTok = AnalizarLexicamente.GetToken();
            if (NumTok == ListaTokens.FIN)
    			return true;
    	}
    	return false;
    }

    public boolean E(Numero v) {
    	if(T(v)){
    		if(Ep(v))
    			return true;
    	}
    	return false;
    }

    public boolean Ep(Numero v) {
        Numero v1 = new Numero();
        int NumTok;
        NumTok = AnalizarLexicamente.GetToken();
        if (NumTok == ListaTokens.MAS || NumTok == ListaTokens.MENOS) {
    		if(T(v1)){

                //Resolver si es correcto
                float aux = v.getNum() + ((NumTok == ListaTokens.MAS) ? v.getNum() : v1.getNum());
                //

                v.setNum(aux);
                //v = v + (NumTok == ListaTokens.MAS) ? v1 -= v;
    			if(Ep(v))
    				return true;
    		}
    		return false;
    	}
    	AnalizarLexicamente.RegresarToken(NumTok);
    	return true;
    }
    public boolean T(Numero v) {
    	if(P(v))
    		if(Tp(v))
    			return true;
    }

    public boolean Tp(Numero v) {
        Numero v1 = new Numero();
        int TOKEN;
        TOKEN = AnalizarLexicamente.GetToken();//Remueve un elemento de la pila
        if (TOKEN == ListaTokens.PROD || TOKEN == ListaTokens.DIV) {
    		if(T(v1)){
                //Resolver si es correcto
                float aux = v.getNum() * ((TOKEN == ListaTokens.PROD) ? v1.getNum() : v1.getNum());
                //
                v.setNum(aux);

                //v = v * ((NumTok == ListaTokens.PROD) ? v1 =/v);
    			if(Tp(v))
    				return true;
    		}
    		return false;
    	}
        AnalizarLexicamente.RegresarToken();//Pone el elemento en la pila
    	return true;
    }

    public boolean F(Numero v) {
        int NumTok = AnalizarLexicamente.GetToken();
        switch (NumTok) {
            case ListaTokens.PAR_I:

            case ListaTokens.SIN:
                NumTok = AnalizarLexicamente.GetToken();
                if (NumTok == ListaTokens.PAR_I) {
    				if(E(v)){
                        NumTok = AnalizarLexicamente.GetToken();
                        if (NumTok == ListaTokens.PAR_D) {
                            v = Math.sin(v);
    						return true;
    					}
    				}
    			}
    			return false;

            case ListaTokens.NUM:
    	}

    }
    */

}