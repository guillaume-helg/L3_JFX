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


public class ChaineController implements Initializable {
    
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

    public void initialize(URL location, ResourceBundle resources) {
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFormattedListeEntree()));
        chaineSortie.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFormattedListeSortie()));
        chaineTableView.setItems(getChaine());
        comboBoxElemE.setItems(getCodeElement());
        comboBoxElemS.setItems(getCodeElement());

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
     * Enable to change the scene from chaine de production to accueil
     * @param actionEvent - click
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml", actionEvent);
    }

    /**
     * Enable to change the scene from chaine de production to inventaire
     * @param actionEvent - click
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml", actionEvent);

    }


    /**
     * Create list from input
     * @param listField
     * the type of liste to create
     * @param elementComboBox
     * Combobox for the element
     * @param quantityField
     * TextField for the quantity
     */

    public void createList(TextField listField, ComboBox<String> elementComboBox, TextField quantityField) {
        String currentList = listField.getText();
        String selectedElement = elementComboBox.getSelectionModel().getSelectedItem();
        String quantity = quantityField.getText();
        if (!currentList.isEmpty()) {
            currentList += ",";
        }
        currentList += "(" + selectedElement + "," + quantity + ")";
        if (Objects.equals(quantity, "") || selectedElement==null)
            showErrorAlert("Séléctionnez un élément et une quantité");
        else
            listField.setText(currentList);
    }
    /**
     * two methods called by the button add
     */
    public void createListEntre(){createList(ajoutListeEntree,comboBoxElemE,inputQuantiteE);}

    public void createListSortie(){createList(ajoutListeSortie,comboBoxElemS,inputQuantiteS);}

    /**
     * Add chaine to list of chaines
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
            showErrorAlert("Entrer vos chaines dans le format suivant :" + '\n' + "(Code Element,Nombre Element),...");
        }
    }

    /**
     * Delete chaine from list of chaines
     */
    public void delChaine() {
        Chaine c = chaineTableView.getSelectionModel().getSelectedItem();
        removeToChaine(c);
    }

    /**
     * Enable to modify selected chaine from TextField
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
     * Clear all text fields
     */
    public void clearTextField() {
        ajoutcode.setText("");
        ajoutnom.setText("");
        ajoutListeEntree.setText("");
        ajoutListeSortie.setText("");
    }

    /**
     * Display alert message on the GUI screen of the Chaine de production page
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

}