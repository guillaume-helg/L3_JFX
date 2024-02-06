package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.service.SceneUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
public class ChaineController implements Initializable {

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
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int currentValue;

    private HashMap<Chaine, Integer> chaineQuantities = new HashMap<>();

    public void initialize(URL location, ResourceBundle resources) {
        currentValue = 0;
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));

        Callback<TableColumn<Chaine, String>, TableCell<Chaine, String>> cellFoctory = (TableColumn<Chaine, String> param) -> {
            final TableCell<Chaine, String> cell = new TableCell<Chaine, String>() {};
            return cell;
        };
        chaineTableView.setItems(listesChaines);
    }



    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml",
                "/toulouse/miage/l3/nyx/style/accueil.css", actionEvent);
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
}
