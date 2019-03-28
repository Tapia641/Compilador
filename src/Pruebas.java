import Clases.AFD;
import Clases.AFN;
import Clases.DescensoRecursivo.AritmeticaBasica;
import Clases.DescensoRecursivo.Calculadora;

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

/*
        AFD afd = new AFD();
        try {
            afd.convertirAFD(conjuntoAFN,"AritmeticaBasica");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


        AFD afd = new AFD();
        Calculadora C = new Calculadora();
        try {
            //afd.convertirAFD(conjuntoAFN);
            afd.LeerObject("Calculadora");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        C.Pertenece("4.5*9+SIN(17-14/8)*6", afd.getMatriz());

/*
        AFD afd = new AFD();
        AritmeticaBasica AB = new AritmeticaBasica();
        try {
            //afd.convertirAFD(conjuntoAFN);
            afd.LeerObject("AritmeticaBasica");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        AB.Pertenece("2.8+76/(19-14.5)", afd.getMatriz());
*/
    }
}
