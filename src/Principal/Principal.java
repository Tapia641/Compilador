package Principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PrincipalFXML.fxml"));
        primaryStage.setTitle("ESCOM");
        Scene primary = new Scene(root, 1910, 1000);
        primary.getStylesheets().add(Principal.class.getResource("bootstrap2.css").toExternalForm());
        primaryStage.setScene(primary);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
