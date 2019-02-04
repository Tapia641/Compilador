package classes;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Iterator;

/* CREA AUTÓMATAS DE THOMPSON */
public class AFN {

    /* AFN */
    private static int ID = 0; //static int para que no se repitan
    private static final char Epsilon = '&'; // Mi epsi ;)

    /* ESTADOS */
    private HashSet<Estado> Estados;
    private Estado EstadoInicial;
    private HashSet<Estado> EstadosAceptacion;

    /* ALFABETO CONFORMADO DE TIPO CARÁCTER */
    private HashSet<Character> Alfabeto;

    /* TODAS LAS TRANSICIONES QUE TIENE EL ESTADO */
    private HashSet<Transicion> conjuntoTransiciones;

    /* CONSTRUCTOR */
    public AFN() {
        Estados = new HashSet<>();
        EstadosAceptacion = new HashSet<>();
        Alfabeto = new HashSet<>();
        conjuntoTransiciones = new HashSet<>();
    }

    /* CREA UN AFN SIMPLE */
    public AFN crearBasico(char S) {
        /* CREAMOS EL ESTADO ORIGEN */
        Estado Origen = new Estado();
        Origen.setID(ID++);
        EstadoInicial = Origen;

        /* TENEMOS UN NUEVO ESTADO PARA EL AFN (EL ORIGEN) */
        Estados.add(Origen);

        /* CREAMOS EL ESTADO DE DESTINO (ACEPTACIÓN) */
        Estado Destino = new Estado();
        Destino.setID(ID++);
        Destino.setEstadoAceptacion(true);

        /* TENEMOS UN NUEVO ESTADO PARA EL AFN (EL DESTINO) */
        Estados.add(Destino);

        /* AÑADIMOS LA TRANSICION DEL S -> (ID) AL ESTADO DE ACEPTACIÓN */
        Transicion T = new Transicion();
        T.pushTransicion(S, Destino);
        EstadosAceptacion.add(Destino);
        conjuntoTransiciones.add(T);

        /* SI EL CARÁCTER NO ESTÁ EN EL ALFABETO, LO AÑADIMOS */
        if (!Alfabeto.contains(S))
            Alfabeto.add(S);

        return this;
    }

    public AFN Unir(AFN B) {
        for (Estado i : this.EstadosAceptacion) {
            /* QUITAMOS LOS ANTERIORES */
            i.setEstadoAceptacion(false);
        }

        for (Estado i : B.EstadosAceptacion) {
            /* QUITAMOS LOS ANTERIORES */
            i.setEstadoAceptacion(false);
        }

        /* QUITAMOS ESTADOS DE ACEPTACIÓN DE AMBOS AUTÓMATAS */
        this.EstadosAceptacion.clear();
        B.EstadosAceptacion.clear();

        Transicion T = new Transicion();

        /* CREAMOS LAS NUEVAS TRANSICIONES DEL ESTADO INICIAL CON EPSILON
         * A LOS ESTADOS INICIALES DE AMBOS AUTÓMATAS */
        T.pushTransicion(Epsilon, this.EstadoInicial);
        T.pushTransicion(Epsilon, B.EstadoInicial);
        this.Alfabeto.add(Epsilon);

        this.conjuntoTransiciones.add(T);
        this.conjuntoTransiciones.addAll(B.conjuntoTransiciones);

        /* CREAMOS UN NUEVO ESTADO ORIGEN */
        Estado nuevoOrigen = new Estado();
        nuevoOrigen.setID(ID++);
        this.EstadoInicial = nuevoOrigen;
        this.Estados.add(nuevoOrigen);

        /* CREAMOS UN NUEVO ESTADO DESTINO */
        Estado nuevoDestino = new Estado();
        nuevoDestino.setID(ID++);
        nuevoDestino.setEstadoAceptacion(true);
        this.EstadosAceptacion.add(nuevoDestino);
        this.Estados.add(nuevoDestino);

        this.Alfabeto.addAll(B.Alfabeto); // Unión de alfabetos
        this.Estados.addAll(B.Estados); // Unión de estados

        /* LIMPIAMOS EL AUTOMATA B */
        B.Alfabeto.clear();
        B.Estados.clear();
        B.setEstadoInicial(new Estado());
        B.conjuntoTransiciones.clear();

        return this;
    }

    public void Opcional() {

    }

    public void CerraduraPositiva() {

    }

    public void CerraduraEstrella() {

    }

    public void Concatenar() {

    }

    /* GETTERS AND SETTERS*///////////////////////////////////////////////////////
    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        AFN.ID = ID;
    }

    public static char getEpsilon() {
        return Epsilon;
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

    public void imprimeAFN() {
        /* IMPRIMIMOS LOS ESTADOS ACCEDIENDO AL HASHSET Y SACANDO CADA ESTADO*/
        System.out.print("Estados: ");
        Iterator it = Estados.iterator();
        Estado E;
        while (it.hasNext()) {
            E = (Estado) it.next();
            System.out.print(E.getID() + ", ");
        }
        System.out.print("\n");

        System.out.println("Estado inicial: " + this.EstadoInicial.getID());

        /* IMPRIMIMOS LOS ESTADOS DE ACEPTACIÓN ACCEDIENDO AL HASHSET Y SACANDO CADA ESTADO */
        System.out.print("Estados de aceptacion: ");
        it = EstadosAceptacion.iterator();
        while (it.hasNext()) {
            E = (Estado) it.next();
            System.out.print(E.getID() + " ");
        }
        System.out.print("\n");

        System.out.println("Alfabeto: " + this.Alfabeto);

        System.out.println("Transiciones: ");
        it = conjuntoTransiciones.iterator();
        Transicion T;
        Pair<Character, Estado> P;
        while (it.hasNext()) {
            T = (Transicion) it.next();
            T.imprimeTransiciones();
        }
        System.out.print("\n");
    }
    //////////////////////////////////////////////////////////////////////////////
}