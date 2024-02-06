package toulouse.miage.l3.nyx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import toulouse.miage.l3.nyx.core.model.Usine;

import java.io.IOException;
import java.util.Objects;

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

            Usine usine = new Usine();
            usine.chargerElements();
            usine.chargerChaines();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}