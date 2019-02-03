package classes;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

/* CREA AUTÓMATAS DE THOMPSON */
public class AFN {

    /* CONFIGURACIÓN BÁSICA DE UN AFN */
    public static int ID = 0; //static int para que no se repitan
    public static final char Epsilon = 223; // En ASCII

    /* ENUMERAMOS LOS ESTADOS */
    public SortedSet<Integer> EstadosAFN;
    public int EstadoAFNInicial;
    public HashSet<Integer> EstadosAceptacion;

    /* ALFABETO CONFORMADO DE TIPO CARÁCTER*/
    public SortedSet<Character> Alfabeto;

    /* CONSTRUCTOR */
    public AFN() {
        EstadosAFN = new TreeSet<>();
        EstadosAceptacion = new HashSet<>();
        Alfabeto = new TreeSet<>();
    }

    /* CONSTRUCTOR CON ID*/
    public AFN(int ID) {
        EstadosAFN = new TreeSet<>();
        EstadosAceptacion = new HashSet<>();
        Alfabeto = new TreeSet<>();
        this.ID = ID;
    }

    /* CONSTRUCTOR CON S*/
    public AFN crearBasico(char S) {
        //  CREAMOS EL ESTADO INICIAL
        Estado E1 = new Estado();

        //  AÑADIMOS LA TRANSICION DEL ID -> s
        E1.pushTransicion(ID++, S);

        // SI EL CARÁCTER NO ESTÁ EN EL ALFABETO, LO AÑADIMOS
        if (!Alfabeto.contains(S))
            Alfabeto.add(S);

        //  TENEMOS UN NUEVO ESTADO PARA EL AFN (EL ID)
        EstadosAFN.add(ID);

        //  LO MARCAMOS COMO ESTADO INICIAL
        EstadoAFNInicial = ID;

        //  CREAMOS EL ESTADO DE ACEPTACION
        Estado E2 = new Estado();
        E2.setIdEstado(ID++);
        E2.setEstadoAceptacion(true);

        //  AÑADIMOS EL NUEVO ESTADO (ESTADO DE ACEPTACIÓN)
        EstadosAFN.add(ID);
        EstadosAceptacion.add(ID);

        return this;
    }

    public void Opcional() {

    }

    public void CerraduraPositiva() {

    }

    public void CerraduraEstrella() {

    }

    public AFN Unir(AFN B) {
        return this;
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

    public SortedSet<Integer> getEstadosAFN() {
        return EstadosAFN;
    }

    public void setEstadosAFN(SortedSet<Integer> estadosAFN) {
        EstadosAFN = estadosAFN;
    }

    public int getEstadoAFNInicial() {
        return EstadoAFNInicial;
    }

    public void setEstadoAFNInicial(int estadoAFNInicial) {
        EstadoAFNInicial = estadoAFNInicial;
    }

    public HashSet<Integer> getEstadosAceptacion() {
        return EstadosAceptacion;
    }

    public void setEstadosAceptacion(HashSet<Integer> estadosAceptacion) {
        EstadosAceptacion = estadosAceptacion;
    }

    public SortedSet<Character> getAlfabeto() {
        return Alfabeto;
    }

    public void setAlfabeto(SortedSet<Character> alfabeto) {
        Alfabeto = alfabeto;
    }
    //////////////////////////////////////////////////////////////////////////////
}