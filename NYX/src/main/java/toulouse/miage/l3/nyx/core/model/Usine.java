package toulouse.miage.l3.nyx.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static toulouse.miage.l3.nyx.core.utils.UtilsChaine.readChaine;
import static toulouse.miage.l3.nyx.core.utils.UtilsElement.readElement;

public class Usine {

    private static Map<Integer,Usine> instance = new HashMap<>();

    public static Usine getInstance(Integer usineId) {
        if (instance.get(usineId) == null) {
            instance.put(usineId,new Usine());
        }
        return instance.get(usineId);
    }

    private Usine(){

    }

    private static ObservableList<Chaine> chaines = FXCollections.observableArrayList();
    private static ObservableList<Map.Entry<Chaine, Integer>> chainesCommandes = FXCollections.observableArrayList();
    private static ObservableList<Element> elements = FXCollections.observableArrayList();

    public void chargerChaines() {
        chaines.addAll(readChaine());
    }
    public void chargerElements() {
        elements.addAll(readElement());
    }

    /* ===========================================
     * FUNCTIONS FOR CHAINES
     * =========================================== */

    public static ObservableList<Chaine> getChaine() {
        return chaines;
    }

    /* ===========================================
     * FUNCTIONS FOR CHAINESCOMMANDES
     * =========================================== */

    public static void clearChainesCommandes() {
        chainesCommandes.clear();
    }

    public static void addAllInChainesCommandes(Set<Map.Entry<Chaine, Integer>> chaines) {
        chainesCommandes.addAll(chaines);
    }

    public static ObservableList<Map.Entry<Chaine, Integer>> getChainesCommandes() {
        return chainesCommandes;
    }

    public static int getSizeChainesCommande() {
        return chainesCommandes.size();
    }

    /* ===========================================
     * FUNCTIONS FOR ELEMENTS
     * =========================================== */

    public static ObservableList<Element> getElements() {
        return elements;
    }

    public static void addToElements(Element e) {
        elements.add(e);
    }

    public static void removeToElement(Element e) {
        elements.remove(e);
    }

    public static boolean elementsContains(Element e) {
        return elements.contains(e);
    }

    public static int elementIndexOf(Element e) {
        return elements.indexOf(e);
    }

    public static Element getElements(int index) {
        return elements.get(index);
    }

    public static void addAllElement(List<Element> elementss) {
        elements.addAll(elementss);
    }

    public static ObservableList<String> getCodeElement(){
        ObservableList<String> codeElements = FXCollections.observableArrayList();
        for(Element element : elements){
            codeElements.add(element.getCode());
        }
        return codeElements;
    }

}