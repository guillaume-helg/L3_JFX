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
     * @param fxmlPath : path of the xml file
     * @param actionEvent : action (click)
     * @throws IOException : exception in case the scene do not exist
     */
    public static void goToScene(String fxmlPath, ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneUtils.class.getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        String css = SceneUtils.class.getResource("/toulouse/miage/l3/nyx/style/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
