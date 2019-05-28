package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Vector;

public class LR {
    private String Tipo;
    private String cadena;
    private Vector<String> Tabla;

    public LR(String tipo) {
        Tipo = tipo;
        Tabla = new Vector<>();
    }

    public LR() {
        Tabla = new Vector<>();
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public void EjecutarPython(String nombre, String tipo) {


        String s = null;

        try {
            File out = new File(new File("src/LR/inicio.py").getAbsolutePath());
            System.out.println("python " + out.getAbsolutePath());
            Process p = Runtime.getRuntime().exec("python " + out.getAbsolutePath() + " " + nombre + " " + tipo);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            System.out.println("Salida:\n");
            String total = "";
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                Tabla.add(s);
                total += s +"\n";
            }
            cadena = total;

            System.out.println("Errores:\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Vector<String> getTabla() {
        return Tabla;
    }

    public void setTabla(Vector<String> tabla) {
        Tabla = tabla;
    }
}
