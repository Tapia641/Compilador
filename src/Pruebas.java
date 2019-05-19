import Clases.AFD;
import Clases.AFN;
import Clases.AnalizadorLexico;
import Clases.AnalizadorSintactico.AritmeticaBasica;
import Clases.AnalizadorSintactico.Calculadora;
import Clases.AnalizadorSintactico.ExpresionesRegulares;
import Clases.AnalizadorSintactico.GramaticaDeGramaticas;
import Clases.Tokens;

import java.io.IOException;
import java.util.HashSet;

public class Pruebas {
    public static void main(String[] args) {

        HashSet<AFN> conjuntoAFN = new HashSet<>();
        AFN f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16;
        f1 = new AFN();
        f2 = new AFN();
        f3 = new AFN();
        f4 = new AFN();
        f5 = new AFN();
        f6 = new AFN();
        f7 = new AFN();
        f8 = new AFN();
        f9 = new AFN();
        f10 = new AFN();
        f11 = new AFN();
        f12 = new AFN();
        f13 = new AFN();
        f14 = new AFN();
        f15 = new AFN();
        f16 = new AFN();

        //GRAMATICA DE GRAMATICAS
        AFD afd1 = new AFD();

        //f1.CrearBasico('A','Z'); f1.setToken(Tokens.GG_SIMBOLO);
        f2.CrearBasico('-');
        f3.CrearBasico('>');
        f2.Concatenar(f3);
        f2.setToken(Tokens.GG_FLECHA);
        f3.CrearBasico(';');
        f3.setToken(Tokens.GG_PUNTO_COMA);
        f4.CrearBasico('|');
        f4.setToken(Tokens.GG_OR);
        f5.CrearBasico('A', 'Z');
        f6.CrearBasico("'");
        f5.Concatenar(f6);
        f5.setToken(Tokens.GG_SIMBOLO);

        f6.CrearBasico(' ', ':');
        f7.CrearBasico('<', '{');
        f6.Unir(f7);
        f7.CrearBasico('}', '~');
        f6.Unir(f7);
        f6.setToken(Tokens.GG_SIMBOLO);

        //conjuntoAFN.add(f1);
        conjuntoAFN.add(f2);
        conjuntoAFN.add(f3);
        conjuntoAFN.add(f4);
        conjuntoAFN.add(f5);
        conjuntoAFN.add(f6);

        try {
            afd1.convertirAFD(conjuntoAFN, "GramaticaDeGramaticas");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Gramatica de Gramaticas
        AFD afd = new AFD();
        GramaticaDeGramaticas GG = new GramaticaDeGramaticas();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
        try {
            //IMPORTAMOS HASMAP DE GRAMATICA CALCULADORA PREVIAMENTE REALIZADA
            afd.LeerObject("GramaticaDeGramaticas");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //String cadena = "S?°D+";
        String cadena = "E->TE';" +
                "E'->+TE|-TE'| ;" +
                "T->FT';" +
                "T'->*FT'|/FT'| ;" +
                "F->(E)|NUM;";
        AnalizarLexicamente.Lexico(cadena, afd.getMatriz());
        GG.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());

/*
        AFD afd = new AFD();
        ExpresionesRegulares ER = new ExpresionesRegulares();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
        try {
            //IMPORTAMOS HASMAP DE GRAMATICA CALCULADORA PREVIAMENTE REALIZADA
            afd.LeerObject("ExpresionesRegulares");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //String cadena = "S?°D+";
        String cadena = "s?°D+°.°D+";
        AnalizarLexicamente.Lexico(cadena, afd.getMatriz());
        ER.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());
*/

/*
        //Terminado
        AFD afd = new AFD();
        Calculadora C = new Calculadora();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
        try {
            //EXPORTAMOS EL AFN CONVERTIDO A AFD EN UN HASHTABLE
            //afd.convertirAFD(conjuntoAFN);
            //IMPORTAMOS HASMAP DE GRAMATICA CALCULADORA PREVIAMENTE REALIZADA
            afd.LeerObject("Calculadora");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String cadena = "4.5*9+SIN(17-14/8)*6";
        AnalizarLexicamente.Lexico(cadena, afd.getMatriz());
        C.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());
*/
        /*
        //Terminado
        AFD afd1 = new AFD();
        AritmeticaBasica AB = new AritmeticaBasica();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();

        try {
            //IMPORTAMOS HASMAP DE GRAMATICA ARITMETICA BASICA PREVIAMENTE REALIZADA
            afd1.LeerObject("AritmeticaBasica");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        //EL ANALIZADOR LEXICO GENERA LOS TOKENS Y EL LEXEMA ASOCIADO
        String cadena = "2.8+76/(19-14.5)";
        //String cadena = "2+7*(5-2)";
        AnalizarLexicamente.Lexico(cadena, afd1.getMatriz());
        AB.AnalizarSintacticamente(AnalizarLexicamente.getPila());
        */

    }
}
