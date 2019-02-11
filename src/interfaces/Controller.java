package interfaces;

import classes.AFN;
import com.jfoenix.controls.JFXTextArea;
import draw.Draw;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.util.HashSet;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
                AFN f = new AFN().crearBasico(S.charAt(0));
                ConjuntoAFN.add(f);
                JFXTextAreaTabla.setText(f.imprimeAFN());
                Draw p = new Draw();
                p.Dibuja(f.dibujarAFN());
            }
        } else
            JOptionPane.showMessageDialog(null, "Ingresaste más de un carácter", "¡Error!", JOptionPane.ERROR_MESSAGE);
    }

    @FXML
    private void onUnirAFNButtonClicked(MouseEvent event) {
    }

}
