package toulouse.miage.l3.nyx.controllers;

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
import toulouse.miage.l3.nyx.core.model.ChaineCommande;
import toulouse.miage.l3.nyx.core.service.SceneUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.service.LoggerTmp.afficherListeCommande;

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
    private TableColumn<Chaine, String> qte;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int currentQte;

    private HashMap<Chaine, Integer> chaineQuantities = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentQte = 0;
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));

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

                        add.setStyle("-fx-cursor: hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;"+ "-fx-border-radius: 50px;"
                        );

                        less.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;" + "-fx-border-radius: 50px;"
                        );

                        tf.setEditable(true);

                        add.setOnAction(e -> {
                            currentQte = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(currentQte + 1));
                            chaineQuantities.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQte + 1));
                        });

                        less.setOnAction(e -> {
                            currentQte = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(Math.max(0, currentQte - 1))); // Ensure the value is non-negative
                            chaineQuantities.put(getTableView().getItems().get(getIndex()), Math.max(0, currentQte - 1));
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
        qte.setCellFactory(cellFoctory);
        chaineTableView.setItems(listesChaines);
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
    public void goToResultat(ActionEvent actionEvent) throws IOException {
        listeCommande = chaineQuantities;
        afficherListeCommande();
        doListCommand();
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml",
                "/toulouse/miage/l3/nyx/style/resultat.css", actionEvent);
    }

    /**
     *
     * @param actionEvent
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml",
                "/toulouse/miage/l3/nyx/style/inventaire.css", actionEvent);
    }

    public void doListCommand() {
        System.out.println("okay");
        for (Map.Entry<Chaine, Integer> entry : listeCommande.entrySet()) {
            listesChainesCommandes.add(new ChaineCommande(
                        entry.getKey().getCode(),
                        entry.getKey().getNom(),
                        entry.getKey().getHashlap1(),
                        entry.getKey().getHashlap2(),
                        entry.getValue()
                    ));
        }
    }
}