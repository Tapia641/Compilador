package Clases.DescensoRecursivo;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class AritmeticaBasica {
    //Con esta gramatica podemos hacer aritmética básica +, -, *, /, ()

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


    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens ListaTokens = new Tokens();

    public boolean E(Numero v) {
        if (T(v))
            if (Ep(v))
                return true;
        return false;
    }

    public boolean Ep(Numero v) {

        /* PEDIMOS UN TOKEN */
        int TOKEN = Lexico.GetToken();

        if (TOKEN == ListaTokens.MAS || TOKEN == ListaTokens.MENOS) {
            if (T(v))
                if (Ep(v))
                    return true;
            return false;
        }
        Lexico.RegresarToken();
        return true;
    }


    public boolean T(Numero v) {
        if (F(v))
            if (Tp(v))
                return false;
        return false;
    }

    public boolean Tp(Numero v) {

        /* PEDIMOS UN TOKEN */
        int TOKEN = Lexico.GetToken();

        if (TOKEN == ListaTokens.PROD || TOKEN == ListaTokens.DIV) {
            if (F(v))
                if (Tp(v))
                    return true;
            return false;
        }
        Lexico.RegresarToken();
        return true;
    }

    public boolean F(Numero v) {
/*
        /* PEDIMOS UN TOKEN
        int TOKEN = Lexico.GetToken();
        switch (TOKEN){
            case ListaTokens.PAR_I:
                if (E(Numero)){
                    TOKEN = Lexico.GetToken();
                    if (TOKEN == ListaTokens.PAR_D)
                        return true;
                }
                return false;
            case ListaTokens.NUM:
                return true;
        }
        */
        return false;
    }
}
