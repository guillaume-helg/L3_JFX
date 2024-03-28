package toulouse.miage.l3.nyx.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import toulouse.miage.l3.nyx.core.model.Commande;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;
import toulouse.miage.l3.nyx.core.utils.UtilsCommande;
import toulouse.miage.l3.nyx.core.utils.UtilsElement;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.utils.UtilsCommande.*;


public class ResultatController implements Initializable {

    @FXML
    private Label statTemps;
    @FXML
    private Label recapitulatif;
    @FXML
    private Label stat;
    @FXML
    private Label indicateurValeur;
    @FXML
    private ProgressBar resultatCommande;
    private Stage stage;
    private Scene scene;
    private Parent root;

    // this boolean let us know if the text file have been created for the confirmation page
    protected static boolean isCommandeWritten = false;
    public static String indicValeur = String.valueOf(0);


    @FXML
    private TableView<Commande> chaineTableView;
    @FXML
    private TableColumn<Commande, String> chaineCode;
    @FXML
    private TableColumn<Commande, String> chaineNom;
    @FXML
    private TableColumn<Commande, String> chaineEntree;
    @FXML
    private TableColumn<Commande, String> chaineSortie;
    @FXML
    private TableColumn<Commande, Integer> qte;
    @FXML
    private TableColumn<Commande, String> faisabilite;

    /**
     * Navigates to the accueil view.
     * @throws IOException in case scene do not exist
     * @param actionEvent the event that triggered the navigation
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml", actionEvent);
    }

    /**
     * Navigates to the confirmation view.
     * @throws IOException in case scene do not exist
     * @param actionEvent the event that triggered the navigation
     */
    public void goToConfirmation(ActionEvent actionEvent) throws IOException {
        placeOrder();
        UtilsElement.writeElement(getElements());
        isCommandeWritten = UtilsCommande.writeResultInAFile();
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/confirmation-view.fxml", actionEvent);
    }

    /**
     * Navigates to the Chaine Production view.
     * @throws IOException in case scene do not exist
     * @param actionEvent the event that triggered the navigation
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml", actionEvent);
    }

    /**
     * Navigates to the Inventaire view.
     * @throws IOException in case scene do not exist
     * @param actionEvent the event that triggered the navigation
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml", actionEvent);
    }

    /**
     * At the start of the scene, we display in each column of the tableview a field of Chaine in listeChaineCommande
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
        // label to display the value of the command
        indicateurValeur.setText("Valeur totale : " + String.valueOf(calculRentabiliteProduction()) + "€");
        indicValeur = indicateurValeur.toString();

        // column of the tableview
        chaineCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getCode()));
        chaineNom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getNom()));
        chaineEntree.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getFormattedListeEntree()));
        chaineSortie.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getFormattedListeSortie()));

        // custom column to see if the command is feasible or not
        qte.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getQuantity()).asObject());
        faisabilite.setCellFactory(column -> new TableCell<Commande, String>() {
            @Override
        protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                    setStyle("");
                } else {
                    boolean isFeasible = getTableView().getItems().get(getIndex()).getFeasible();
                    if (isFeasible) {
                        setText("Faisable");
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setText("Non Faisable");
                        setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });

        chaineTableView.setItems(getCommandes());

        // display the number of command produce
        String[] s = getNbOrder().split("/");
        double resultat = (double) Double.parseDouble(s[0]) / Double.parseDouble(s[1]) ;
        resultatCommande.setProgress(resultat);
        stat.setText("Résultat : " + Integer.parseInt(s[0]) + "/"
                                   + (Integer.parseInt(s[0])
                                   + Integer.parseInt(s[1])) + " réalisées !");

        recapitulatif.setText(getUsedElement());

        statTemps.setText("Temps de prod : " + timeEstimation() + " heures");
    }
}