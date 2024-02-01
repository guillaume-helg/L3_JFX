package toulouse.miage.l3.nyx.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

import static toulouse.miage.l3.nyx.core.service.Utils.readChaine;
import static toulouse.miage.l3.nyx.core.service.Utils.readElement;

public class Usine {
    private String name;
    private String address;

    public Usine(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Usine() {
    }

    public static ObservableList<Chaine> listesChaines = FXCollections.observableArrayList();
    public static ObservableList<ChaineCommande> listesChainesCommandes = FXCollections.observableArrayList();

    public static ObservableList<Element> listesElements = FXCollections.observableArrayList();
    public static ObservableList<Production> listesProductions = FXCollections.observableArrayList();

    public static HashMap<Chaine, Integer> listeCommande = new HashMap<>();
    public void chargerChaines() {
        listesChaines.addAll(readChaine());
    }
    public void chargerElements() {
        listesElements.addAll(readElement());
    }


}
