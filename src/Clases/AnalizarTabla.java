package Clases;

import java.util.Vector;

public class AnalizarTabla {
    private String cadena;
    private Vector<Vector<String>> Tabla;

    public AnalizarTabla(String cadena, Vector<Vector<String>> tabla) {
        this.cadena = cadena;
        Tabla = tabla;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Vector<Vector<String>> getTabla() {
        return Tabla;
    }

    public void setTabla(Vector<Vector<String>> tabla) {
        Tabla = tabla;
    }

    public void Analiza(){

    }
}
