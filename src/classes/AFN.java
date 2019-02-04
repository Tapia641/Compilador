package classes;

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

    /* CONSTRUCTOR */
    public AFN() {
        Estados = new HashSet<>();
        EstadosAceptacion = new HashSet<>();
        Alfabeto = new HashSet<>();
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

        /* AÑADIMOS LA TRANSICION DEL (Origen) -> S -> (DESTINO) AL ESTADO DE ACEPTACIÓN */
        Origen.Transiciones.pushTransicion(S, Destino);
        EstadosAceptacion.add(Destino);

        /* SI EL CARÁCTER NO ESTÁ EN EL ALFABETO, LO AÑADIMOS */
        if (!Alfabeto.contains(S))
            Alfabeto.add(S);

        return this;
    }

    public AFN Unir(AFN B) {

        /* CREAMOS UN NUEVO ESTADO ORIGEN */
        Estado nuevoOrigen = new Estado();
        nuevoOrigen.setID(ID++);

        /* CREAMOS LAS NUEVAS TRANSICIONES DEL ESTADO INICIAL CON EPSILON
         * A LOS ESTADOS INICIALES DE AMBOS AUTÓMATAS */
        nuevoOrigen.Transiciones.pushTransicion(Epsilon, this.EstadoInicial); //falla
        nuevoOrigen.Transiciones.pushTransicion(Epsilon, B.EstadoInicial);

        this.EstadoInicial = nuevoOrigen;
        this.Alfabeto.add(Epsilon);
        this.Estados.add(nuevoOrigen);

        /* CREAMOS UN NUEVO ESTADO DESTINO */
        Estado nuevoDestino = new Estado();
        nuevoDestino.setID(ID++);
        nuevoDestino.setEstadoAceptacion(true);

        /* QUITAMOS ESTADOS DE ACEPTACIÓN DE AMBOS AUTÓMATAS */
        for (Estado i : this.EstadosAceptacion) {
            /* QUITAMOS LOS ANTERIORES */
            i.setEstadoAceptacion(false);
            i.Transiciones.pushTransicion(Epsilon, nuevoDestino);
        }

        for (Estado i : B.EstadosAceptacion) {
            /* QUITAMOS LOS ANTERIORES */
            i.setEstadoAceptacion(false);
            i.Transiciones.pushTransicion(Epsilon, nuevoDestino);
        }

        /* BORRAMOS EL CONJUNTO DE ESTADOS DE ACEPTACIÓN */
        this.EstadosAceptacion.clear();

        /* AÑADIMOS UN ÚNICO ESTADO DE ACEPTACIÓN */
        this.EstadosAceptacion.add(nuevoDestino);
        this.Estados.add(nuevoDestino);

        /* REALIZAMOS LA UNIÓN */
        this.Alfabeto.addAll(B.Alfabeto); // Unión de alfabetos
        this.Estados.addAll(B.Estados); // Unión de estados

        /* LIMPIAMOS EL AUTOMATA B */
        B.Alfabeto.clear();
        B.Estados.clear();
        B.setEstadoInicial(new Estado());
        B.EstadosAceptacion.clear();

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
        System.out.println("Alfabeto: " + this.Alfabeto);
        System.out.println("Estado inicial: " + this.EstadoInicial.getID());
        System.out.print("Estados de aceptacion: ");

        /* IMPRIMIMOS LOS ESTADOS DE ACEPTACIÓN ACCEDIENDO AL HASHSET Y SACANDO CADA ESTADO */
        Iterator it = Estados.iterator();
        Estado E;
        it = EstadosAceptacion.iterator();
        while (it.hasNext()) {
            E = (Estado) it.next();
            System.out.print(E.getID() + " ");
        }

        System.out.println("\nTransiciones: ");
        it = Estados.iterator();
        while (it.hasNext()) {
            E = (Estado) it.next();
            System.out.print("Estado: " + E.getID() + "\n");
            E.imprimeTransiciones();
            System.out.print("\n");
        }
        System.out.print("\n");





        /*it = conjuntoTransiciones.iterator();
        Transicion Transiciones;
        Pair<Character, Estado> P;
        while (it.hasNext()) {
            Transiciones = (Transicion) it.next();
            Transiciones.imprimeTransiciones();
        }
        */
        System.out.print("\n");
    }
    //////////////////////////////////////////////////////////////////////////////
}