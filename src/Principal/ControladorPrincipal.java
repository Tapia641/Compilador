package Principal;

import Clases.AFD;
import Clases.AFN;
import Clases.LR;
import com.jfoenix.controls.JFXTextArea;
import Dibujar.Draw;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.swing.*;

public class ControladorPrincipal {

    @FXML
    private JFXTextArea JFXTextAreaTabla = null;
    private HashSet<AFN> ConjuntoAFN = new HashSet<>();
    private VBox pnl_scroll;

    @FXML
    private ImageView ImageViewGrafo = new ImageView();

    @FXML
    private void onCrearAFNButtonClicked(MouseEvent event) {

        String a = MessageInput("AFN", "Ingresa un carácter");

        /* PEDIMOS QUE INGRESE UN CARÁCTER */
        //String S = JOptionPane.showInputDialog(null, "Ingresa una letra", "Crear AFN", JOptionPane.QUESTION_MESSAGE);

        if (a.length() < 2) {
            if (a.equals("")) {
                JOptionPane.showMessageDialog(null, "Cadena vacia", "¡Alerta!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                AFN f = new AFN().CrearBasico(a.charAt(0));
                ConjuntoAFN.add(f);
                //JFXTextAreaTabla.setText(f.ImprimeAFN());
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
        } else if (a.length() == 3) {
            AFN f = new AFN().CrearBasico(a.charAt(0), a.charAt(2));
            ConjuntoAFN.add(f);
            //JFXTextAreaTabla.setText(f.ImprimeAFN());
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

        } else {
            JOptionPane.showMessageDialog(null, "Ingresaste más de un carácter", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void onUnirAFNButtonClicked(MouseEvent event) {
        if (ConjuntoAFN.size() <= 1) {
            JOptionPane.showMessageDialog(null, "Crea un autómata más ;)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            List<String> choices = new ArrayList<>();

            for (AFN i : ConjuntoAFN) {
                String x = String.valueOf(i.getAlfabeto());
                choices.add(x);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices.get(0), choices);
            //dialog.setTitle("");
            dialog.setHeaderText("Lista de autómatas");
            dialog.setContentText("Elige uno:");

            dialog2.setHeaderText("Lista de autómatas");
            dialog2.setContentText("Con: ");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            Optional<String> result1 = dialog2.showAndWait();

            AFN original = new AFN();

            if (result.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result.get())) {

                        //ConjuntoAFN.remove(i);
                        original = i;
                        break;
                    }
                }
            }
            if (result1.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result1.get())) {
                        original = original.Unir(i);
                        ConjuntoAFN.remove(i);
                        ConjuntoAFN.add(original);


                        Draw p = new Draw();
                        p.Dibuja(original.DibujarAFN());

                        /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                        Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                        /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                        ImageViewGrafo.setImage(image);
                        ImageViewGrafo.setFitWidth(200);
                        ImageViewGrafo.setPreserveRatio(true);
                        ImageViewGrafo.setSmooth(true);
                        ImageViewGrafo.setCache(true);

                        break;
                    }
                }

            }

        }

    }

