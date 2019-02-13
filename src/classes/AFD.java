package classes;

import draw.Draw;
import javafx.util.Pair;

import java.util.*;

/* CREAMOS AUTÓMATAS TIPO AFD */
public class AFD {

    /* AFD */
    private static int ID = 0; //static int para que no se repitan
    private static final char Epsilon = '&'; // Mi epsi ;)
    private static long TOKEN = 10; // Pensando como ponerlo

    /* ESTADOS */
    private HashSet<Estado> Estados;
    private Estado EstadoInicial;
    private HashSet<Estado> EstadosAceptacion;

    /* ALFABETO CONFORMADO DE TIPO CARÁCTER */
    private HashSet<Character> Alfabeto;

    /* CONSTRUCTOR */
    public AFD() {
        Estados = new HashSet<>();
        EstadoInicial = new Estado();
        EstadosAceptacion = new HashSet<>();
        Alfabeto = new HashSet<>();
    }

    public AFD convertirAFD(HashSet<AFN> conjuntoAFN) {

        /* CREAMOS UN NUEVO ORIGEN PARA UNIR TODOS LOS AFN */
        Estado nuevoOrigen = new Estado();
        nuevoOrigen.setID(36);//Nota: resolver el id

        /* SÓLO AÑADIMOS UN ESTADIO DE ORIGEN CON TRANSICIONES EPSILON
         * PARA UNIR TODOS LOS AUTÓMATAS */
        for (AFN i : conjuntoAFN) {
            nuevoOrigen.Transiciones.pushTransicion(Epsilon, i.getEstadoInicial());
            this.Estados.addAll(i.getEstados());
            this.EstadosAceptacion.addAll(i.getEstadosAceptacion());
            this.Alfabeto.addAll(i.getAlfabeto());
        }

        this.Estados.add(nuevoOrigen);
        this.EstadoInicial = nuevoOrigen;

        /* COMENZAMOS A CONVERTIR */

        /* APLICAMOS LA CERRADURA EPSILON AL ESTADO INICIAL DEL CONJUNTO */
        HashSet<Estado> conjuntoEpsilon;
        conjuntoEpsilon = this.CerraduraEpsilon(this.EstadoInicial);

        Draw D = new Draw();
        D.Dibuja(this.DibujarAFD());

        return this;
    }

    public AFD convertirAFD(AFN f) {
        /* S0 = se calcula la cerradura epsilon del estado inicial
         *  Metemos Q.add(S0) a la cola;
         *  Calculamos el Ir_A(S0,Cada elemento del Alfabeto)
         *  S1 = Ie_A(S0,Alfabeto[i]) ¿S1 == S0? si no Q.add(S1)
         *  así sucesivamente hasta terminar con los elementos del
         *  alfabeto. Hasta terminar con S0, entonces sacamos S0.
         *  Se saca otro elemento de la cola S1 con cada elemento del
         *  alfabeto.
         * */

        /* LA COLA QUE LLEVARÁ LOS Sj*/
        Queue<HashSet<Estado>> Q = new ArrayDeque<>();
        HashSet<Estado> S, SAux;

        /* LLEVARÁ EL ORDEN DE LA LISTA */
        LinkedList<Vector<Integer>> ListaEnlazada = new LinkedList<>();

        /* FILA PARA LA LISTA*/
        Vector<Integer> Fila = new Stack<>();
        int Indice = 0;

        /* CALCULAMOS LA CERRADURA EPSILON AL ESTADO INICIAL */
        S = this.CerraduraEpsilon(f.getEstadoInicial());
        Q.add(S);

        /* MIENTRAS NO TERMINEMOS DE ANALIZAR CADA S */
        while (!Q.isEmpty()) {

            /* SACAMAOS UN ELEMENTO DE LA COLA */
            S = ((ArrayDeque<HashSet<Estado>>) Q).pop();
            Indice++;

            /* ITERAMOS SOBRE EL ALFABATEO */
            for (Character i : f.getAlfabeto()) {

                /* CALCULAMOS EL IR_A A CADA ELEMENTO DEL ALFABETO*/
                SAux = Ir_A(S, i);

                /* SI SON DISTINTOS LO AÑADIMOS A LA COLA */
                if (!S.equals(SAux)) {
                    Q.add(SAux);
                }
            }
        }

        return this;
    }

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

