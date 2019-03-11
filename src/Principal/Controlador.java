package Principal;

import Clases.AFN;
import com.jfoenix.controls.JFXTextArea;
import Dibujar.Draw;
import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;

import javax.swing.*;

public class Controlador {

    @FXML
    private JFXTextArea JFXTextAreaTabla = null;
    private HashSet<AFN> ConjuntoAFN = new HashSet<>();

    @FXML
    private ImageView ImageViewGrafo = new ImageView();

    @FXML
    private void onCrearAFNButtonClicked(MouseEvent event) {

        /* PEDIMOS QUE INGRESE UN CARÁCTER */
        String S = JOptionPane.showInputDialog(null, "Ingresa una letra", "Crear AFN", JOptionPane.QUESTION_MESSAGE);

        if (S.length() < 2) {
            if (S.equals("")) {
                JOptionPane.showMessageDialog(null, "Cadena vacia", "¡Alerta!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                AFN f = new AFN().CrearBasico(S.charAt(0));
                ConjuntoAFN.add(f);
                JFXTextAreaTabla.setText(f.ImprimeAFN());
                Draw p = new Draw();
                p.Dibuja(f.DibujarAFN());

                /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                ImageViewGrafo.setImage(image);
                ImageViewGrafo.setFitWidth(200);
                ImageViewGrafo.setPreserveRatio(true);
                ImageViewGrafo.setSmooth(true);
                ImageViewGrafo.setCache(true);
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingresaste más de un carácter", "¡Error!", JOptionPane.ERROR_MESSAGE);
    }

    @FXML
    private void onUnirAFNButtonClicked(MouseEvent event) {
    }

    public void Unir(Stage unirInterfaz) throws Exception {
    }

}
