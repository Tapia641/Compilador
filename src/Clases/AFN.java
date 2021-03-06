package Clases;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Iterator;

/* CREA AUTÓMATAS DE THOMPSON */
public class AFN {

    /* AFN */
    private static int ID = 0; //static int para que no se repitan
    private static final char Epsilon = '&'; // Mi epsi ;)
    private static int TOKEN;

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
    public AFN CrearBasico(char S) {

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


    /* CREA UN AFN SIMPLE */
    public AFN CrearBasico(String S) {

        AFN f1;
        f1 = new AFN();
        char[] Aux = S.toCharArray();
        this.CrearBasico(Aux[0]);
        System.out.println(Aux[0]);
        for (int i = 1; i < S.length(); i++) {
            f1.CrearBasico(Aux[i]);
            System.out.println(Aux[i]);
            this.Concatenar(f1);
        }
        return this;
    }


    /* CREA UN AFN SIMPLE */
    public AFN CrearBasico(char S, char P) {

        if (S < P) {
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
            for (int i = S; i <= P; i++) {
                Origen.Transiciones.pushTransicion((char) i, Destino);

                /* SI EL CARÁCTER NO ESTÁ EN EL ALFABETO, LO AÑADIMOS */
                if (!Alfabeto.contains((char) i))
                    Alfabeto.add((char) i);
            }
            EstadosAceptacion.add(Destino);


        } else {
            CrearBasico(S);
        }
        return this;
    }


    /* CREA UN AFN SIMPLE */
    public AFN CrearBasico(int a, int b) {

        if (a < b) {
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
            for (int i = a; i <= b; i++) {
                Origen.Transiciones.pushTransicion(Integer.toString(i).charAt(0), Destino);

                /* SI EL CARÁCTER NO ESTÁ EN EL ALFABETO, LO AÑADIMOS */
                if (!Alfabeto.contains(Integer.toString(i).charAt(0)))
                    Alfabeto.add(Integer.toString(i).charAt(0));

            }
            EstadosAceptacion.add(Destino);

        } else {
        }
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

    public AFN CerraduraOpcional() {// ?

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
        this.EstadoInicial.Transiciones.pushTransicion(Epsilon, nuevoDestino);

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

    public String ImprimeAFN() {
        /* ALMACENAMOS EN UN STRING*/
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

    public String DibujarAFN() {
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
    //////////////////////////////////////////////////////////////////////////////


    public int getToken() {
        return TOKEN;
    }

    public void setToken(int TOKEN) {
        for (Estado i : this.EstadosAceptacion) {
            i.setToken(TOKEN);
        }
    }
}