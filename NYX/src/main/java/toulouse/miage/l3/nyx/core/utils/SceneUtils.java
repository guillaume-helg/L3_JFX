package toulouse.miage.l3.nyx.core.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;


public class SceneUtils {

    /**
     * Manage the travel between each scene and loading the css of each scene
     *
     * @param fxmlPath : path of the xml file
     * @param cssPath : path of the css file
     * @param actionEvent : action (click)
     * @throws IOException : exeption
     */
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