    public HashSet<Estado> Ir_A(HashSet<Estado> conjuntoEstados, char S) {

        /*IR_A({},S) : CerraduraEpsilon(Mover({i},S))*/

        /* CREAMOS LOS CONJUNTOS DE ALMACENAMIENTO */
        HashSet<Estado> C = new HashSet<>();
        HashSet<Estado> M = new HashSet<>();

        /* CALCULAMOS EL MOVER({i},S)*/
        for (Estado i : conjuntoEstados) {
            M.addAll(Mover(i, S));
        }

        /* CALCULAMOS LA CERRADURAEPSILON(MOVER({i},S)) */
        for (Estado i : M) {
            C.addAll(CerraduraEpsilon(i));
        }

        return C;
    }

    public HashSet<Estado> Ir_A(Estado E, char S) {

        /*IR_A(E,S) : CerraduraEpsilon(Mover(E,S))*/

        /* CREAMOS LOS CONJUNTOS DE ALMACENAMIENTO */
        HashSet<Estado> C = new HashSet<>();
        HashSet<Estado> M = new HashSet<>();

        /* CALCULAMOS EL MOVER(E,S)*/
        M.addAll(Mover(E, S));

        /* CALCULAMOS LA CERRADURAEPSILON(MOVER(E,S)) */
        for (Estado i : M) {
            C.addAll(CerraduraEpsilon(i));
        }

        return C;
    }

    public HashSet<Estado> Mover(HashSet<Estado> conjuntoEstados, char S) {

        /* CONJUNTO DE ESTADOS A LOS CUALES HAY TRANSICIÓN CON E -> S */
        HashSet<Estado> C = new HashSet<>();

        /* ITERAMOS SOBRE LAS TRANSICIONES DE CONJUNTO */
        for (Estado i : conjuntoEstados) {

            /* ITERAMOS SOBRE LAS TRANSICIONES DE E */
            for (Pair<Character, Estado> P : i.getTransiciones()) {

                /* SI HAY UNA TRANSICIÓN A S */
                if (P.getKey().equals(S)) {

                    /* AÑADIMOS EL ESTADO */
                    C.add(P.getValue());
                }
            }
        }

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

    public AFD SetTOKENAFD() {

        /* PONEMOS UN TOKEN DIFERENTE A CADA ESTADO DE ACEPTACIÓN */
        for (Estado i : this.EstadosAceptacion) {
            TOKEN += 10;
            i.setTOKEN(TOKEN);
        }

        return this;
    }

    public String DibujarAFD() {
        String cadena = "";

        /* FORMATO DEL DIBUJO */
        cadena += "node [shape=circle];\n" +
                "node [style=filled];\n" +
                "node [fillcolor=\"#EEEEEE\"];\n" +
                "node [color=\"#EEEEEE\"];\n" +
                "edge [color=\"#31CEF0\"];\n";

        /* ESTRUCTURA PARA .DOT */
        //"EstadoOrigen -> EstadoDestino [label=\"Transicion\"];\n"

        /* IMPRIMIMOS LOS ESTADOS DE ACEPTACIÓN ACCEDIENDO AL HASHSET Y SACANDO CADA ESTADO */
        Iterator it = Estados.iterator();
        Estado E;

        HashSet<Pair<Character, Estado>> t;

        while (it.hasNext()) {
            E = (Estado) it.next();
            for (Pair<Character, Estado> i : E.getTransiciones()) {
                cadena += (E.getID() + " -> " + i.getValue().getID() + " [label=\"" + i.getKey() + "\"];\n");
            }
        }

        /* FIN DE FORMATO*/
        cadena += "rankdir=LR;\n";

        return cadena;
    }

    /* GETTERS AND SETTERS *////////////////////////////////////////////////
    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        AFD.ID = ID;
    }

    public static char getEpsilon() {
        return Epsilon;
    }

    public static long getTOKEN() {
        return TOKEN;
    }

    public static void setTOKEN(long TOKEN) {
        AFD.TOKEN = TOKEN;
    }

    public HashSet<Estado> getEstados() {
        return Estados;
    }

    public void setEstados(HashSet<Estado> estados) {
        Estados = estados;
    }

    public Estado getEstadoInicial() {
        return EstadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        EstadoInicial = estadoInicial;
    }

    public HashSet<Estado> getEstadosAceptacion() {
        return EstadosAceptacion;
    }

    public void setEstadosAceptacion(HashSet<Estado> estadosAceptacion) {
        EstadosAceptacion = estadosAceptacion;
    }

    public HashSet<Character> getAlfabeto() {
        return Alfabeto;
    }

    public void setAlfabeto(HashSet<Character> alfabeto) {
        Alfabeto = alfabeto;
    }
    /////////////////////////////////////////////////////////////////////////////
}