package toulouse.miage.l3.nyx.controllers;

import javafx.collections.ObservableList;
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
import toulouse.miage.l3.nyx.core.model.ChaineCommande;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.service.SceneUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.*;

public class ResultatController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<ChaineCommande> chaineTableView;
    @FXML
    private TableColumn<ChaineCommande, String> chaineCode;
    @FXML
    private TableColumn<ChaineCommande, String> chaineNom;
    @FXML
    private TableColumn<ChaineCommande, String> chaineEntree;
    @FXML
    private TableColumn<ChaineCommande, String> chaineSortie;
    @FXML
    private TableColumn<ChaineCommande, String> qte;
    @FXML
    TableColumn<ChaineCommande, String> faisabilite;

    /**
     *
     * @throws IOException
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml",
                "/toulouse/miage/l3/nyx/style/accueil.css", actionEvent);
    }

    /**
     *
     * @param actionEvent
     */
    public void goToConfirmation(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/confirmation-view.fxml",
                "/toulouse/miage/l3/nyx/style/confirmation.css", actionEvent);
    }


    /**
     *
     * @param actionEvent
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml",
                "/toulouse/miage/l3/nyx/style/chaineproduction.css", actionEvent);
    }

    /**
     *
     * @param actionEvent
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml",
                "/toulouse/miage/l3/nyx/style/inventaire.css", actionEvent);
    }

    public void initialize(URL location, ResourceBundle resources) {
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));
        qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        chaineTableView.setItems(listesChainesCommandes);
    }
}