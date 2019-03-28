import Clases.AFD;
import Clases.AFN;
import Clases.DescensoRecursivo.AritmeticaBasica;
import java.io.IOException;
import java.util.HashSet;

public class Pruebas {
    public static void main(String[] args) {

        HashSet<AFN> conjuntoAFN = new HashSet<>();
        AFN f1, f2, f3, f4, f5, f6, f7, f8, f9;
        f1 = new AFN();
        f2 = new AFN();
        f3 = new AFN();
        f4 = new AFN();
        f5 = new AFN();
        f6 = new AFN();
        f7 = new AFN();
        f8 = new AFN();
        f9 = new AFN();
/*
        //Automata para la Aritmética Básica
        f1.CrearBasico('+');
        f1.setToken(10);
        f2.CrearBasico('-');
        f2.setToken(20);
        f3.CrearBasico('*');
        f3.setToken(30);
        f4.CrearBasico('/');
        f4.setToken(40);
        f5.CrearBasico('(');
        f5.setToken(50);
        f6.CrearBasico(')');
        f6.setToken(60);

        f7.CrearBasico(0, 9);
        f7.CerraduraPositiva();
        f8.CrearBasico('.');
        f9.CrearBasico(0, 9);
        f9.CerraduraPositiva();
        f8.Concatenar(f9);
        f7.Concatenar(f8);
        f8.CrearBasico(0, 9);
        f7.Unir(f8);
        f7.setToken(70);

        conjuntoAFN.add(f1);
        conjuntoAFN.add(f2);
        conjuntoAFN.add(f3);
        conjuntoAFN.add(f4);
        conjuntoAFN.add(f5);
        conjuntoAFN.add(f6);
        conjuntoAFN.add(f7);
        //Fin

*/
        AFD afd = new AFD();
        /*
        try {
            afd.convertirAFD(conjuntoAFN);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        /*
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
