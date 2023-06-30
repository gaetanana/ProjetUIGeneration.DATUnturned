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
import java.util.*;

import static Modele.modeleApplication.getNumberOfBlueprints;
import static Modele.modeleApplication.readBlueprintsData;


public class ApplicationFXML {

    boolean isFileLoaded = false;
    int nbCraftAttribut;
    String cheminAttribut;
    private Map<Integer, List<HBox>> addedHBoxes = new HashMap<>();

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
    private TabPane tabPane;


    @FXML
    protected void onImportButtonClick() throws Exception {
        statusCreateDelete.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File file = fileChooser.showOpenDialog(buttonImport.getScene().getWindow());
        if (!file.getName().endsWith(".dat")) {
            nomFichier.setText("Erreur : Le fichier n'est pas un .dat");
            nomFichier.setStyle("-fx-fill: red;");
            tabPane.getTabs().clear();
            isFileLoaded = false;
            return;
        }
        isFileLoaded = true;
        nomFichier.setText(file.getName());

        String chemin = file.getAbsolutePath();
        int nbCraft = getNumberOfBlueprints(chemin);
        nbCraftAttribut = nbCraft;

        String nbCraftString = Integer.toString(nbCraftAttribut);
        nombreCraftPossible.setText(nbCraftString);
        Map<String, String> attributDeCraftItem = readBlueprintsData(chemin);
        System.out.println(attributDeCraftItem);
        tabPane.getTabs().clear();

        for (int i = 0; i < nbCraftAttribut; i++) {
            Tab newTab = new Tab("Craft " + (i + 1));
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(8);

            Spinner<Integer> suppliesSpinner = null;
            List<HBox>[] hboxes = new List[100];

            for (Map.Entry<String, String> entry : attributDeCraftItem.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("Blueprint_" + i + "_")) {
                    Label label = new Label(key + " : ");
                    TextField textField = new TextField(entry.getValue());
                    HBox hbox = new HBox();
                    hbox.setSpacing(8);
                    hbox.getChildren().addAll(label, textField);
                    if (key.equals("Blueprint_" + i + "_Type") || key.equals("Blueprint_" + i + "_Tool")
                            || key.equals("Blueprint_" + i + "_Level") || key.equals("Blueprint_" + i + "_Skill")
                            || key.equals("Blueprint_" + i + "_Build")) {
                        vbox.getChildren().add(hbox);
                    }
                    else if (key.equals("Blueprint_" + i + "_Supplies")) {
                        int initialSupplies = Integer.parseInt(entry.getValue());
                        suppliesSpinner = new Spinner<>(0, 100, initialSupplies);
                        HBox hboxSupplies = new HBox();
                        hboxSupplies.setSpacing(8);
                        hboxSupplies.getChildren().addAll(new Label(key + " : "), suppliesSpinner);
                        vbox.getChildren().add(hboxSupplies);
                    }
                    else if (key.startsWith("Blueprint_" + i + "_Supply")) {
                        int supplyIndex = Integer.parseInt(key.split("_")[3]);
                        if (hboxes[supplyIndex] == null) {
                            hboxes[supplyIndex] = new ArrayList<>();
                        }
                        hboxes[supplyIndex].add(hbox);
                    }
                }
            }

            // Insert supply boxes after the Supplies spinner.
            for (List<HBox> hBoxList : hboxes) {
                if (hBoxList != null) {
                    vbox.getChildren().addAll(hBoxList);
                }
            }

            if (suppliesSpinner != null) {
                int finalI = i;
                suppliesSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    System.out.println("oldValue = " + oldValue);
                    System.out.println("newValue = " + newValue);
                    if (newValue > oldValue) {
                        List<HBox> newHBoxes = new ArrayList<>();

                        Label labelId = new Label("Blueprint_" + finalI + "_Supply_" + newValue + "_ID : ");
                        TextField textFieldId = new TextField();
                        HBox hboxId = new HBox();
                        hboxId.setSpacing(8);
                        hboxId.getChildren().addAll(labelId, textFieldId);

                        Label labelAmount = new Label("Blueprint_" + finalI + "_Supply_" + newValue + "_Amount : ");
                        TextField textFieldAmount = new TextField();
                        HBox hboxAmount = new HBox();
                        hboxAmount.setSpacing(8);
                        hboxAmount.getChildren().addAll(labelAmount, textFieldAmount);

                        newHBoxes.add(hboxId);
                        newHBoxes.add(hboxAmount);

                        vbox.getChildren().addAll(newHBoxes);
                        hboxes[newValue] = newHBoxes;
                    }
                    else if (newValue == 0) {
                        for (int j = 0; j < oldValue; j++) {
                            List<HBox> toRemove = hboxes[j];
                            if (toRemove != null) {
                                vbox.getChildren().removeAll(toRemove);
                                hboxes[j] = null;
                            }
                        }
                    }
                    else if (newValue < oldValue) {
                        List<HBox> toRemove = hboxes[oldValue - 1];
                        if (toRemove != null) {
                            vbox.getChildren().removeAll(toRemove);
                            hboxes[oldValue - 1] = null;
                        }
                    }

                });
            }


            ScrollPane sp = new ScrollPane();
            sp.setContent(vbox);
            newTab.setContent(sp);
            tabPane.getTabs().add(newTab);
        }
    }



    @FXML
    protected void onCreateCraftButtonClick() {
        if (!isFileLoaded) {
            statusCreateDelete.setText("Erreur : Aucun fichier n'a été chargé");
            statusCreateDelete.setStyle("-fx-fill: red;");
            return;
        }

        //Créer une nouvelle tab
        nbCraftAttribut++;
        Tab newTab = new Tab("Craft " + (nbCraftAttribut));
        System.out.println(nbCraftAttribut);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        tabPane.getTabs().add(newTab);

    }

    @FXML
    protected void onDeleteCraftCurrentButtonClick() {
        if (!isFileLoaded) {
            statusCreateDelete.setText("Erreur : Aucun fichier n'a été chargé");
            statusCreateDelete.setStyle("-fx-fill: red;");
            return;
        }
        if (tabPane.getTabs().size() == 0) {
            statusCreateDelete.setText("Erreur : Aucun craft n'a été créé");
            statusCreateDelete.setStyle("-fx-fill: red;");
            return;
        }
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem(); // récupérer l'onglet actuellement sélectionné
        if (currentTab != null) {
            tabPane.getTabs().remove(currentTab); // supprimer l'onglet actuellement sélectionné
            nbCraftAttribut--;
            //Met à jour tous les onglets
            for (int i = 0; i < nbCraftAttribut; i++) {
                Tab tab = tabPane.getTabs().get(i);
                tab.setText("Craft " + (i + 1));
            }
        }
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
