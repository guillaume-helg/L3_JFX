package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.listesChaines;
import static toulouse.miage.l3.nyx.core.model.Usine.listesElements;

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
    private TableColumn<Element, String> elementPrixA;
    @FXML
    private TableColumn<Element, String> elementPrixV;
    @FXML
    private TableColumn<Element, String> qte;
    @FXML
    private TableColumn<Element,String> unite;

    public void goToAccueil(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/accueil.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void goToChaineProduction(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/chaine-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/chaine.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/inventaire.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elementCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        elementNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        elementPrixA.setCellValueFactory(new PropertyValueFactory<>("prix achat"));
        elementPrixV.setCellValueFactory(new PropertyValueFactory<>("prix vente"));
        qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        unite.setCellValueFactory(new PropertyValueFactory<>("unité"));
        elementTableView.setItems(listesElements);
    }
}
