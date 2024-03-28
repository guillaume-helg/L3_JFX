package toulouse.miage.l3.nyx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.utils.UtilsCommande.parseHashmapToCommand;

/**
 * Controller for the accueil IHM
 * @author Guillaume Helg
 * @version 1.0
 */
public class AccueilController implements Initializable {
    @FXML
    public TableColumn<Chaine, String> temps;
    @FXML
    private TableView<Chaine> chaineTableView;
    @FXML
    private TableColumn<Chaine, String> chaineCode;
    @FXML
    private TableColumn<Chaine, String> chaineNom;
    @FXML
    private TableColumn<Chaine, String> chaineEntree;
    @FXML
    private TableColumn<Chaine, String> chaineSortie;
    @FXML
    private TableColumn<Chaine, String> qte;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int currentQte;

    private Map<Chaine, Integer> listeCommandeAccueil = new HashMap<>();

    /**
     * The application will load this function at the start when it's called
     *
     * The code will display a table view, with custom columns
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentQte = 0; // initialize to 0 when you go in this page

        // set value for each column of the tableview
        chaineCode.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCode()));
        chaineNom.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNom()));
        chaineEntree.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFormattedListeEntree()));
        chaineSortie.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFormattedListeSortie()));

        // custom button to set the quantity of the chaine
        Callback<TableColumn<Chaine, String>, TableCell<Chaine, String>> cellFoctory = (TableColumn<Chaine, String> param) -> {
            final TableCell<Chaine, String> cell = new TableCell<Chaine, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button add = new Button("+");
                        Button less = new Button("-");
                        TextFieldTableCell tf = new TextFieldTableCell();

                        tf.setText(String.valueOf(currentQte));
                        tf.setStyle(" -fx-cursor: hand ;" + "-glyph-size:50px;" + "-fx-fill:#ff1744;" +
                                    "-fx-cell-size: 80px;" + "-fx-start-margin: 30px;" + "-fx-pref-width: 60px;" +
                                    "-fx-alignment: center;"
                        );

                        add.setStyle("-fx-cursor: hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;"+ "-fx-border-radius: 50px;");

                        less.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;" + "-fx-border-radius: 50px;"
                        );

                        tf.setEditable(true);

                        // function to change on clic the value of the label
                        add.setOnAction(e -> {
                            currentQte = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(currentQte + 1));
                            listeCommandeAccueil.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQte + 1));
                        });

                        // function to change on clic the value of the label
                        less.setOnAction(e -> {
                            currentQte = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(Math.max(0, currentQte - 1)));
                            listeCommandeAccueil.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQte - 1));
                            if (currentQte - 1 == 0) {
                                listeCommandeAccueil.remove(getTableView().getItems().get(getIndex()));
                            }
                        });

                        HBox managebtn = new HBox();
                        managebtn.getChildren().addAll(add, tf, less);
                        managebtn.setStyle("-fx-alignment:center");

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };

            return cell;
        };

        temps.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getTime())));


        qte.setCellFactory(cellFoctory);
        chaineTableView.setItems(getChaine());
    }

    /**
     * Enable to change the scene from accueil to resultat
     * @param actionEvent - click
     * @throws IOException in case the scene does not exist
     */
    public void goToResultat(ActionEvent actionEvent) throws IOException {
        parseHashmapToCommand(listeCommandeAccueil);
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml",
                actionEvent);
    }

    /**
     * Enable to change the scene from accueil to chaine de production
     * @param actionEvent - click
     * @throws IOException in case the scene do not exist
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml",
                actionEvent);
    }

    /**
     * Enable to change the scene from accueil to inventaire
     * @param actionEvent - click
     * @throws IOException in case the scene do not exist
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml", actionEvent);
    }
}