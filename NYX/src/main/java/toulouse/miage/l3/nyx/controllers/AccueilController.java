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
import toulouse.miage.l3.nyx.core.model.Chaine;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.Main.listesChaines;

public class AccueilController implements Initializable {

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
    private TableColumn<Chaine, Integer> qte;
    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Display the content of listesChaines in the tableview
     */
    public void displayTableView() {
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));
        // TODO : voir pour faire 2 buttons avec un input
        //qte.setCellFactory(NumericComboBoxTableCell.forTableColumn(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        qte.setCellValueFactory(new PropertyValueFactory<>("CustomNumericTableCell"));
        chaineTableView.setItems(listesChaines);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayTableView();
    }

    public void switchToScene2(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/resultat.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}