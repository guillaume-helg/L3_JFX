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
import toulouse.miage.l3.nyx.core.model.Usine;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static toulouse.miage.l3.nyx.core.service.Utilitaire.lireChaine;
import static toulouse.miage.l3.nyx.core.service.Utilitaire.readElement;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml")));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/accueil.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("NYX");
            stage.setScene(scene);
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.show();
            Usine u = new Usine();
            u.chargerElements();
            u.chargerChaines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();

    }
}