package toulouse.miage.l3.nyx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.service.Utilitaire.lireChaine;
import static toulouse.miage.l3.nyx.core.service.Utilitaire.lireElements;

public class AccueilController implements Initializable {

    @FXML
    private TableView<Chaine> chaineTableView;
    @FXML
    private TableColumn<Chaine, Integer> chaineCode;
    @FXML
    private TableColumn<Chaine, String> chaineNom;
    @FXML
    private TableColumn<Chaine, HashMap<Element, Double>> chaineEntree;
    @FXML
    private TableColumn<Chaine, HashMap<Element, Double>> chaineSortie;
    @FXML
    private TableColumn<Chaine, Double> qte;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene1(java.awt.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Chaine> listesChaines = FXCollections.observableArrayList();
    ObservableList<Element> listesElements = FXCollections.observableArrayList();
    public void chargerChaines() {
        listesChaines.addAll(lireChaine());
    }
    public void chargerElements() {
        listesElements.addAll(lireElements());
    }

    public void montrerChaine() {
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("entree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("sortie"));
        qte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        chaineTableView.setItems(listesChaines);
        qte.setCellFactory(new Callback<TableColumn<Chaine, Double>, TableCell<Chaine, Double>>() {
            @Override
            public TableCell<Chaine, Double> call(TableColumn<Chaine, Double> chaineDoubleTableColumn) {
                return null;
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerElements();
        System.out.println(listesElements);
        chargerChaines();
        montrerChaine();
    }

    public void switchToScene2(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}