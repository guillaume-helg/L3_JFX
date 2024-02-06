package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.listesChaines;
import static toulouse.miage.l3.nyx.core.model.Usine.listesElements;
import static toulouse.miage.l3.nyx.core.service.Utils.writeElement;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elementCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        elementNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        elementPrixA.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        elementPrixV.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        elementQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        elementUnite.setCellValueFactory(new PropertyValueFactory<>("uniteMesure"));
        elementTableView.setItems(listesElements);
    }

    public void goToAccueil(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/accueil.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void goToChaineProduction(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/chaine-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/chaine.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/inventaire.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void addElement(){
        Element e = new Element(ajoutcode.getText(),ajoutnom.getText(),
                Double.parseDouble(ajoutqte.getText()),ajoutunite.getText(),
                Double.parseDouble(ajoutprixa.getText()), Double.parseDouble(ajoutprixv.getText()));
        listesElements.add(e);
        writeElement(listesElements.toArray(new Element[0]));
    }

    public void delElement(){
        Element e = elementTableView.getSelectionModel().getSelectedItem();
        String code = e.getCode();
        for (Element el : listesElements){
            if (code == el.getCode()){
                listesElements.remove(el);
            }
        }
    }



}
