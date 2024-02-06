package toulouse.miage.l3.nyx.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.service.SceneUtils;
import toulouse.miage.l3.nyx.core.service.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.*;

public class ResultatController implements Initializable {

    @FXML
    private ProgressBar resultatCommande;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Map.Entry<Chaine, Integer>> chaineTableView;
    @FXML
    private TableColumn<Map.Entry<Chaine, String>, String> chaineCode;
    @FXML
    private TableColumn<Map.Entry<Chaine, String>, String> chaineNom;
    @FXML
    private TableColumn<Map.Entry<Chaine, String>, String> chaineEntree;
    @FXML
    private TableColumn<Map.Entry<Chaine, String>, String> chaineSortie;
    @FXML
    private TableColumn<Map.Entry<Chaine, Integer>, Integer> qte;
    @FXML
    private TableColumn<Map.Entry<Chaine, Integer>, String> faisabilite;

    /**
     *
     * @throws IOException
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        listesChainesCommandes.clear();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml",
                "/toulouse/miage/l3/nyx/style/accueil.css", actionEvent);
    }

    /**
     *
     * @param actionEvent
     */
    public void goToConfirmation(ActionEvent actionEvent) throws IOException {
        Utils.writeResultInAFile();
        listesChainesCommandes.clear();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/confirmation-view.fxml",
                "/toulouse/miage/l3/nyx/style/confirmation.css", actionEvent);
    }

    /**
     *
     * @param actionEvent
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        listesChainesCommandes.clear();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml",
                "/toulouse/miage/l3/nyx/style/chaineproduction.css", actionEvent);
    }

    /**
     *
     * @param actionEvent
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        listesChainesCommandes.clear();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml",
                "/toulouse/miage/l3/nyx/style/inventaire.css", actionEvent);
    }

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        double[] faisable = {0};
        chaineCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getCode()));
        chaineNom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getNom()));
        chaineEntree.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getListeElementEntree()));
        chaineSortie.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getListeElementSortie()));
        qte.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue()).asObject());
        faisabilite.setCellValueFactory(param -> new SimpleStringProperty(""));
        faisabilite.setCellFactory(new Callback<TableColumn<Map.Entry<Chaine, Integer>, String>, TableCell<Map.Entry<Chaine, Integer>, String>>() {
            @Override
            public TableCell<Map.Entry<Chaine, Integer>, String> call(TableColumn<Map.Entry<Chaine, Integer>, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                            setStyle("");
                        } else {
                            Chaine chaine = param.getTableView().getItems().get(getIndex()).getKey();
                            int quantity = param.getTableView().getItems().get(getIndex()).getValue();
                            boolean isFaisable = chaine.isFeasible(quantity);

                            if (isFaisable) {
                                setText("Faisable");
                                setStyle("-fx-text-fill: green;");
                            } else {
                                setText("Non Faisable");
                                setStyle("-fx-text-fill: red;");
                            }
                            faisable[0] += 1;
                        }
                    }
                };
            }
        });

        chaineTableView.setItems(listesChainesCommandes);

        double resultat = faisable[0] / listesChainesCommandes.size();
        resultatCommande.setProgress(resultat);
    }
}