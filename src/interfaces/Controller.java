package interfaces;

import classes.AFN;
import com.jfoenix.controls.JFXTextArea;
import draw.Draw;
import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.File;
import java.util.HashSet;

import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private JFXTextArea JFXTextAreaTabla = null;
    private HashSet<AFN> ConjuntoAFN = new HashSet<>();

    @FXML
    private ImageView ImageViewGrafo;

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
                File input = new File(new File("src/draw/automata.png").getAbsolutePath()); // Windows
                ImageViewGrafo.setImage(Image.impl_fromPlatformImage(input));
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingresaste más de un carácter", "¡Error!", JOptionPane.ERROR_MESSAGE);
    }

    @FXML
    private void onUnirAFNButtonClicked(MouseEvent event) {
    }

}
