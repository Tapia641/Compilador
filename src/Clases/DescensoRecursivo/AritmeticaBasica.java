package Clases.DescensoRecursivo;

import Clases.AnalizadorLexico;
import Clases.Tokens;

import java.util.HashMap;
import java.util.Vector;

public class AritmeticaBasica {
    //Con esta gramatica podemos hacer aritmética básica +, -, *, /, ()

    private AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
    public static Tokens ListaTokens = new Tokens();
    private boolean Asociada = false;

    public void Pertenece(String cadena, HashMap<Integer, Vector<String>> Matriz) {

        AnalizarLexicamente = new AnalizadorLexico();
        AnalizarLexicamente.Lexico(cadena, Matriz);

        System.out.println("\nResultado del analizador sintáctico:\n");

        /* COMENZAMOS CON EL ANÁLISIS SINTÁCTICO*/
        Asociada = E();

        if (Asociada) {
            System.out.println("La cadena " + cadena + " pertenece al lenguaje");
        } else System.out.println("La cadena " + cadena + " no pertenece al lenguaje");

    }

    public boolean E() {
        if (T())
            if (Ep())
                return true;
        return false;
    }

    public boolean Ep() {

        /* PEDIMOS UN TOKEN */
        int TOKEN = AnalizarLexicamente.GetToken();

        if (TOKEN == ListaTokens.MAS || TOKEN == ListaTokens.MENOS) {
            if (T())
                if (Ep())
                    return true;
            return false;
        }
        AnalizarLexicamente.RegresarToken(TOKEN);
        return true;
    }


    public boolean T() {
        if (F())
            if (Tp())
                return true; //Duda
        return false;
    }

    public boolean Tp() {

        /* PEDIMOS UN TOKEN */
        int TOKEN = AnalizarLexicamente.GetToken();

        if (TOKEN == ListaTokens.PROD || TOKEN == ListaTokens.DIV) {
            if (F())
                if (Tp())
                    return true;
            return false;
        }
        AnalizarLexicamente.RegresarToken(TOKEN);
        return true;
    }

    public boolean F() {
        /* PEDIMOS UN TOKEN*/
        int TOKEN = AnalizarLexicamente.GetToken();

        if (TOKEN == ListaTokens.PAR_I) {
            if (E()) {
                TOKEN = AnalizarLexicamente.GetToken();
                if (TOKEN == ListaTokens.PAR_D)
                    return true;
            }
            return false;

        } else if (TOKEN == ListaTokens.NUM) {
            return true;
        }
        return false;
    }
}
