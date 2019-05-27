import Clases.*;
//import LR.Grammar;
//import LR.LR1;

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

        /*
        //PROBANDO LL1
        try {
            String nombreArchivo = "Gramatica1.txt";
            LL1 LL1 = new LL1(nombreArchivo);
            LL1.ConstruirTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        //PROBANDO LR0AUX
        String nombreArchivo = "Gramatica1.txt";
        try {
            LR0AUX LR = new LR0AUX(nombreArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        //Gramatica de Gramaticas
        AFD afd = new AFD();
        GramaticaDeGramaticas GG = new GramaticaDeGramaticas();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
        try {
            //IMPORTAMOS HASMAP DE GRAMATICA DE GRAMATICAS PREVIAMENTE REALIZADA
            afd.LeerObject("GramaticaDeGramaticas");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String cadena = "E->TE';" +
                "E'->+TE|-TE'| ;" +
                "T->FT';" +
                "T'->*FT'|/FT'| ;" +
                "F->(E)|NUM;";
        AnalizarLexicamente.Lexico(cadena, afd.getMatriz());
        GG.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());

*/
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
