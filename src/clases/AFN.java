package clases;

import java.util.HashSet;

public class AFN {

    private static int IdAFN = 0;
    private static char Epsilon = '\0';

    /* EL AUTÓMATA DE THOMPSON SÓLO POSEE UN ESTADO DE ACEPTACIÓN */
    public HashSet<String> EstadosAceptacion;

    public HashSet<String> Alfabeto;
    public HashSet<String> Estados;

    private Estado EstadoInicial = new Estado();
    private Estado EstadoAceptacion = new Estado();

    /* CREAR UN AUTÓMATA BÁSICO */
    public void CrearBasico(char s) {
        Estado E = new Estado();
    }


    public void opcional() {

    }


    public void cerraduraPositiva() {

    }


    public void unir() {

    }

    public void concatenar() {

    }

}