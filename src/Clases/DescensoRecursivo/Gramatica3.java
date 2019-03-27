package Clases.DescensoRecursivo;

public class Gramatica3 {
	/*
    private Integer Numero;
    private AnalizadorLexico Lexico = new AnalizadorLexico();
	private Tokens ListaTokens = new Tokens();

    public boolean E(AFN f){
    	if(T(f))
    		if(Ep(f))
    			return true;
    		return false;
    }

    public boolean Ep(AFN f){
    	AFN f1;
		int NumTok;
		NumTok = Lexico.GetToken();

		if (NumTok == ListaTokens.OR) {
    		if(T(f1)){
				f = f.Unir(f1);
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
		int NumTok;
		NumTok = Lexico.GetToken();

		if (NumTok == ListaTokens.Concatenar(f1)) {
    		if(C(f1)){
				f = f.Concatenar(f1);
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
		int NumTok;
		NumTok = Lexico.GetToken();
		switch (NumTok) {
			case ListaTokens.CERR_K:
    			f.CerraduraKleen();
    			if(Cp(f))
    				return true;
    			return false;

			case ListaTokens.CERR_POS:
    			f.CerraduraPositiva();
    			if(Cp(f))
    				return true;
    			return false;

			case ListaTokens.CERR_OPC:
    			f.CerraduraOpcional();
    			if(Cp(f))
    				return true;
    			return false;
    	}
    	Lexico.RegresarToken();
    	return true;
    }

    public boolean F(AFN f){
		int NumTok;
		NumTok = Lexico.GetToken();
		switch (ListaTokens) {
			case ListaTokens.PAR_I:
    			if(E(f)){
					int NumTok;
					NumTok = Lexico.GetToken();
					if (NumTok == ListaTokens.PAR_D)
    					return true;
    			}
    			return false;

			case ListaTokens.SIMB:
    			//f.CrearBasico(Lexico.GetLexema()[0]);
    			return true;
    	}

    	return false;
    }
    */
}