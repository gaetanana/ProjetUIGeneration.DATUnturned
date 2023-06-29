package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class ApplicationFXML {


    @FXML
    private Button buttonAide;

    @FXML
    private Button buttonImport;
    @FXML
    private Text nomFichier;

    @FXML
    protected void onImportButtonClick() throws Exception {
        System.out.println("click");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File file = fileChooser.showOpenDialog(buttonImport.getScene().getWindow());
        //Vérifier que le fichier est bien un .dat
        if (!file.getName().endsWith(".dat")) {
            nomFichier.setText("Erreur : Le fichier n'est pas un .dat");
            return;
        }
        //Afficher le nom du fichier dans le textNomFichier
        nomFichier.setText(file.getName());


    }

    @FXML
    protected void onHelpButtonCick() {

        try {
            // Load the fxml file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HelpFXML.fxml"));
            Parent popupRoot = fxmlLoader.load();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(popupRoot));

            // Show the popup stage
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
