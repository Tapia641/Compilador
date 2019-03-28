package Clases.DescensoRecursivo;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class Gramatica4 {

/*
    private Integer Numero;
    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens Tok = new Tokens();

    public boolean G(){
        if(ListaReglas())
            return true;
        return false;
    }

    public boolean ListaReglas(){
        int TOKEN;
        if(Regla()){
            TOKEN = Lexico.GetToken();
            if(TOKEN == Tok.PUNTO_COMA){
                if(ListaReglasP())
                    return true;
            }
        }
        return false;
    }

    public boolean ListaReglasP(){
        int TOKEN;
        if(Regla()){
            TOKEN = Lexico.GetToken();
            if(TOKEN == Tok.PUNTO_COMA){
                Lexico.GetEdo(E); // Guardar el estado inicial
                if(ListaReglas())
                    return true;
            }
        }
        return false;
    }

    public boolean Regla(){
        int TOKEN;
        if(LadoIzquierdo()){
            TOKEN = Lexico.GetToken();
            if(TOKEN == Tok.FLECHA){
                if(ListaLadosDer())
                    return true;
            }
        }
        return false;
    }

    public boolean LadoIzquierdo(){
        int TOKEN = Lexico.GetToken();
        if(TOKEN == Tok.SIMBOLO)
            return true;
        return false;
    }

    public boolean ListaLadosDer(){
        int TOKEN;
        if(LadoDerecho()){
            TOKEN = Lexico.GetToken();
            if(TOKEN == Tok.OR){
                if(ListaLadosDer())
                    return true;
                return false;
            }
            Lexico.RegresarToken();
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
        if(TOKEN == Tok.SIMBOLO) {
            //Lexico.getEdo(E);
            if (ListaSimbolos())
                Lexico.RegresarToken();
            return true;
        }
        return false;
    }
    */
}
