import Clases.*;
//import LR.Grammar;
//import LR.LR1;

import java.io.IOException;
import java.util.HashSet;

public class Pruebas {
    public static void main(String[] args) {
/*
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

        f1.CrearBasico('+'); f1.setToken(10);
        f2.CrearBasico('-'); f2.setToken(20);
        f3.CrearBasico('*'); f3.setToken(30);
        f4.CrearBasico('/'); f4.setToken(40);
        f5.CrearBasico('^'); f5.setToken(50);
        f6.CrearBasico('('); f6.setToken(60);
        f7.CrearBasico(')'); f7.setToken(70);
        f8.CrearBasico("SIN"); f8.setToken(80);
        f9.CrearBasico("COS"); f9.setToken(90);
        f10.CrearBasico("TAN"); f10.setToken(100);
        f11.CrearBasico("EXP"); f11.setToken(110);
        f12.CrearBasico("LOG"); f12.setToken(120);
        f13.CrearBasico("LN"); f13.setToken(130);

        f14.CrearBasico(0, 9);
        f14.CerraduraPositiva();

        f15.CrearBasico('.');
        f16.CrearBasico(0, 9);
        f16.CerraduraPositiva();
        f15.Concatenar(f16);

        f14.Concatenar(f15);
        f15.CrearBasico(0, 9);
        f15.CerraduraPositiva();
        f14.Unir(f15); f14.setToken(140);

        conjuntoAFN.add(f1);
        conjuntoAFN.add(f2);
        conjuntoAFN.add(f3);
        conjuntoAFN.add(f4);
        conjuntoAFN.add(f5);
        conjuntoAFN.add(f6);
        conjuntoAFN.add(f7);
        conjuntoAFN.add(f8);
        conjuntoAFN.add(f9);
        conjuntoAFN.add(f10);
        conjuntoAFN.add(f11);
        conjuntoAFN.add(f12);
        conjuntoAFN.add(f13);
        conjuntoAFN.add(f14);

        AFD a = new AFD();
        try {
            a.convertirAFD(conjuntoAFN, "Calculadora");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//
//        String nombreArchivo = "Gramatica1.txt";
//        try {
//            LR0AUX LR = new LR0AUX(nombreArchivo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
