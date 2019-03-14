package Clases.AnalizadorSintactico;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class Gramatica3 {
    private Integer Numero;
    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens Tok = new Tokens();

    public boolean E(AFN f){
    	if(T(f))
    		if(Ep(f))
    			return true;
    		return false;
    }

    public boolean Ep(AFN f){
    	AFN f1;
    	Tok = Lexico.GetToken();

    	if(TOKEN == Tok.OR){
    		if(T(f1)){
    			f = f.unir(f1);
    			if(Ep(f))
    				return true;
    		}
    		return false;
    	}
    	Lexico.RegresarToken();
    	return true;
    }

    public boolean T(AFN f){
    	if(C(f))
    		if(Tp(f))
    			return true;
    		return false;
    }

    public boolean Tp(AFN f){
    	AFN f1;
    	Tok = Lexico.GetToken();

    	if(TOKEN == Tok.Concatenar(f1)){
    		if(C(f1)){
    			f = f.Concatenar(f1))
				if(Tp(f1))
					return true;
    		}
    		return false;
    	}
    	Lexico.RegresarToken();
    	return true;
    }

    public boolean C(AFN f){
    	if(F(f))
    		if(Cp(f1))
    			return true;
    		return false;
    }

    public boolean Cp(AFN f){
    	Tok = Lexico.GetToken();
    	switch(tok){
    		case CERR_K:
    			f.CerraduraKleen();
    			if(Cp(f))
    				return true;
    			return false;

    		case CERR_POS:
    			f.CerraduraPositiva();
    			if(Cp(f))
    				return true;
    			return false;

    		case CERR_OPC:
    			f.CerraduraOpcional();
    			if(Cp(f))
    				return true;
    			return false;
    	}
    	Lexico.RegresarToken();
    	return true;
    }

    public boolean F(AFN f){
    	Tok = Lexico.GetToken();
    	switch(Tok){
    		case PAR_I:
    			if(E(f)){
    				Tok = Lexico.GetToken();
    				if(TOKEN == Tok.PAR_D)
    					return true;
    			}
    			return false;

    		case SIMB:
    			//f.CrearBasico(Lexico.GetLexema()[0]);
    			return true;
    	}

    	return false;
    }
}