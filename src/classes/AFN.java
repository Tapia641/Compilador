package classes;

import java.util.HashSet;
import java.util.Iterator;

/* CREA AUTÓMATAS DE THOMPSON */
public class AFN {

    /* AFN */
    private static int ID = 0; //static int para que no se repitan
    private static final char Epsilon = 245; // En ASCII

    /* ESTADOS */
    private HashSet<Estado> Estados;
    private Estado EstadoInicial;
    private HashSet<Estado> EstadosAceptacion;

    /* ALFABETO CONFORMADO DE TIPO CARÁCTER */
    private HashSet<Character> Alfabeto;

    public HashSet<Transicion> conjuntoTransiciones;

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

        /* AÑADIMOS LA TRANSICION DEL S -> (ID) AL ESTADO DE ACEPTACIÓN */
        Transicion T = new Transicion();
        T.pushTransicion(S, Destino);
        EstadosAceptacion.add(Destino);

        // SI EL CARÁCTER NO ESTÁ EN EL ALFABETO, LO AÑADIMOS
        if (!Alfabeto.contains(S))
            Alfabeto.add(S);

        return this;
    }

    public AFN Unir(AFN B) {
        /* CREAMOS UN NUEVO ESTADO ORIGEN */
        Estado nuevoOrigen = new Estado();
        nuevoOrigen.setID(ID++);
        this.EstadoInicial = nuevoOrigen;

        /* CREAMOS UN NUEVO ESTADO DESTINO */
        for (Estado i : this.EstadosAceptacion) {
            i.setEstadoAceptacion(false);
        }
        Estado nuevoDestino = new Estado();
        nuevoDestino.setID(ID++);
        nuevoDestino.setEstadoAceptacion(true);
        this.EstadosAceptacion.add(nuevoDestino);


        /*  */
        Transicion T = new Transicion();
        T.pushTransicion(Epsilon, this.EstadoInicial);
        T.pushTransicion(Epsilon, B.EstadoInicial);
        this.Alfabeto.add(Epsilon);

        B.EstadoInicial = null;

        /* QUITAMOS ESTADOS INICIALES DE AMBOS AUTÓMATAS*/


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

        System.out.println("Alfabeto: " + this.Alfabeto + "\n");

        System.out.println("Transiciones: " + "" + "\n");
    }
    //////////////////////////////////////////////////////////////////////////////
}