package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.getElements;
import static toulouse.miage.l3.nyx.core.service.Utils.isTheCodeInArray;

public class InventaireController implements Initializable {
    public static final List<Character> verifcode =
            Collections.unmodifiableList(Arrays.asList('0','1','2','3','4','5','6','7','8','0'));

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
    @FXML
    private Label message;

    /**
     * The application will load this function at the start when it's called
     *
     * The code will display a table view, with custom columns
     *
     * It also listens to clicks on table view rows in order to fill text fields
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
        elementTableView.setItems(getElements());

        elementTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ajoutcode.setText(newSelection.getCode());
                ajoutnom.setText(newSelection.getNom());
                ajoutqte.setText(String.valueOf(newSelection.getQuantite()));
                ajoutunite.setText(newSelection.getUniteMesure());
                ajoutprixa.setText(String.valueOf(newSelection.getPrixAchat()));
                ajoutprixv.setText(String.valueOf(newSelection.getPrixVente()));
            }
        });
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

    public boolean isNiceFormatCode(String code){
        if (code.length() > 2) return false;
        if (code.charAt(0) != 'E') return false;
        for (int i = 1; i < code.length(); i++){
            if (!verifcode.contains(code.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Create an element and add it in the table view
     */
    public void addElement(){
        Element e = new Element(ajoutcode.getText(),ajoutnom.getText(),
                Double.parseDouble(ajoutqte.getText()), ajoutunite.getText(),
                Double.parseDouble(ajoutprixa.getText()), Double.parseDouble(ajoutprixv.getText()));
        if (isTheCodeInArray(e)) {
            getElements().get(getElements().indexOf(e)).setQuantite(getElements().get(getElements().indexOf(e)).getQuantite() + e.getQuantite());
            message.setStyle("-fx-text-fill: green");
            message.setText("Quantité mise à jour");
            elementTableView.refresh();
            ajoutqte.setText(String.valueOf(getElements(getElements().indexOf(e)).getQuantite()));
        }
        else {
            getElements().add(e);
            message.setStyle("-fx-text-fill: green");
            message.setText("Element ajouté");
        }
    }

    /**
     * Remove the selected element from the table view
     */
    public Element delElement(){
        Element e = elementTableView.getSelectionModel().getSelectedItem();
        getElements().remove(e);
        message.setStyle("-fx-text-fill: red");
        message.setText("Element supprimé");
        return e;
    }

    /**
     * Modify the selected element from the table view
     */
    public void modifyElement() {
        Element a = new Element(ajoutcode.getText(),ajoutnom.getText(),
                Double.parseDouble(ajoutqte.getText()), ajoutunite.getText(),
                Double.parseDouble(ajoutprixa.getText()), Double.parseDouble(ajoutprixv.getText()));
        Element e = elementTableView.getSelectionModel().getSelectedItem();
        if (isTheCodeInArray(e)){
            getElements().set(getElements().indexOf(e),a);
            elementTableView.refresh();

        }
        else{
            Element b = delElement();
            getElements().add(e);
        }
        message.setStyle("-fx-text-fill: green");
        message.setText("Element modifié");
    }

    /**
     * Clear all text fields
     */
    public void clearTextField() {
        ajoutcode.setText("");
        ajoutnom.setText("");
        ajoutqte.setText("");
        ajoutunite.setText("");
        ajoutprixa.setText("");
        ajoutprixv.setText("");
        message.setText("");
    }
}