    @FXML
    private void onCerraduraPositivaAFNButtonClicked(MouseEvent event) {
        if (ConjuntoAFN.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Crea un autómata más ;)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            List<String> choices = new ArrayList<>();

            for (AFN i : ConjuntoAFN) {
                String x = String.valueOf(i.getAlfabeto());
                choices.add(x);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            //dialog.setTitle("Choice Dialog");
            dialog.setHeaderText("Lista de autómatas");
            dialog.setContentText("Elige uno: ");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result.get())) {

                        //ConjuntoAFN.remove(i);
                        i = i.CerraduraPositiva();
                        Draw p = new Draw();
                        p.Dibuja(i.DibujarAFN());

                        /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                        Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                        /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                        ImageViewGrafo.setImage(image);
                        ImageViewGrafo.setFitWidth(200);
                        ImageViewGrafo.setPreserveRatio(true);
                        ImageViewGrafo.setSmooth(true);
                        ImageViewGrafo.setCache(true);

                        break;
                    }
                }
            }

        }

    }

    @FXML
    private void onCerraduraEstrellaAFNButtonClicked(MouseEvent event) {
        if (ConjuntoAFN.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Crea un autómata más ;)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            List<String> choices = new ArrayList<>();

            for (AFN i : ConjuntoAFN) {
                String x = String.valueOf(i.getAlfabeto());
                choices.add(x);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            //dialog.setTitle("Choice Dialog");
            dialog.setHeaderText("Lista de autómatas");
            dialog.setContentText("Elige uno: ");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result.get())) {

                        //ConjuntoAFN.remove(i);
                        i = i.CerraduraEstrella();
                        Draw p = new Draw();
                        p.Dibuja(i.DibujarAFN());

                        /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                        Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                        /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                        ImageViewGrafo.setImage(image);
                        ImageViewGrafo.setFitWidth(200);
                        ImageViewGrafo.setPreserveRatio(true);
                        ImageViewGrafo.setSmooth(true);
                        ImageViewGrafo.setCache(true);

                        break;
                    }
                }
            }

        }
    }

    @FXML
    private void onCerraduraKleenAFNButtonClicked(MouseEvent event) {
        if (ConjuntoAFN.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Crea un autómata más ;)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            List<String> choices = new ArrayList<>();

            for (AFN i : ConjuntoAFN) {
                String x = String.valueOf(i.getAlfabeto());
                choices.add(x);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            //dialog.setTitle("Choice Dialog");
            dialog.setHeaderText("Lista de autómatas");
            dialog.setContentText("Elige uno: ");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result.get())) {

                        //ConjuntoAFN.remove(i);
                        i = i.CerraduraOpcional();
                        Draw p = new Draw();
                        p.Dibuja(i.DibujarAFN());

                        /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                        Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                        /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                        ImageViewGrafo.setImage(image);
                        ImageViewGrafo.setFitWidth(200);
                        ImageViewGrafo.setPreserveRatio(true);
                        ImageViewGrafo.setSmooth(true);
                        ImageViewGrafo.setCache(true);

                        break;
                    }
                }
            }

        }

    }

    @FXML
    public void onConcatenarAFNButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        if (ConjuntoAFN.size() <= 1) {
            JOptionPane.showMessageDialog(null, "Crea un autómata más ;)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            List<String> choices = new ArrayList<>();

            for (AFN i : ConjuntoAFN) {
                String x = String.valueOf(i.getAlfabeto());
                choices.add(x);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices.get(0), choices);
            //dialog.setTitle("");
            dialog.setHeaderText("Lista de autómatas");
            dialog.setContentText("Elige uno:");

            dialog2.setHeaderText("Lista de autómatas");
            dialog2.setContentText("Con: ");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            Optional<String> result1 = dialog2.showAndWait();

            AFN original = new AFN();

            if (result.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result.get())) {

                        //ConjuntoAFN.remove(i);
                        original = i;
                        break;
                    }
                }
            }
            if (result1.isPresent()) {
                for (AFN i : ConjuntoAFN) {
                    if ((String.valueOf(i.getAlfabeto())).equals(result1.get())) {
                        original = original.Concatenar(i);
                        ConjuntoAFN.remove(i);
                        ConjuntoAFN.add(original);


                        Draw p = new Draw();
                        p.Dibuja(original.DibujarAFN());

                        /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                        Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                        /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                        ImageViewGrafo.setImage(image);
                        ImageViewGrafo.setFitWidth(200);
                        ImageViewGrafo.setPreserveRatio(true);
                        ImageViewGrafo.setSmooth(true);
                        ImageViewGrafo.setCache(true);

                        break;
                    }
                }

            }

        }

    }

    public String MessageInput(String Titulo, String mensaje) {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("");
        dialog.setHeaderText(Titulo);
        dialog.setContentText(mensaje);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return "";
    }

    @FXML
    public void onExportarAFNButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {

        if (ConjuntoAFN.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Crea un autómata más ;)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            String nombre = MessageInput("Convertir Autómata", "Elige uno: ");
            AFD afd1 = new AFD();
            afd1.convertirAFD(ConjuntoAFN, nombre);


            //ConjuntoAFN.remove(i);
            Draw p = new Draw();
            p.Dibuja(afd1.DibujarAFD());

            /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
            Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

            /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
            ImageViewGrafo.setImage(image);
            ImageViewGrafo.setFitWidth(500);
            ImageViewGrafo.setPreserveRatio(true);
            ImageViewGrafo.setSmooth(true);
            ImageViewGrafo.setCache(true);
            JOptionPane.showMessageDialog(null, "¡Eres un crack!");
        }
    }

    @FXML
    public void onAnalizadorLRButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        LR L = new LR();
        L.EjecutarPython();
    }


}
