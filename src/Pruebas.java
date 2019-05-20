import Clases.*;
import LR.Grammar;
import LR.LR1;

import java.io.IOException;
import java.util.ArrayList;
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

        //PROBANDO LR0
        /*----------ANÁLISIS PARA UNA GRAMÁTICA----------------------
        //AUTÓMATA PARA IDENTIFICACION DE UNA AGRAMÁTICA
        LR.AFN a0 = new LR.AFN();
        a0.createAFN("__SIMBOLO__");
        a0.asignaToken(LR.Tokens.SIMBOLO);

        LR.AFN a1 = new LR.AFN();
        a1.createAFN("-");
        LR.AFN a2 = new LR.AFN();
        a2.createAFN(">");
        a1.concatenacion(a2);
        a1.asignaToken(LR.Tokens.FLECHA);

        LR.AFN a3 = new LR.AFN();
        a3.createAFN(";");
        a3.asignaToken(LR.Tokens.PUNTO_COMA);

        LR.AFN a4= new LR.AFN();
        a4.createAFN("|");
        a4.asignaToken(LR.Tokens.OR);

        LR.AFN a5 = new LR.AFN();
        a5.createAFN("__SIMBOLO__");
        LR.AFN a6 = new LR.AFN();
        a6.createAFN("'");
        a5.concatenacion(a6);
        a5.asignaToken(LR.Tokens.SIMBOLO);

        LR.AFN a7 = new LR.AFN();
        a7.createAFN("Ɛ");
        a7.asignaToken(LR.Tokens.SIMBOLO);

        //Array de automatas que crearan el AFN especial
        ArrayList<LR.AFN> automatas = new ArrayList<>();
        automatas.add(a0);
        automatas.add(a1);
        automatas.add(a3);
        automatas.add(a4);
        automatas.add(a5);
        automatas.add(a7);

        //Un afn especial es un AFN con un estado inicicial con transicion al los edos iniciales de un conjunto de automatas
        LR.AFN special1 = new LR.AFN();
        special1 = special1.makeAFNSpecial(automatas);

        //Objeto AFD
        LR.AFD D = special1.convertToAFD();
        D.printAFD(); //Imprime la tabla e transiciones resultante de la conversion del AFN a AFD

        String grammar = ""
                + "E->E+T|E-T|T;"
                + "T->T*P|T/P|P;"
                + "P->P^F|F;"
                + "F->(E)|n;";
        //Símbolos terminales de la gramática
        HashSet<String> vt = new HashSet<>();
        vt.add("+");
        vt.add("-");
        vt.add("*");
        vt.add("^");
        vt.add("(");
        vt.add(")");
        vt.add("n");
        vt.add("/");

        //Símbolos no terminales de la gramática
        HashSet<String> vn = new HashSet<>();
        vn.add("E");
        vn.add("T");
        vn.add("F");
        vn.add("P");

        //G es un obj de la clase Grammar que recive sus reglas gramticales en un string
        //Y sus symbolos termianles y no terminales
        //su mapa  de reglas rm RulesMap se fromará en el descenso recursivo del análisis sintáctico
        Grammar G = new Grammar(vt,vn,grammar);
        G.LexicAnalize(D); //Analiza lexicamente la gramtica proporcionaada con su AFD asociado
        G.SintaxAnalize(); //Analiza sintácticamente la gramática prorporcionada

         */
        //PROBANDO LL1
        try {
            String nombreArchivo = "Gramatica1.txt";
            LL1 LL1 = new LL1(nombreArchivo);
            LL1.ConstruirTabla();
        } catch (IOException e) {
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
