package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class LR {
    private String Tipo;

    public LR(String tipo) {
        Tipo = tipo;
    }

    public LR() {
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public void EjecutarPython() {

        String s = null;

        try {
            File out = new File(new File("src/LR/inicio.py").getAbsolutePath());
            System.out.println("python " + out.getAbsolutePath());
            Process p = Runtime.getRuntime().exec("python " + out.getAbsolutePath());

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            System.out.println("Salida:\n");
            String total = "";
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                total += s;
            }

            System.out.println("Errores:\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
