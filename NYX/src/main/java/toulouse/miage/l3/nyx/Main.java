package toulouse.miage.l3.nyx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/toulouse/miage/l3/nyx/view/hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            String css = this.getClass().getResource("/toulouse/miage/l3/nyx/style/nyx.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("NYX");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}