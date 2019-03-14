package Clases.AnalizadorSintactico;

import Clases.AnalizadorLexico;
import Clases.Tokens;

public class Gramatica1 {

    //

    private Integer Numero;
    private AnalizadorLexico Lexico = new AnalizadorLexico();
    private Tokens Tok = new Tokens();

    public boolean E(Integer Numero) {
        if (T(Numero))
            if (Ep(Numero))
                return true;
        return false;
    }

    public boolean Ep(Integer Numero) {

        /* PEDIMOS UN TOKEN */
        int TOKEN = Lexico.GetToken();

        if (TOKEN == Tok.MAS || TOKEN == Tok.MENOS) {
            if (T(Numero))
                if (Ep(Numero))
                    return true;
            return false;
        }
        Lexico.RegresarToken();
        return true;
    }


    public boolean T(Integer Numero) {
        if (F(Numero))
            if (Tp(Numero))
                return false;
        return false;
    }

    public boolean Tp(Integer Numero) {

        /* PEDIMOS UN TOKEN */
        int TOKEN = Lexico.GetToken();

        if (TOKEN == Tok.PROD || TOKEN == Tok.DIV) {
            if (F(Numero))
                if (Tp(Numero))
                    return true;
            return false;
        }
        Lexico.RegresarToken();
        return true;
    }

    public boolean F(Integer Numero) {

        /* PEDIMOS UN TOKEN */
        int TOKEN = Lexico.GetToken();
/*
        switch (TOKEN){
            case Tok.PAR_I:
                if (E(Numero)){
                    TOKEN = Lexico.GetToken();
                    if (TOKEN == Tok.PAR_D)
                        return true;
                }
                return false;
            case Tok.NUM:
                return true;
        }
*/
        return false;
    }
}
