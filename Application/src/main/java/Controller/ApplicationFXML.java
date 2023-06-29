package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Map;

import static Modele.modeleApplication.readDATFile;

public class ApplicationFXML {
    @FXML
    private Button buttonImport;

    @FXML
    protected void onImportButtonClick() {
        System.out.println("click");
        System.out.println(buttonImport.getText());
    }
}
