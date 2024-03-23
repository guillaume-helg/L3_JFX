package toulouse.miage.l3.nyx.controllers;

import javafx.beans.property.SimpleStringProperty;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.io.*;
import java.net.URL;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.utils.UtilsChaine.*;

// Controller class for managing production chains in a GUI application
public class ChaineController implements Initializable {

    // FXML fields for UI components
    @FXML
    private TextField inputQuantiteS;
    @FXML
    private TextField inputQuantiteE;
    @FXML
    private TableView<Chaine> chaineTableView;
    @FXML
    private TableColumn<Chaine, Integer> chaineCode;
    @FXML
    private TableColumn<Chaine, String> chaineNom;
    @FXML
    private TableColumn<Chaine, String> chaineEntree;
    @FXML
    private TableColumn<Chaine, String> chaineSortie;
    @FXML
    private TextField ajoutcode;
    @FXML
    private TextField ajoutnom;
    @FXML
    private TextField ajoutListeEntree;
    @FXML
    private TextField ajoutListeSortie;
    @FXML
    private ComboBox comboBoxElemE;
    @FXML
    private ComboBox comboBoxElemS;
    // Label for displaying messages to the user
    @FXML
    private Label message;

    /**
     * Initialization method called after the FXML file has been loaded.
     * Sets up the table view and combo boxes with data and adds a listener to update text fields when a chain is selected.
     *
     * @param location the location of the FXML file
     * @param resources the resources used by the FXML file
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Set cell value factories for table columns
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));
        // Populate table view and combo boxes with data
        chaineTableView.setItems(getChaine());
        comboBoxElemE.setItems(getNomElement());
        comboBoxElemS.setItems(getNomElement());

        // Add listener to update text fields when a chain is selected
        chaineTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ajoutcode.setText(newSelection.getCode());
                ajoutnom.setText(newSelection.getNom());
                ajoutListeEntree.setText(String.valueOf(newSelection.getListeEntreeCSVType()));
                ajoutListeSortie.setText(String.valueOf(newSelection.getListeSortieCSVType()));
            }
        });
    }

    /**
     * Navigates to the accueil view.
     *
     * @param actionEvent the event that triggered the navigation
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml", actionEvent);
    }

    /**
     * Navigates to the inventaire view.
     *
     * @param actionEvent the event that triggered the navigation
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml", actionEvent);
    }

    /**
     * Creates a list of elements and their quantities from user input.
     *
     * @param listField the text field for the list
     * @param elementComboBox the combo box for selecting elements
     * @param quantityField the text field for entering quantities
     */
    public void createList(TextField listField, ComboBox<String> elementComboBox, TextField quantityField) {
        String currentList = listField.getText();
        String selectedElement = elementComboBox.getSelectionModel().getSelectedItem();
        String quantity = quantityField.getText();
        if (!currentList.isEmpty()) {
            currentList += ",";
        }
        currentList += "(" + getCodeFromName(selectedElement) + "," + quantity + ")";
        if (Objects.equals(quantity, "") || selectedElement==null)
            printLabel("-fx-text-fill: red","Séléctionnez un élément et une quantité");
        else
            listField.setText(currentList);
    }

    /**
     * Creates a list of elements and their quantities from user input, needed to create an element.
     *
     */
    public void createListEntre(){createList(ajoutListeEntree,comboBoxElemE,inputQuantiteE);}
    /**
     * Creates a list of elements and their quantities from user input, that a created by a chaine.
     *
     */
    public void createListSortie(){createList(ajoutListeSortie,comboBoxElemS,inputQuantiteS);}

    /**
     * Adds a new chain to the list of chains.
     */
    public void addChaine() {
        try{
            Chaine c = new Chaine(ajoutcode.getText(), ajoutnom.getText(),
                    parseElementList(ajoutListeEntree.getText()),
                    parseElementList(ajoutListeSortie.getText()));

            if(checkCodeChaine(ajoutcode.getText())
                    && checkNomChaine(ajoutnom.getText())){
                addToChaine(c);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            printLabel("-fx-text-fill: red","Entrer vos chaines dans le format suivant :" + '\n' + "(Code Element,Nombre Element),...");
        }
    }

    /**
     * Deletes a selected chain from the list of chains.
     */
    public void delChaine() {
        Chaine c = chaineTableView.getSelectionModel().getSelectedItem();
        removeToChaine(c);
    }

    /**
     * Modifies a selected chain based on the current text fields.
     */
    public void modifyChaine() {
        Chaine post = new Chaine(ajoutcode.getText(), ajoutnom.getText(),
                parseElementList(ajoutListeEntree.getText()),
                parseElementList(ajoutListeSortie.getText()));
        Chaine pre=chaineTableView.getSelectionModel().getSelectedItem();
        if (chainesContains(pre)){
            modifyToChaine(pre,post);
            chaineTableView.refresh();
        }
    }

    /**
     * Clears all text fields.
     */
    public void clearTextField() {
        ajoutcode.setText("");
        ajoutnom.setText("");
        ajoutListeEntree.setText("");
        ajoutListeSortie.setText("");
    }

    /**
     * Updates the message label with a given style and text.
     *
     * @param style the CSS style for the message label
     * @param text the text to display in the message label
     */
    public void printLabel(String style, String text){
        message.setStyle(style);
        message.setText(text);
    }
}
