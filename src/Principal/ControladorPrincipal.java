package Principal;

import Clases.*;
import Clases.AnalizadorSintactico.AritmeticaBasica;
import Clases.AnalizadorSintactico.Calculadora;
import Clases.AnalizadorSintactico.ExpresionesRegulares;
import Clases.AnalizadorSintactico.GramaticaDeGramaticas;
import Dibujar.Draw;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class ControladorPrincipal {

    /*VARIABLES A UTILIZAR*/////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private HashSet<AFN> ConjuntoAFN = new HashSet<>();

    @FXML
    private ImageView ImageViewGrafo = new ImageView();

    @FXML
    private TableView<ObservableList<String>> TablaLL1 = new TableView<>();

    @FXML
    private TableView<ObservableList<String>> TablaLR0 = new TableView<>();

    @FXML
    private TableView<ObservableList<String>> TablaLR1 = new TableView<>();

    @FXML
    private TableView<ObservableList<String>> TablaLALR = new TableView<>();

    @FXML
    private JFXTextField CadenaLexico;

    @FXML
    private JFXTextField CadenaSintactico;

    @FXML
    private JFXTextField CadenaTabla;

    @FXML
    private StackPane mySP;

    private AFD AFD_Importado = null;

    private String Nombre_Archivo = null;

    private Vector<Vector<String>> MEMOLL1, MEMOLR0, MEMOLR1, MEMOLALR;

    /*ÁREA DE LOS AUTOMÁTAS*////////////////////////////////////////////////////////////////////////////////////////////
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

    /*ÁREA LOS ANALIZADORES*////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public void onAnalizadorTablaButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {

        /*COMPARAMOS QUE HAYA UNA CADENA DE TEXTO INGRESADA*/
        if (!CadenaTabla.getText().equals("")) {

            List<String> choices = new ArrayList<>();
            if (MEMOLL1 != null)
                choices.add("Tabla LL1");
            if (MEMOLR0 != null)
                choices.add("Tabla LR0");
            if (MEMOLR1 != null)
                choices.add("Tabla LR1");
            if (MEMOLALR != null)
                choices.add("Tabla LALR");
            if (!choices.isEmpty()){
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                //dialog.setTitle("Choice Dialog");
                dialog.setHeaderText("Analizar Sintácticamente");
                dialog.setContentText("Utilizar: ");
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    System.err.println(result.get());

                }else{
                    /*MOSTRAMOS UNA ALERTA DE ERROR*/
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna tabla", "Error", 0);
                }
            }else{
                /*MOSTRAMOS UNA ALERTA DE ERROR*/
                JOptionPane.showMessageDialog(null, "Crea por lo menos una tabla :o", "Error", 0);
            }

        } else {
            /*MOSTRAMOS UNA ALERTA DE ERROR*/
            JOptionPane.showMessageDialog(null, "No has ingresado ninguna cadena", "Error", 0);
        }
    }

    @FXML
    public void onLL1ButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Archivo");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            /*LA LIMPIAMOS*/
            TablaLL1.getColumns().clear();
            TablaLL1.getItems().clear();

            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath());

            LL1 L = new LL1(file.getName());
            L.ConstruirTabla();

            Vector<Vector<String>> Tabla = L.getMatriz();

            /*PONEMOS LAS COLUMNAS EN FOR MATO DE SPLIT[]*/
            String[] aux = new String[Tabla.get(0).size()];
            for (int i = 0; i < Tabla.get(0).size(); i++) {
                aux[i] = Tabla.get(0).get(i);
            }

            TestDataGenerator dataGenerator = new TestDataGenerator();
            TestDataGenerator.setLOREM(aux);

            List<String> columnNames = dataGenerator.getNext(aux.length);

            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(aux[i]);
                column.setMinWidth(100);
                column.setSortable(false);
                column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                TablaLL1.getColumns().add(column);
            }

            /*PASAMOS A FORMATO DE VECTOR DE STRING*/
            Vector<String> TablaAux = new Vector<>();
            String cadena = "", cadaux = "";
            for (int i = 1; i < Tabla.size(); i++) {
                for (int j = 0; j < Tabla.get(i).size(); j++) {
//                    System.err.println(Tabla.get(i).get(j));
//                    if (Tabla.get(i).get(j).contains(" ")){
////                        cadaux = Tabla.get(i).get(j);
////                        cadaux.replaceAll("(\\s)","");
////                        cadena += cadaux;
//                        cadena += Tabla.get(i).get(j);
//
//                    }else{
                    cadena += Tabla.get(i).get(j) + " ";
//                    }
                }
                if (i == 1) {
                    TablaAux.add(cadena);
                }
                TablaAux.add(cadena);
                cadena = "";
            }

            //System.err.println(TablaAux);

            CargarDatos(TablaAux, TablaLL1);

            /*RECUERAMOS LOS DATOS PARA QUE PODAMOS REALIZAR EL ANÁLISIS COMODAMENTE*/
            MEMOLL1 = new Vector<>();
            RecuperarDatos(TablaLL1, MEMOLL1);

        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void onLR0ButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Archivo");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            /*LA LIMPIAMOS*/
            TablaLR0.getColumns().clear();
            TablaLR0.getItems().clear();

            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath());
            LR L = new LR();
            //0 LALR
            //1 LR0
            //2 LR1
            L.EjecutarPython(file.getAbsolutePath(), "1");


            Vector<String> Tabla = L.getTabla();
            Tabla.remove(0);

            String[] aux = Tabla.get(0).split("(?=\\s)");

            TestDataGenerator dataGenerator = new TestDataGenerator();
            TestDataGenerator.setLOREM(aux);

            List<String> columnNames = dataGenerator.getNext(aux.length);

            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(aux[i]);
                column.setMinWidth(100);
                column.setSortable(false);
                column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                TablaLR0.getColumns().add(column);
            }

            CargarDatos(Tabla, TablaLR0);

            /*RECUERAMOS LOS DATOS PARA QUE PODAMOS REALIZAR EL ANÁLISIS COMODAMENTE*/
            MEMOLR0 = new Vector<>();
            RecuperarDatos(TablaLR0, MEMOLR0);


        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void onLR1ButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Archivo");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            /*LA LIMPIAMOS*/
            TablaLR1.getColumns().clear();
            TablaLR1.getItems().clear();

            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath());
            LR L = new LR();
            //0 para LALR
            //1 LR0
            //2 LR1
            L.EjecutarPython(file.getAbsolutePath(), "2");


            Vector<String> Tabla = L.getTabla();
            Tabla.remove(0);

            String[] aux = Tabla.get(0).split("(?=\\s)");

            TestDataGenerator dataGenerator = new TestDataGenerator();
            TestDataGenerator.setLOREM(aux);

            List<String> columnNames = dataGenerator.getNext(aux.length);

            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(aux[i]);
                column.setMinWidth(100);
                column.setSortable(false);
                column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                TablaLR1.getColumns().add(column);
            }

            CargarDatos(Tabla, TablaLR1);

            /*RECUERAMOS LOS DATOS PARA QUE PODAMOS REALIZAR EL ANÁLISIS COMODAMENTE*/
            MEMOLR1 = new Vector<>();
            RecuperarDatos(TablaLR1, MEMOLR1);


        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    public void onLALRButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir Archivo");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            /*LA LIMPIAMOS*/
            TablaLALR.getColumns().clear();
            TablaLALR.getItems().clear();

            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath());
            LR L = new LR();
            //0 para LALR
            //1 LR0
            //2 LR1
            L.EjecutarPython(file.getAbsolutePath(), "0");


            Vector<String> Tabla = L.getTabla();
            Tabla.remove(0);

            String[] aux = Tabla.get(0).split("(?=\\s)");

            TestDataGenerator dataGenerator = new TestDataGenerator();
            TestDataGenerator.setLOREM(aux);

            List<String> columnNames = dataGenerator.getNext(aux.length);

            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(aux[i]);
                column.setMinWidth(100);
                column.setSortable(false);
                column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );
                TablaLALR.getColumns().add(column);
            }

            CargarDatos(Tabla, TablaLALR);

            /*RECUERAMOS LOS DATOS PARA QUE PODAMOS REALIZAR EL ANÁLISIS COMODAMENTE*/
            MEMOLALR = new Vector<>();
            RecuperarDatos(TablaLALR, MEMOLALR);


        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void CargarDatos(Vector<String> T, TableView Tipo) {
        for (int i = 1; i < T.size(); i++) {
            TestDataGenerator dataGenerator = new TestDataGenerator();
            TestDataGenerator.setLOREM(T.get(i).split("(\\s)"));
            Tipo.getItems().add(
                    FXCollections.observableArrayList(
                            dataGenerator.getNext(T.size() - 1)
                    )
            );
        }
    }

    public void RecuperarDatos(TableView<ObservableList<String>> t, Vector<Vector<String>> V) {
        Vector<String> Columnas = new Vector<>(), Filas;
        int numCol = 0;

        for (TableColumn<ObservableList<String>, ?> column : t.getColumns()) {
            Columnas.add(column.getText());
            numCol++;
            //System.err.println(column.getText());
        }
        //0
        V.add(Columnas);

        for (int i = 0; i < t.getItems().size(); i++) {
            //System.err.println(t.getItems().get(i));
            Filas = new Vector<>();
            for (int j = 0; j < numCol; j++) {
                Filas.add(t.getItems().get(i).get(j));
            }
            V.add(Filas);
        }
//
//        for (Vector<String> v: V){
//            for (String k : v){
//                System.out.print(k);
//            }
//            System.out.println();
//        }
    }

    @FXML
    public void onImportarLexicoButtonClicked(javafx.scene.input.MouseEvent event) throws IOException, ClassNotFoundException {

        /*EJECUTAMOS EL FILECHOOSER*/
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir un archivo");

        /*FILTRAMOS LOS ARCHIVOS PARA SOLO VER LOS .OUT*/
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OUT Files", "*.out"));

        /*LO MOSTRAMOS EN ESTA ESCENA*/
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            AFD_Importado = new AFD();
            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath(), "Exito", JOptionPane.PLAIN_MESSAGE);
            AFD_Importado.LeerObject(file.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", 0);
        }
    }

    @FXML
    public void onAnalizadorLexicoButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {

        /*COMPARAMOS QUE HAYA UNA CADENA DE TEXTO INGRESADA*/
        if (!CadenaLexico.getText().equals("")) {
            if (AFD_Importado != null) {
                /*PODEMOS TRABAJAR CON EL AUTÓMATA SELECCIONADO*/
                AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
                AnalizarLexicamente.Lexico(CadenaLexico.getText(), AFD_Importado.getMatriz());
                Vector<Pair<String, Integer>> Resultado = AnalizarLexicamente.getTablaLexema();

                /*MOSTRAMOS LOS TOKENS ASOCIADOS*/
                System.err.println(Resultado);
                alertCreator("Analizador Léxico", "", AnalizarLexicamente.getStringTabla());

            } else {
                /*MOSTRAMOS UNA ALERTA DE ERROR*/
                JOptionPane.showMessageDialog(null, "No has importado un autómata", "Error", 0);
            }
        } else {
            /*MOSTRAMOS UNA ALERTA DE ERROR*/
            JOptionPane.showMessageDialog(null, "No has ingresado ninguna cadena", "Error", 0);
        }
    }

    @FXML
    public void onImportarSintacticoButtonClicked(javafx.scene.input.MouseEvent event) throws IOException, ClassNotFoundException {
        /*EJECUTAMOS EL FILECHOOSER*/
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir un archivo");

        /*FILTRAMOS LOS ARCHIVOS PARA SOLO VER LOS .OUT*/
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OUT Files", "*.out"));

        /*LO MOSTRAMOS EN ESTA ESCENA*/
        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            AFD_Importado = new AFD();
            Nombre_Archivo = file.getName();
            System.err.println(Nombre_Archivo);
            JOptionPane.showMessageDialog(null, "Se importó: " + file.getAbsolutePath(), "Exito", JOptionPane.PLAIN_MESSAGE);
            AFD_Importado.LeerObject(file.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo :(", "¡Error!", 0);
        }
    }

    @FXML
    public void onAnalizadorSintacticoButtonClicked(javafx.scene.input.MouseEvent event) throws IOException {

        /*COMPARAMOS QUE HAYA UNA CADENA DE TEXTO INGRESADA*/
        if (!CadenaSintactico.getText().equals("")) {
            if (AFD_Importado != null) {

                /*PODEMOS TRABAJAR CON EL AUTÓMATA SELECCIONADO*/
                AnalizadorLexico AnalizarLexicamente = new AnalizadorLexico();
                AnalizarLexicamente.Lexico(CadenaSintactico.getText(), AFD_Importado.getMatriz());
                Vector<Pair<String, Integer>> Resultado = AnalizarLexicamente.getTablaLexema();

                if (Nombre_Archivo.equals("AritmeticaBasica.out")) {
                    AritmeticaBasica AE = new AritmeticaBasica();
                    AE.AnalizarSintacticamente(AnalizarLexicamente.getPila());
                    if (AE.getR()) {
                        alertCreator("Analizador Sintáctico", "¡Éxito!", "Cadena sintácticamente correcta");
                    } else {
                        alertCreator("Analizador Sintáctico", "¡Error!", "Cadena sintácticamente incorrecta");
                    }
                } else if (Nombre_Archivo.equals("Calculadora.out")) {
                    Calculadora C = new Calculadora();
                    C.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());
                    if (C.getR()) {
                        alertCreator("Analizador Sintáctico", "¡Éxito!", "Cadena sintácticamente correcta" +
                                "\n El resultado es: " + C.getResultado());
                    } else {
                        alertCreator("Analizador Sintáctico", "¡Error!", "Cadena sintácticamente incorrecta");
                    }
                } else if (Nombre_Archivo.equals("ExpresionesRegulares.out")) {
                    ExpresionesRegulares ER = new ExpresionesRegulares();
                    ER.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());
                    if (ER.getR()) {
                        alertCreator("Analizador Sintáctico", "¡Éxito!", "Cadena sintácticamente correcta" +
                                "\n El resultado se ha dibujado ;)");
                        Draw p = new Draw();
                        AFN f = new AFN();
                        f = ER.getResultado();
                        p.Dibuja(f.DibujarAFN());

                        /* SOLUCIÓN A LA RUTA DEL AUTÓMATA */
                        Image image = new Image("file:///" + new File("src/Dibujar/automata.png").getAbsolutePath());

                        /* CONFIGURACIÓN DE LA IMAGEN A MOSTRAR */
                        ImageViewGrafo.setImage(image);
                        ImageViewGrafo.setFitWidth(500);
                        ImageViewGrafo.setPreserveRatio(true);
                        ImageViewGrafo.setSmooth(true);
                        ImageViewGrafo.setCache(true);
                    } else {
                        alertCreator("Analizador Sintáctico", "¡Error!", "Cadena sintácticamente incorrecta");
                    }
                } else if (Nombre_Archivo.equals("GramaticaDeGramaticas.out")) {
                    GramaticaDeGramaticas GG = new GramaticaDeGramaticas();
                    GG.AnalizarSintacticamente(AnalizarLexicamente.getTablaLexema());
                    if (GG.getR()) {
                        alertCreator("Analizador Sintáctico", "¡Éxito!", "Cadena sintácticamente correcta");
                    } else {
                        alertCreator("Analizador Sintáctico", "¡Error!", "Cadena sintácticamente incorrecta");
                    }
                } else {
                    System.err.println("No es ninguno previamente creado");
                }
            } else {
                /*MOSTRAMOS UNA ALERTA DE ERROR*/
                JOptionPane.showMessageDialog(null, "No has importado un autómata", "Error", 0);
            }
        } else {
            /*MOSTRAMOS UNA ALERTA DE ERROR*/
            JOptionPane.showMessageDialog(null, "No has ingresado ninguna cadena", "Error", 0);
        }
    }

    private static class TestDataGenerator {
        private static String[] LOREM;

        public static void setLOREM(String[] LOREM) {
            TestDataGenerator.LOREM = LOREM;
        }

        public static String[] getLOREM() {
            return LOREM;
        }

        private int curWord = 0;

        List<String> getNext(int nWords) {
            List<String> words = new ArrayList<>();
            for (int i = 0; i < nWords; i++) {
                if (curWord == Integer.MAX_VALUE) {
                    curWord = 0;
                }
                words.add(LOREM[curWord % LOREM.length]);
                curWord++;
            }
            return words;
        }
    }

    private void alertCreator(String title, String header, String content) {
        //mySP.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(header == null ? title : title + "\n" + header));
        dialogContent.setBody(new Text(content));
        JFXButton close = new JFXButton("Aceptar");
        close.getStyleClass().add("JFXButton");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(mySP, dialogContent, JFXDialog.DialogTransition.CENTER);
        close.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent __) {
                dialog.close();
            }
        });
        dialog.show();
    }
}


