package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.service.SceneUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.addToElements;
import static toulouse.miage.l3.nyx.core.model.Usine.elements;

public class InventaireController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<Element> elementTableView;
    @FXML
    private TableColumn<Element, Integer> elementCode;
    @FXML
    private TableColumn<Element, String> elementNom;
    @FXML
    private TableColumn<Element, Double> elementPrixA;
    @FXML
    private TableColumn<Element, Double> elementPrixV;
    @FXML
    private TableColumn<Element, Double> elementQuantite;
    @FXML
    private TableColumn<Element, String> elementUnite;
    @FXML
    private TextField ajoutcode;
    @FXML
    private TextField ajoutnom;
    @FXML
    private TextField ajoutprixa;
    @FXML
    private TextField ajoutprixv;
    @FXML
    private TextField ajoutqte;
    @FXML
    private TextField ajoutunite;

    /**
     * The application will load this function at the start when it's called
     *
     * The code will display a table view, with custom columns
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elementCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        elementNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        elementPrixA.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        elementPrixV.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        elementQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        elementUnite.setCellValueFactory(new PropertyValueFactory<>("uniteMesure"));
        elementTableView.setItems(elements);
    }

    /**
     * Enable to change the scene from inventaire to acceuil
     * @param actionEvent - click
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml",
                "/toulouse/miage/l3/nyx/style/accueil.css", actionEvent);
    }

    /**
     * Enable to change the scene from inventaire to chaine de production
     * @param actionEvent
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml",
                "/toulouse/miage/l3/nyx/style/chaineproduction.css", actionEvent);
    }

    /**
     * Enable to change the scene from inventaire to inventaire (refresh)
     * @param actionEvent
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml",
                "/toulouse/miage/l3/nyx/style/inventaire.css", actionEvent);
    }

    /**
     * Create an element and add it in the table view
     */
    public void addElement(){
        Element e = new Element(ajoutcode.getText(),ajoutnom.getText(),
                Double.parseDouble(ajoutqte.getText()), ajoutunite.getText(),
                Double.parseDouble(ajoutprixa.getText()), Double.parseDouble(ajoutprixv.getText()));
        addToElements(e);
    }

    /**
     * Remove the selected element from the table view
     */
    public void delElement(){
        Element e = elementTableView.getSelectionModel().getSelectedItem();
        elements.remove(e);
    }
}
