package Clases;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

public class LR0 {

    private static int ID = 0; //static int para que no se repitan
    private static final char Epsilon = '&'; // Mi epsi ;)
    private static int TOKEN; // Pensando como ponerlo


    public HashSet<Estado> CerraduraEpsilon(Estado E) { //Funciona

        /* CONJUNTO DE ESTADOS */
        HashSet<Estado> C = new HashSet<>();

        /* PILA PARA ALAMACENAR A LOS QUE ALCANZA CON EPSILON */
        Stack<Estado> S = new Stack<>();

        /* INGRESAMOS EL PRIMER ESTADO A ANALIZAR */
        S.add(E);

        Estado EstadoPrueba;

        while (!S.empty()) {

            /* TOMAMOS EL SIGUIENTE ELEMENTO */
            EstadoPrueba = S.pop();

            if (!C.contains(EstadoPrueba)) {

                /* AÑADIMOS EL ESTADO AL QUE SE LLEGÓ */
                C.add(EstadoPrueba);

                /* PREGUNTAMOS SI TIENE TRANSICIONES EPSILON */
                HashSet<Pair<Character, Estado>> t = EstadoPrueba.Transiciones.getTransiciones();
                Iterator it = t.iterator();
                Pair<Character, Estado> P;

                while (it.hasNext()) {

                    P = (Pair) it.next();

                    /* SI TIENE UNA TRANSICION EPSILON */
                    if (P.getKey() == Epsilon) {

                        /* AÑADIMOS EL ESTADO AL QUE ALCANZA */
                        S.add(P.getValue());
                    }
                }
            }
        }

        /* AÑADIMOS EL ESTADO CON EL QUE SE INICIÓ */
        C.add(E);

        return C;
    }

    public HashSet<Estado> Mover(Estado E, char S) {

        /* CONJUNTO DE ESTADOS A LOS CUALES HAY TRANSICIÓN CON E -> S */
        HashSet<Estado> C = new HashSet<>();

        /* ITERAMOS SOBRE LAS TRANSICIONES DE E */
        for (Pair<Character, Estado> P : E.getTransiciones()) {

            /* SI HAY UNA TRANSICIÓN A S */
            if (P.getKey().equals(S)) {

                /* AÑADIMOS EL ESTADO */
                C.add(P.getValue());
            }
        }

        return C;
    }

    public HashSet<Estado> Ir_A(HashSet<Estado> conjuntoEstados, char S) {

        /*IR_A({},S) : CerraduraEpsilon(Mover({i},S))*/

        /* CREAMOS LOS CONJUNTOS DE ALMACENAMIENTO */
        HashSet<Estado> C = new HashSet<>();
        HashSet<Estado> M = new HashSet<>();

        /* CALCULAMOS EL MOVER({i},S)*/
        int j = 0;
        for (Estado i : conjuntoEstados) {
            System.out.println("Mover(" + i.getID() + ", " + S + ")");
            M.addAll(Mover(i, S));
        }

        /* CALCULAMOS LA CERRADURAEPSILON(MOVER({i},S)) */
        for (Estado i : M) {
            System.out.println("C_E(" + i.getID() + ")");
            C.addAll(CerraduraEpsilon(i));
        }

        return C;
    }

}
