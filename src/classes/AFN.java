package classes;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/* CREA AUTÓMATAS DE THOMPSON */
public class AFN {

    /* AFN */
    private static int ID = 0; //static int para que no se repitan
    private static final char Epsilon = '&'; // Mi epsi ;)
    private static int TOKEN = 10;

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
        nuevoOrigen.Transiciones.pushTransicion(Epsilon, this.EstadoInicial);
        nuevoOrigen.Transiciones.pushTransicion(Epsilon, B.EstadoInicial);

        /* AGREGAMOS EL QUE CREAMOS (NUEVOORIGEN) */
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

    public AFN Concatenar(AFN B) {

        /* QUITAMOS LOS ESTADOS DE ACEPTACIÓN */
        for (Estado i : this.EstadosAceptacion) {
            i.setEstadoAceptacion(false);

            /*GUARDAMOS LAS TRANSICIONES DEL ESTADO INICIAL DE B EN THIS*/
            i.Transiciones.uneTransiciones(B.EstadoInicial.Transiciones);
        }

        /* BORRAMOS EL CONJUNTO DE ESTADOS DE ACEPTACIÓN */
        this.EstadosAceptacion.clear();

        /* AÑADIMOS UN ÚNICO ESTADO DE ACEPTACIÓN */
        this.EstadosAceptacion.addAll(B.EstadosAceptacion);

        /* REALIZAMOS LA UNIÓN */
        this.Alfabeto.addAll(B.Alfabeto); // Unión de alfabetos
        B.Estados.remove(B.EstadoInicial); //Correción importante
        this.Estados.addAll(B.Estados); // Unión de estados

        /* LIMPIAMOS EL AUTOMATA B */
        B.Alfabeto.clear();
        B.Estados.clear();
        B.setEstadoInicial(new Estado());
        B.EstadosAceptacion.clear();

        return this;
    }

    public HashSet<Estado> CerraduraEpsilon(Estado E) {

        /* CONJUNTO DE ESTADOS */
        HashSet<Estado> C = new HashSet<>();

        /* PILA PARA ALAMACENAR A LOS QUE ALCANZA CON EPSILON */
        Stack<Estado> S = new Stack<>();

        /* INGRESAMOS EL PRIMER ESTADO A ANALIZAR */
        S.add(E);
        C.add(E);

        Estado K;
        while (!S.empty()) {
            K = S.pop();
            if (!C.contains(K)) {

                /* PREGUNTAMOS SI TIENE TRANSICIONES EPSILON */
                HashSet<Pair<Character, Estado>> t = K.Transiciones.getTransiciones();
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

        return C;
    }

    public AFN CerraduraPositiva() {

        /* AÑADIMOS UNA CERRADURA DEL ESTADO DE ACEPTACIÓN AL ESTADO INICIAL */
        for (Estado i : this.EstadosAceptacion) {
            i.Transiciones.pushTransicion(Epsilon, this.EstadoInicial);
        }

        /* CREAMOS UN NUEVO ESTADO ORIGEN */
        Estado nuevoOrigen = new Estado();
        nuevoOrigen.setID(ID++);
        nuevoOrigen.Transiciones.pushTransicion(Epsilon, this.EstadoInicial);
        this.Estados.add(nuevoOrigen);
        this.EstadoInicial = nuevoOrigen;

        /* CREAMOS UN NUEVO ESTADO DESTINO */
        Estado nuevoDestino = new Estado();
        nuevoDestino.setID(ID++);
        nuevoDestino.setEstadoAceptacion(true);
        this.Estados.add(nuevoDestino);

        for (Estado i : this.EstadosAceptacion) {
            i.setEstadoAceptacion(false);
            i.Transiciones.pushTransicion(Epsilon, nuevoDestino);
        }
        this.EstadosAceptacion.clear();
        this.EstadosAceptacion.add(nuevoDestino);

        return this;
    }

    public AFN Opcional() {// ?

        /* CREAMOS UN NUEVO ESTADO ORIGEN */
        Estado nuevoOrigen = new Estado();
        nuevoOrigen.setID(ID++);
        nuevoOrigen.Transiciones.pushTransicion(Epsilon, this.EstadoInicial);
        this.Estados.add(nuevoOrigen);
        this.EstadoInicial = nuevoOrigen;

        /* CREAMOS UN NUEVO ESTADO DESTINO */
        Estado nuevoDestino = new Estado();
        nuevoDestino.setID(ID++);
        nuevoDestino.setEstadoAceptacion(true);
        this.Estados.add(nuevoDestino);

        for (Estado i : this.EstadosAceptacion) {
            i.setEstadoAceptacion(false);
            i.Transiciones.pushTransicion(Epsilon, nuevoDestino);
        }
        this.EstadosAceptacion.clear();
        this.EstadosAceptacion.add(nuevoDestino);


        /* SÓLO AÑADIMOS UNA TRANSICIÓN DEL ESTADO INICIAL AL ESTADO DE ACEPTACIÓN */
        for (Estado i : this.EstadosAceptacion) {
            this.EstadoInicial.Transiciones.pushTransicion(Epsilon, i);
        }

        return this;
    }

    public AFN CerraduraEstrella() {
        return this;
    }


    public AFN CerraduraKleen() {
        return this;
    }

    /* GETTERS AND SETTERS *///////////////////////////////////////////////////////
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

    public String imprimeAFN() {
        /* ALMACENAMOS TODO EN UN STRING */
        String cadena = "";

        /* IMPRIMIMOS LOS ESTADOS ACCEDIENDO AL HASHSET Y SACANDO CADA ESTADO*/
        cadena += ("Alfabeto: " + this.Alfabeto + "\n");
        cadena += ("Estado inicial: " + this.EstadoInicial.getID() + "\n");
        cadena += ("Estados de aceptacion: ");

        /* IMPRIMIMOS LOS ESTADOS DE ACEPTACIÓN ACCEDIENDO AL HASHSET Y SACANDO CADA ESTADO */
        Iterator it = EstadosAceptacion.iterator();

        Estado E;
        while (it.hasNext()) {
            E = (Estado) it.next();
            cadena += (E.getID() + " \n");
        }

        cadena += "\nTransiciones: \n";
        it = Estados.iterator();
        while (it.hasNext()) {
            E = (Estado) it.next();
            cadena += ("Estado: " + E.getID() + "\n");
            cadena += E.imprimeTransiciones();
            cadena += "\n";
        }
        cadena += "\n";

        return cadena;
    }
    //////////////////////////////////////////////////////////////////////////////
}