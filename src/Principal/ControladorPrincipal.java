package Principal;

import Clases.AFD;
import Clases.AFN;
import Clases.LR;
import Dibujar.Draw;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class ControladorPrincipal {

    @FXML
    private HashSet<AFN> ConjuntoAFN = new HashSet<>();

    @FXML
    private ImageView ImageViewGrafo = new ImageView();

    @FXML
    private TableView<String> MiTabla = new TableView<>();

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
                        ImageViewGrafo.setFitWidth(500);
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
                        ImageViewGrafo.setFitWidth(500);
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
                        ImageViewGrafo.setFitWidth(500);
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
                        ImageViewGrafo.setFitWidth(500);
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
                        ImageViewGrafo.setFitWidth(500);
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
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Archivo");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath());
            LR L = new LR();
            //0 para LALR
            //1 LR0
            //2 LR1
            L.EjecutarPython(file.getAbsolutePath(), "0");

            Vector<String> Tabla = L.getTabla();
            Tabla.remove(0);

            String[] aux = Tabla.get(0).split("(?=\\s)");
            for (int i = 0; i < aux.length; i++) {
                TableColumn<String,String> col = new TableColumn(aux[i]);
                //col.setCellValueFactory(new PropertyValueFactory<>(aux[i]));
                col.setCellValueFactory((x -> new SimpleObjectProperty<>("Just String")));
                col.setMinWidth(100);
                MiTabla.getColumns().addAll(col);
            }

            // add data
            LlenarDatos(Tabla);
            /*
            for (int i = 1; i < Tabla.size(); i++) {
                String[] aux2 = Tabla.get(i).split("(?=\\s)");

                for (int j = 0; j < aux2.length; j++) {
                    System.err.println(aux2[j]);
                    MiTabla.getItems().addAll(aux2[j]);
                }
            }*/

        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void onImportarAutómataButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OUT Files", "*.out"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath());

        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void LlenarDatos(Vector<String> V) {
        ObservableList data = FXCollections.observableArrayList();
        for (int j = 1; j < V.size(); j++) {
            String[] aux = V.get(j).split("(?=\\s)");
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 0; i < aux.length; i++) {
                row.add(aux[i]);
            }
            data.add(row);
        }
        MiTabla.setItems(data);
    }
}
