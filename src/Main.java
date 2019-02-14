import classes.AFD;
import classes.AFN;
import draw.Draw;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        HashSet<AFN> conjuntoAFN = new HashSet<>();
        Draw D = new Draw();
        AFN f1, f2, f3, f4, f5, f6, f7, f8;
        f1 = new AFN();
        f2 = new AFN();
        f3 = new AFN();
        f4 = new AFN();
        f5 = new AFN();
        f6 = new AFN();
        f7 = new AFN();
        f8 = new AFN();

        /*
        f1.CrearBasico('S');
        f1.CerraduraOpcional();
        f2.CrearBasico('D');
        f2.CerraduraPositiva();
        f1.Concatenar(f2);

        conjuntoAFN.add(f1);


        f3.CrearBasico('S');
        f3.CerraduraOpcional();
        f4.CrearBasico('D');
        f4.CerraduraPositiva();
        f3.Concatenar(f4);
        f4.CrearBasico('.');
        f3.Concatenar(f4);
        f4.CrearBasico('D');
        f4.CerraduraPositiva();
        f3.Concatenar(f4);

        conjuntoAFN.add(f3);


        f5.CrearBasico('L');
        f6.CrearBasico('L');
        f7.CrearBasico('D');
        f6.Unir(f7);
        f6.CerraduraEstrella();
        f5.Concatenar(f6);

        conjuntoAFN.add(f5);

        f8.CrearBasico('T');
        f8.CerraduraEstrella();

        conjuntoAFN.add(f8);

        AFD adf1 = new AFD();
        adf1.convertirAFD(conjuntoAFN);
        */

        /*1*/
        f1.CrearBasico('A');
        f2.CrearBasico('B');
        f1.Unir(f2);
        f1.CerraduraPositiva();

        f3.CrearBasico('C');
        f3.CerraduraEstrella();
        f4.CrearBasico('D');
        f4.CerraduraPositiva();
        f3.Concatenar(f4);
        f1.Concatenar(f3);

        conjuntoAFN.add(f1);


        //System.out.println(f1.ImprimeAFN());

        /*2*/
        f2.CrearBasico('A');
        f3.CrearBasico('B');
        f3.CerraduraEstrella();
        f2.Concatenar(f3);

        f4.CrearBasico('C');
        f5.CrearBasico('A');
        f5.CerraduraPositiva();
        f4.Concatenar(f5);
        f2.Concatenar(f4);

        conjuntoAFN.add(f2);

        /*3*/
        f3.CrearBasico('C');
        f3.CerraduraPositiva();
        f4.CrearBasico('B');
        f4.CerraduraPositiva();
        f3.Concatenar(f4);

        f4.CrearBasico('C');
        f5.CrearBasico('D');
        f4.Unir(f5);
        f4.CerraduraEstrella();

        f3.Concatenar(f4);

        conjuntoAFN.add(f3);

        /*4*/
        f4.CrearBasico('C');
        f5.CrearBasico('C');

        f4.Concatenar(f5);
        f5.CrearBasico('C');

        f4.Concatenar(f5);
        f5.CrearBasico('B');

        f5.CerraduraPositiva();

        f4.Concatenar(f5);

        f5.CrearBasico('A');
        f5.CerraduraPositiva();
        f6.CrearBasico('B');
        f5.Unir(f6);

        f4.Concatenar(f5);
        conjuntoAFN.add(f4);

        //D.Dibuja(f4.DibujarAFN());
        AFD afd = new AFD();

        /*abbacddabbcaaaccbbdccdcccbbbd*/
        afd.convertirAFD(conjuntoAFN);



    }
}
