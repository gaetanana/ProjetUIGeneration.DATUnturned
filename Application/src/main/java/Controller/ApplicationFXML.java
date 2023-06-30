package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static Modele.modeleApplication.getNumberOfBlueprints;
import static Modele.modeleApplication.readBlueprintsData;


public class ApplicationFXML {

    boolean isFileLoaded = false;

    @FXML
    private Button buttonAide;
    @FXML
    private Button buttonImport;
    @FXML
    private Button buttonCreateCraft;
    @FXML
    private Button buttonDeleteCraft;
    @FXML
    private Text nomFichier;
    @FXML
    private Text nombreCraftPossible;
    @FXML
    private Text statusCreateDelete;

    @FXML
    private TabPane tabPane; // Remember to add this line at the top of your controller


    @FXML
    protected void onImportButtonClick() throws Exception {
        statusCreateDelete.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File file = fileChooser.showOpenDialog(buttonImport.getScene().getWindow());
        //Vérifier que le fichier est bien un .dat
        if (!file.getName().endsWith(".dat")) {
            nomFichier.setText("Erreur : Le fichier n'est pas un .dat");
            nomFichier.setStyle("-fx-fill: red;");
            //Delete the tabs
            tabPane.getTabs().clear();
            isFileLoaded = false;
            return;
        }
        isFileLoaded = true;
        //Afficher le nom du fichier dans le textNomFichier
        nomFichier.setText(file.getName());

        String chemin = file.getAbsolutePath();
        //Récupérer le nombre de craft disponible pour cet item
        int nbCraft = getNumberOfBlueprints(chemin);
        String nbCraftString = Integer.toString(nbCraft);
        nombreCraftPossible.setText(nbCraftString);
        Map<String,String> attributDeCraftItem = readBlueprintsData(chemin);
        System.out.println(attributDeCraftItem);
        // Clear the existing tabs
        tabPane.getTabs().clear();

        // Create new tabs based on nbCraft
        for (int i = 0; i < nbCraft; i++) {
            Tab newTab = new Tab("Craft " + (i+1));
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(8);

            for (Map.Entry<String, String> entry : attributDeCraftItem.entrySet()) {
                if (entry.getKey().startsWith("Blueprint_"+i+"_")) {
                    Label label = new Label(entry.getKey() + " : ");
                    TextField textField = new TextField(entry.getValue());
                    HBox hbox = new HBox();
                    hbox.setSpacing(8);
                    hbox.getChildren().addAll(label, textField);
                    vbox.getChildren().add(hbox);
                }
            }
            newTab.setContent(vbox);
            tabPane.getTabs().add(newTab);

        }
    }


    @FXML
    protected void onCreateCraftButtonClick(){
        if(!isFileLoaded){
            statusCreateDelete.setText("Erreur : Aucun fichier n'a été chargé");
            statusCreateDelete.setStyle("-fx-fill: red;");
            return;
        }

        //Créer une nouvelle tab
        Tab newTab = new Tab("Craft " + (tabPane.getTabs().size()+1));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);


        tabPane.getTabs().add(newTab);

    }

    @FXML
    protected void onDeleteCraftCurrentButtonClick(){
        if(!isFileLoaded){
            statusCreateDelete.setText("Erreur : Aucun fichier n'a été chargé");
            statusCreateDelete.setStyle("-fx-fill: red;");
            return;
        }
        if(tabPane.getTabs().size() == 0){
            statusCreateDelete.setText("Erreur : Aucun craft n'a été créé");
            statusCreateDelete.setStyle("-fx-fill: red;");
            return;
        }
        tabPane.getTabs().remove(tabPane.getTabs().size()-1);
    }


    @FXML
    protected void onHelpButtonCick() {

        try {
            // Load the fxml file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HelpFXML.fxml"));
            Parent popupRoot = fxmlLoader.load();

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
