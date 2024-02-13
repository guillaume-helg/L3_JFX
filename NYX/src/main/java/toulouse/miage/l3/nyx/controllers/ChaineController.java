package toulouse.miage.l3.nyx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;

import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.utils.UtilsChaine.parseElementList;

public class ChaineController implements Initializable {

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
    private TextField ajoutcode;
    @FXML
    private TextField ajoutnom;
    @FXML
    private TextField ajoutListeEntree;
    @FXML
    private TextField ajoutListeSortie;

    private HashMap<Chaine, Integer> chaineQuantities = new HashMap<>();

    public void initialize(URL location, ResourceBundle resources) {
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));
        chaineTableView.setItems(getChaine());
    }


    /**
     * Enable to change the scene from chaine de production to accueil
     * @param actionEvent - click
     */
    public void goToAccueil(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/accueil-view.fxml",
                "/toulouse/miage/l3/nyx/style/accueil.css", actionEvent);
    }

    /**
     * Enable to change the scene from chaine de production to inventaire
     * @param actionEvent - click
     */
    public void goToInventaire(ActionEvent actionEvent) throws IOException {
        SceneUtils.goToScene("/toulouse/miage/l3/nyx/fxml/inventaire-view.fxml",
                "/toulouse/miage/l3/nyx/style/inventaire.css", actionEvent);

    }

    /**
     * Add chaine to Table and to .csv file
     */
    public void addChaine() throws IOException {
        boolean isInChaine = false;
        try{Chaine c = new Chaine(ajoutcode.getText(), ajoutnom.getText(),
                parseElementList(ajoutListeEntree.getText()),
                parseElementList(ajoutListeSortie.getText()));
            for(Chaine i: getChaine())
                if(c.getCode().equals(i.getCode())) {
                    isInChaine=false;
                }else{
                    isInChaine=true;
                }
            if(!isInChaine){
                getChaine().add(c);
            }
            getChaine().add(c);
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Entrer vos chaines dans le format suivant :"+'\n'+"(Code Element,Nombre Element),...");
            alert.showAndWait();
        }

    }

    public void delChaine(){
        Chaine c = chaineTableView.getSelectionModel().getSelectedItem();
        getChaine().remove(c);
    }

    /**
     * Clear all text fields
     */
    public void clearTextField() {
        ajoutcode.setText("");
        ajoutnom.setText("");
        ajoutListeEntree.setText("");
        ajoutListeSortie.setText("");
    }
}