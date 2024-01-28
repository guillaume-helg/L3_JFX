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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import static toulouse.miage.l3.nyx.core.model.Usine.*;

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
    private int currentValue;

    private HashMap<Chaine, Integer> chaineQuantities = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentValue = 0;
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

                        tf.setText(String.valueOf(currentValue));
                        tf.setStyle(" -fx-cursor: hand ;" + "-glyph-size:50px;" + "-fx-fill:#ff1744;" +
                                    "-fx-cell-size: 80px;" + "-fx-start-margin: 30px;" + "-fx-pref-width: 60px;" +
                                    "-fx-alignment: center;"
                        );

                        add.setStyle("-fx-cursor: hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;"
                        );

                        less.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;"
                        );

                        tf.setEditable(true);

                        add.setOnAction(e -> {
                            currentValue = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(currentValue + 1));
                            chaineQuantities.put(getTableView().getItems().get(getIndex()), Math.max(0, currentValue + 1));
                        });

                        less.setOnAction(e -> {
                            currentValue = Integer.parseInt(tf.getText());
                            tf.setText(Integer.toString(Math.max(0, currentValue - 1))); // Ensure the value is non-negative
                            chaineQuantities.put(getTableView().getItems().get(getIndex()), Math.max(0, currentValue - 1));
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

    public HashMap<Chaine, Integer> getChaineQuantities() {
        return chaineQuantities;
    }

    public void switchToScene2(ActionEvent actionEvent) throws IOException {
        listeCommande = getChaineQuantities();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/resultat.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        afficherListeCommande();
    }

    public void afficherListeCommande() {
        for (Map.Entry<Chaine, Integer> entry : listeCommande.entrySet()) {
            System.out.println("Clé : " + entry.getKey() + ", Valeur : " + entry.getValue());
        }
    }
}