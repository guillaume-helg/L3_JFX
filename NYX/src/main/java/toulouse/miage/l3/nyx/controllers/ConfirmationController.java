package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing export of command in a GUI application
 * @author Guillaume HELG
 * @version 1.0
 */
public class ConfirmationController implements Initializable {

    @FXML
    private Label annonceConfirmation;
    @FXML
    private ImageView imageConfirmation;

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        String imageAdress;
        String text;

        if(ResultatController.isCommandeWritten) {
            imageAdress = "/toulouse/miage/l3/nyx/image/like.png";
            text = "Votre commande à bien été réalisée";
        } else {
            imageAdress = "/toulouse/miage/l3/nyx/image/dislike.png";
            text = "Votre commande n'a pas pu être réalisée";
        }

        annonceConfirmation.setText(text);
        Image newImage = new Image(getClass().getResource(imageAdress).toExternalForm());
        imageConfirmation.setImage(newImage);
    }

    /**
     * Enable to change the scene from confirmation to accueil
     * @param actionEvent click on the button
     * @throws IOException trigger if the view do not exist
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml", actionEvent);
    }


    /**
     * Enable to change the scene from confirmation to resultat
     * @param actionEvent click on the button
     * @throws IOException trigger if the view do not exist
     */
    public void goToResultat(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/resultat-view.fxml", actionEvent);
    }

    /**
     * Enable to change the scene from confirmation to chaine de production
     * @param actionEvent click on the button
     * @throws IOException trigger if the view do not exist
     */
    public void goToChaineProduction(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/chaineproduction-view.fxml", actionEvent);
    }

    /**
     * Enable to change the scene from confirmation to inventaire
     * @param actionEvent click on the button
     * @throws IOException trigger if the view do not exist
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml", actionEvent);
    }
}
