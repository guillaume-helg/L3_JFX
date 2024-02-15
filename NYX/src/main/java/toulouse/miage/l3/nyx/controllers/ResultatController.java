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
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;
import toulouse.miage.l3.nyx.core.utils.UtilsCommande;
import toulouse.miage.l3.nyx.core.utils.UtilsElement;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.*;

public class ResultatController implements Initializable {
    @FXML
    private Label stat;
    @FXML
    private Label indicateurValeur;
    @FXML
    private ProgressBar resultatCommande;
    private Stage stage;
    private Scene scene;
    private Parent root;

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
     * Enable to change the scene from resultat to accueil
     * @throws IOException
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml",
                "/toulouse/miage/l3/nyx/style/accueil.css", actionEvent);
    }

    /**
     * Enable to change the scene from resultat to confirmation
     * @param actionEvent
     */
    public void goToConfirmation(ActionEvent actionEvent) throws IOException {
        UtilsElement.writeElement(getElements());
        isCommandeWritten = UtilsCommande.writeResultInAFile();
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/confirmation-view.fxml",
                "/toulouse/miage/l3/nyx/style/confirmation.css", actionEvent);
    }

    /**
     * Enable to change the scene from resultat to chaine de production
     * @param actionEvent
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml",
                "/toulouse/miage/l3/nyx/style/chaineproduction.css", actionEvent);
    }

    /**
     * Enable to change the scene from resultat to inventaire
     * @param actionEvent
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        clearChainesCommandes();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml",
                "/toulouse/miage/l3/nyx/style/inventaire.css", actionEvent);
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
        indicateurValeur.setText(String.valueOf(calculRentabiliteProduction()) + "€");
        indicValeur = indicateurValeur.toString();

        chaineCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getCode()));
        chaineNom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getNom()));
        chaineEntree.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getListeElementEntree()));
        chaineSortie.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getChaine().getListeElementSortie()));
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

        double resultat = (double) faisible() / getSizeChainesCommande();
        resultatCommande.setProgress(resultat);
        stat.setText(faisible() + " " + "/" + " " + getSizeChainesCommande() + " réalisées !");
    }

    /**
     *
     * @return
     */
    public int faisible () {
        int count = 0;

        for(Commande c : getCommandes()) {
            if (c.getFeasible()) {
                count ++;
            }
        }
        return count;
    }

    /**
     *
     */
    public double calculRentabiliteProduction() {
        double prixTotal = 0;
        for(Commande c : getCommandes()) {
            for (Map.Entry<Element, Double> element : c.getChaine().getListeElementEntreeH().entrySet()) {
                prixTotal -= element.getKey().getPrixVente() * element.getValue() * c.getQuantity();
            }
            for (Map.Entry<Element, Double> element : c.getChaine().getListeElementSortieH().entrySet()) {
                prixTotal += element.getKey().getPrixVente() * element.getValue() * c.getQuantity();
            }
        }
        return prixTotal;
    }
}