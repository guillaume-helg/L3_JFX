package toulouse.miage.l3.nyx.core.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class SceneUtils {
    public static void goToScene(String fxmlPath, String cssPath, ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneUtils.class.getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        if (cssPath != null && !cssPath.isEmpty()) {
            String css = SceneUtils.class.getResource(cssPath).toExternalForm();
            scene.getStylesheets().add(css);
        }

        stage.setScene(scene);
        stage.show();
    }
}
