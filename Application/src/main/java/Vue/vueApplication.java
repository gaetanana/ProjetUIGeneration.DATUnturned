package Vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Map;

import static Modele.modeleApplication.readDATFile;

public class vueApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Modification de fichier DAT");
        // Chargez la scène à partir du fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ApplicationFXML.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}