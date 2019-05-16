import Clases.AFD;
import Clases.AFN;
import Clases.AnalizadorLexico;
import Clases.AnalizadorSintactico.AritmeticaBasica;
import Clases.AnalizadorSintactico.Calculadora;
import Clases.AnalizadorSintactico.ExpresionesRegulares;
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

        //GRAMATICA DE EXPRESIONES REGULARES
/*
        AFD afd1 = new AFD();

        f1.CrearBasico('&');  f1.setToken(Tokens.ER_CONCA);
        f2.CrearBasico("|"); f2.setToken(Tokens.ER_OR);
        f3.CrearBasico('+');  f3.setToken(Tokens.ER_CERRADURA_POSTIVIA);
        f4.CrearBasico('*');  f4.setToken(Tokens.ER_CERRADURA_ESTRELLA);
        f5.CrearBasico('A','Z'); f5.setToken(Tokens.ER_SIMBOLO);
        f6.CrearBasico('?'); f6.setToken(Tokens.ER_THOMPSON);
        f7.CrearBasico('('); f7.setToken(Tokens.ER_PAR_I);
        f8.CrearBasico(')'); f8.setToken(Tokens.ER_PAR_D);

        conjuntoAFN.add(f1);
        conjuntoAFN.add(f2);
        conjuntoAFN.add(f3);
        conjuntoAFN.add(f4);
        conjuntoAFN.add(f5);
        conjuntoAFN.add(f6);
        conjuntoAFN.add(f7);
        conjuntoAFN.add(f8);


        try {
            afd1.convertirAFD(conjuntoAFN,"ExpresionesRegulares");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String cadena = "(A|B)*&C+";
        AnalizarLexicamente.Lexico(cadena, afd.getMatriz());
        ER.AnalizarSintacticamente(AnalizarLexicamente.getPila(),AnalizarLexicamente.getTablaLexema());
*/
/*
    //Para convertir una AFD a un .out
        AFD afd = new AFD();
        try {
            afd.convertirAFD(conjuntoAFN,"AritmeticaBasica");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

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

        /*
        AFD afd1 = new AFD();
        AritmeticaBasica AB = new AritmeticaBasica();
        AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();

        try {
            //EXPORTAMOS EL AFN CONVERTIDO A AFD EN UN HASHTABLE
            //afd.convertirAFD(conjuntoAFN);
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
