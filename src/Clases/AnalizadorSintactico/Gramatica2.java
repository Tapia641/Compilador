package Clases.AnalizadorSintactico;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class Gramatica2{
	private Integer Numero;
    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens Tok = new Tokens();

    boolean Epsilon(float v){
    	if(E(v)){
    		Tok = Lexico.GetToken();
    		if(TOKEN == Tok.FIN)
    			return true;
    	}
    	return false;
    }

    boolean E(float v){
    	if(T(v)){
    		if(Ep(v))
    			return true;
    	}
    	return false;
    }

    boolean Ep(float v){
    	float v1;
    	Tok = Lexico.GetToken();
    	if(TOKEN == Tok.MAS || TOKEN == Tok.MENOS){
    		if(T(v1)){
    			v = v + (TOKEN == Tok.MAS) ? v1 -= v;
    			if(Ep(v))
    				return true;
    		}
    		return false;
    	}
    	Lexico.RegresarTocken();
    	return true;
    }

    boolean T(float v){
    	if(P(v))
    		if(Tp(v))
    			return true;
    }

    boolean Tp(float v){
    	float v1;
    	Tok = Lexico.GetToken();
    	if(TOKEN == Tok.PROD || TOKEN == Tok.DIV){
    		if(T(v1)){
    			v = v * (TOKEN == Tok.PROD) ? v1 =/ v;
    			if(Tp(v))
    				return true;
    		}
    		return false;
    	}
    	Lexico.RegresarTocken();
    	return true;
    }

    boolean F(float v){
    	int token = Tok.GetToken();

    	switch(token){
    		case PAR_I:

    		case SIN:
    			token = Lexico.GetToken();
    			if(TOKEN == Tok.PAR_I){
    				if(E(v)){
    					token = Lexico.GetToken();
    					if(TOKEN == Tok.PAR_D){
    						v = sin(v);
    						return true;
    					}
    				}
    			}
    			return false;

    		case NUM:
    	}

    }

}