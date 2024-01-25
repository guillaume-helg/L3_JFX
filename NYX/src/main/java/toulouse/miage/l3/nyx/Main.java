package toulouse.miage.l3.nyx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static toulouse.miage.l3.nyx.core.service.Utilitaire.lireChaine;
import static toulouse.miage.l3.nyx.core.service.Utilitaire.readElement;

public class Main extends Application {
    public static ObservableList<Chaine> listesChaines = FXCollections.observableArrayList();
    public static ObservableList<Element> listesElements = FXCollections.observableArrayList();
    public void chargerChaines() {
        listesChaines.addAll(lireChaine());
    }
    public void chargerElements() {
        listesElements.addAll(readElement());
    }
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml")));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/accueil.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("NYX");
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.show();
            chargerElements();
            chargerChaines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();

    }
}