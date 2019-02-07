import classes.AFN;
import classes.Estado;
import interfaces.InterfazPrimaria;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        AFN f1, f2;
        f1 = new AFN();
        f2 = new AFN();

        f1.crearBasico('A');
        f2.crearBasico('B');
        //f1.Unir(f2);
        //f2.crearBasico('C');
        f1.Concatenar(f2);

        System.out.println(f1.imprimeAFN());
        /*
        System.out.println("Probando cerradura epsilon con f1...");
        HashSet<Estado> C = new HashSet<>();
        C.addAll(f1.CerraduraEpsilon(f1.getEstadoInicial()));

        for (Estado i : C) {
            System.out.println(i.imprimeTransiciones());
        }

        /* MOSTRANDO INTERFAZ */
        /* InterfazPrimaria compilador = new InterfazPrimaria(); */

    }

}
