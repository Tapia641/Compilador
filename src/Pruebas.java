import Clases.AFD;
import Clases.AFN;
import Clases.AnalizadorLexico;
import Clases.LenguajeGramaticas;
import Dibujar.Draw;

import java.io.IOException;
import java.util.HashSet;

public class Pruebas {
    public static void main(String[] args) {

        HashSet<AFN> conjuntoAFN = new HashSet<>();
        Draw D = new Draw();
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
        */
/*//////////Aritmetica
        f1.CrearBasico('+');
        f2.CrearBasico('-');
        f1.Unir(f2);

        f1.CerraduraOpcional();

        f2.CrearBasico(0, 9);//D
        f2.CerraduraPositiva();

        f1.Concatenar(f2);
        //f1.setToken(10);
        conjuntoAFN.add(f1);

        ////////////////////////


        f3.CrearBasico('+');
        f4.CrearBasico('-');
        f3.Unir(f4);
        f3.CerraduraOpcional();

        f4.CrearBasico(0, 9);
        f4.CerraduraPositiva();
        f3.Concatenar(f4);

        ///////////////////////////

        f4.CrearBasico('.');
        f5.CrearBasico(0, 9);
        f5.CerraduraPositiva();
        f4.Concatenar(f5);

        f3.Concatenar(f4);
        //f3.setToken(20);
        conjuntoAFN.add(f3);

        /////////////////////


        f5.CrearBasico('A', 'Z');

        f6.CrearBasico('A', 'Z');
        f7.CrearBasico(0, 9);
        f6.Unir(f7);
        f6.CerraduraEstrella();

        f5.Concatenar(f6);
        //f3.setToken(30);
        conjuntoAFN.add(f5);

        ////   ///////////////////

        f8.CrearBasico(' ');
        f8.CerraduraPositiva();
        //f3.setToken(40);
        conjuntoAFN.add(f8);
*/////////////Aritmetica

/*
        f1.CrearBasico('A');
        f2.CrearBasico('B');
        f1.Unir(f2);

        f2.CrearBasico('C');
        f2.CerraduraEstrella();

        f1.CerraduraPositiva();
        f1.Concatenar(f2);
*/
        //D.Dibuja(f1.DibujarAFN());
        //conjuntoAFN.add(f1);

        //f1.CrearBasico(0,9);
        //conjuntoAFN.add(f1);

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


        //Procedemos a realizar el análisis
        AFD afd = new AFD();

        //  10      20      30          10
        /*abbacdd abbcaaa ccbbdccdccc bbbd*/
        try {
            afd.convertirAFD(conjuntoAFN);

        } catch (IOException e) {
            e.printStackTrace();
        }
        AnalizadorLexico Analizar = new AnalizadorLexico();
        //Analizar.Lexico("sddttdd.dtldlldtddt", afd.getMatriz());

        //Analizar.Lexico("abbacddabbcaaaccbbdccdcccbbbd", afd.getMatriz());
        //Analizar.Lexico("+17.56ART21  -16.4 1435", afd.getMatriz());

        Analizar.Lexico("2.8+76/(19-14.5)", afd.getMatriz());

        //LenguajeGramaticas LG = new LenguajeGramaticas();
        //LG.Iniciar();


    }
}
