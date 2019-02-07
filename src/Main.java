import classes.AFN;
import interfaces.InterfazPrimaria;

public class Main {

    public static void main(String[] args) {

        AFN f1, f2, f3, f4, f5, f6, f7, f8;
        f1 = new AFN();
        f2 = new AFN();
        f3 = new AFN();
        f4 = new AFN();
        f5 = new AFN();
        f6 = new AFN();
        f7 = new AFN();
        f8 = new AFN();



        f1.crearBasico('S');
        f1.Opcional();
        f2.crearBasico('D');
        f2.CerraduraPositiva();
        f1.Concatenar(f2);

        System.out.println(f1.imprimeAFN());

        f3.crearBasico('S');
        f3.Opcional();
        f4.crearBasico('D');
        f4.CerraduraPositiva();
        f3.Concatenar(f4);
        f4.crearBasico('.');
        f3.Concatenar(f4);
        f4.crearBasico('D');
        f4.CerraduraPositiva();
        f3.Concatenar(f4);

        System.out.println(f3.imprimeAFN());

        f5.crearBasico('L');
        f6.crearBasico('L');
        f7.crearBasico('D');
        f6.Unir(f7);
        f6.CerraduraEstrella();
        f5.Concatenar(f6);
        System.out.println(f5.imprimeAFN());

        f8.crearBasico('T');
        f8.CerraduraEstrella();
        System.out.println(f8.imprimeAFN());


        /*
        System.out.println("Probando cerradura epsilon con f1...");
        HashSet<Estado> C = new HashSet<>();
        C.addAll(f1.CerraduraEpsilon(f1.getEstadoInicial()));

        for (Estado i : C) {
            System.out.println(i.imprimeTransiciones());
        }
        */

    }

}
