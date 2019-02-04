import classes.AFN;
import interfaces.InterfazPrimaria;

public class Main {

    public static void main(String[] args) {

        AFN f1, f2;
        f1 = new AFN();
        f2 = new AFN();

        f1.crearBasico('A');
        f2.crearBasico('B');

        System.out.println("Imprimiendo...");
        System.out.println(f1.imprimeAFN());
        System.out.println(f2.imprimeAFN());


        System.out.println("Uniendo f1 con f2...");
        f1.Unir(f2);
        System.out.println(f1.imprimeAFN());

        /* MOSTRANDO INTERFAZ */
        /*
        InterfazPrimaria compilador = new InterfazPrimaria();

        /* PRIMERAS CONFIGURACIONES
        compilador.setSize(900, 700);
        compilador.setTitle(" PROYECTO PARA LA MATERIA DE COMPILADORES ");
        compilador.setLocationRelativeTo(null);

        //compilador.Operaciones();

        compilador.setVisible(true);
        System.exit(0);
        */

    }

}
