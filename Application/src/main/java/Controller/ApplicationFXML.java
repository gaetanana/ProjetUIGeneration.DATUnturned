package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Map;


public class ApplicationFXML {

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
}
