package toulouse.miage.l3.nyx.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

import static toulouse.miage.l3.nyx.core.service.Utilitaire.readChaine;
import static toulouse.miage.l3.nyx.core.service.Utilitaire.readElement;

public class Usine {
    private String name;
    private String address;

    public static ObservableList<Chaine> listesChaines = FXCollections.observableArrayList();
    public static ObservableList<Element> listesElements = FXCollections.observableArrayList();
    public static ObservableList<Commande> listesCommandes = FXCollections.observableArrayList();
    public static ObservableList<Production> listesProductions = FXCollections.observableArrayList();

    public static HashMap<Chaine, Integer> listeCommande = new HashMap<>();
    public void chargerChaines() {
        listesChaines.addAll(readChaine());
    }
    public void chargerElements() {
        listesElements.addAll(readElement());
    }


}
