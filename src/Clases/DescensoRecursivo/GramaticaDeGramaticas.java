package Clases.DescensoRecursivo;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class GramaticaDeGramaticas {

    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens ListaTokens = new Tokens();
    private AnalizadorLexico.Edo Estado;

    public boolean G(){
        if(ListaReglas())
            return true;
        return false;
    }

    public boolean ListaReglas(){
        int TOKEN;
        if(Regla()){
            TOKEN = Lexico.GetToken();
            if (TOKEN == ListaTokens.PUNTO_COMA) {
                Estado = Lexico.GetEdo();
                if (ListaReglas())
                    return true;
                Lexico.SetEdo(Estado);
                return true;
            }
        }
        return false;
    }


    public boolean Regla(){
        int TOKEN;
        if(LadoIzquierdo()){
            TOKEN = Lexico.GetToken();
            if (TOKEN == ListaTokens.FLECHA) {
                if(ListaLadosDer())
                    return true;
            }
        }
        return false;
    }

    public boolean LadoIzquierdo(){
        int TOKEN = Lexico.GetToken();
        if (TOKEN == ListaTokens.SIMBOLO)
            return true;
        return false;
    }

    public boolean ListaLadosDer(){
        int TOKEN;
        if(LadoDerecho()){
            TOKEN = Lexico.GetToken();
            if (TOKEN == ListaTokens.OR) {
                if(ListaLadosDer())
                    return true;
                return false;
            }
            Lexico.RegresarToken(TOKEN);
            return true;
        }
        return false;
    }

    public boolean LadoDerecho(){
        int TOKEN;
        if(ListaSimbolos())
            return true;
        return false;
    }

    public boolean ListaSimbolos(){
        int TOKEN = Lexico.GetToken();
        if (TOKEN == ListaTokens.SIMBOLO) {
            //Lexico.getEdo(E);
            if (ListaSimbolos())
                Lexico.RegresarToken(TOKEN);
            return true;
        }
        return false;
    }
}
