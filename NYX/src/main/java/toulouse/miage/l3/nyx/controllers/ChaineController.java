package toulouse.miage.l3.nyx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.utils.SceneUtils;

import java.io.*;
import java.net.URL;
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
    @FXML
    private ComboBox comboBoxElemE;
    @FXML
    private ComboBox comboBoxQttE;
    @FXML
    private ComboBox comboBoxElemS;
    @FXML
    private ComboBox comboBoxQttS;
    ObservableList<String> qtt = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10");

    public void initialize(URL location, ResourceBundle resources) {
        chaineCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        chaineNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chaineEntree.setCellValueFactory(new PropertyValueFactory<>("listeElementEntree"));
        chaineSortie.setCellValueFactory(new PropertyValueFactory<>("listeElementSortie"));
        chaineTableView.setItems(getChaine());
        comboBoxElemE.setItems(getCodeElement());
        comboBoxQttE.setItems(qtt);
        comboBoxElemS.setItems(getCodeElement());
        comboBoxQttS.setItems(qtt);
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


    public void createListEntre(){createList(ajoutListeEntree,comboBoxElemE,comboBoxQttE);}

    public void createListSortie(){createList(ajoutListeSortie,comboBoxElemS,comboBoxQttS);}

    public void createList(TextField liste,ComboBox cbe,ComboBox cbq){
        if (!liste.getText().isEmpty()) {
            liste.setText(liste.getText() + ",");
        }
        liste.setText("("+liste.getText()+cbe.getSelectionModel().getSelectedItem().toString()+",");
        liste.setText(liste.getText()+cbq.getSelectionModel().getSelectedItem().toString()+")");
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