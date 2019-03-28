package Clases.DescensoRecursivo;

import Clases.AnalizadorLexico;
import Clases.Tokens;

/* ANÁLISIS DE ARITMÉTICA BÁSICA PARA UNA CADENA DADA */

public class Gramatica2 {

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
/*
    /* DATOS CON LOS QUE VAMOS A TRABAJAR
    private Numero N = new Numero();
    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens ListaTokens = new Tokens();

    public Gramatica2() {
    }

    /* INICIAMOS CON EL ANÁLISIS SINTÁCTICO
    public boolean Epsilon(Numero v) {
    	if(E(v)){
            int NumTok;
            NumTok = Lexico.GetToken();
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
        NumTok = Lexico.GetToken();
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
    	Lexico.RegresarToken();
    	return true;
    }
    public boolean T(Numero v) {
    	if(P(v))
    		if(Tp(v))
    			return true;
    }

    public boolean Tp(Numero v) {
        Numero v1 = new Numero();
        int NumTok;
        NumTok = Lexico.GetToken();//Remueve un elemento de la pila
        if (NumTok == ListaTokens.PROD || NumTok == ListaTokens.DIV) {
    		if(T(v1)){
                //Resolver si es correcto
                float aux = v.getNum() * ((NumTok == ListaTokens.PROD) ? v1.getNum() : v1.getNum());
                //
                v.setNum(aux);

                //v = v * ((NumTok == ListaTokens.PROD) ? v1 =/v);
    			if(Tp(v))
    				return true;
    		}
    		return false;
    	}
        Lexico.RegresarToken();//Pone el elemento en la pila
    	return true;
    }

    public boolean F(Numero v) {
        int NumTok = Lexico.GetToken();
        switch (NumTok) {
            case ListaTokens.PAR_I:

            case ListaTokens.SIN:
                NumTok = Lexico.GetToken();
                if (NumTok == ListaTokens.PAR_I) {
    				if(E(v)){
                        NumTok = Lexico.GetToken();
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