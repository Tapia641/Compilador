package Clases.AnalizadorSintactico;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class Gramatica2 {

    //
	/*
	private Integer Numero;
    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens ListaTokens = new Tokens();

    public boolean Epsilon(float v){
    	if(E(v)){
            int NumTok;
            NumTok = Lexico.GetToken();
            if (NumTok == ListaTokens.FIN)
    			return true;
    	}
    	return false;
    }

    public boolean E(float v){
    	if(T(v)){
    		if(Ep(v))
    			return true;
    	}
    	return false;
    }

    public boolean Ep(float v){
    	float v1;
        int NumTok;
        NumTok = Lexico.GetToken();
        if (NumTok == ListaTokens.MAS || NumTok == ListaTokens.MENOS) {
    		if(T(v1)){
                v = v + (NumTok == ListaTokens.MAS) ? v1 -= v;
    			if(Ep(v))
    				return true;
    		}
    		return false;
    	}
    	Lexico.RegresarToken();
    	return true;
    }

    public boolean T(float v){
    	if(P(v))
    		if(Tp(v))
    			return true;
    }

    public boolean Tp(float v){
    	float v1;
        int NumTok;
        NumTok = Lexico.GetToken();
        if (NumTok == ListaTokens.PROD || NumTok == ListaTokens.DIV) {
    		if(T(v1)){
                v = v * (NumTok == ListaTokens.PROD) ? v1 =/v;
    			if(Tp(v))
    				return true;
    		}
    		return false;
    	}
    	Lexico.RegresarToken();
    	return true;
    }

    public boolean F(float v){
        int NumTok = ListaTokens.GetToken();
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