package toulouse.miage.l3.nyx.core.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.util.*;

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

    private Usine(){}

    private static ObservableList<Chaine> chaines = FXCollections.observableArrayList();
    private static ObservableList<Commande> commandes = FXCollections.observableArrayList();
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
        return FXCollections.unmodifiableObservableList(chaines);
    }
    public static void addToChaine(Chaine c) {
        chaines.add(c);
    }

    public static void removeToChaine(Chaine c) {
        chaines.remove(c);
    }

    /* ===========================================
     * FUNCTIONS FOR COMMANDES
     * =========================================== */

    public static void clearChainesCommandes() {
        commandes.clear();
    }

    public static void addAllInChainesCommandes(Commande commande) {
        commandes.addAll(commande);
    }

    public static void addToCommandes(Commande commande) {
        commandes.add(commande);
    }

    public static ObservableList<Commande> getCommandes() {
        return FXCollections.unmodifiableObservableList(commandes);
    }

    public static int getSizeChainesCommande() {
        return commandes.size();
    }

    public static void removeToCommande(Commande c) {
        commandes.remove(c);
    }

    public static void removeToCommandeByChaine(Chaine c) {
        for(Commande commande : commandes) {
            if (commande.getChaine().equals(c)) {
                removeToCommande(commande);
            }
        }
    }

    /* ===========================================
     * FUNCTIONS FOR ELEMENTS
     * =========================================== */

    public static ObservableList<Element> getElements() {
        return FXCollections.unmodifiableObservableList(elements);
    }

    public static void addToElements(Element e) {
        elements.add(e);
    }

    public static void removeToElement(Element e) {
        elements.remove(e);
    }

    public static void modifyToElement(Element elpre, Element elpost) {elements.set(elementIndexOf(elpre),elpost);}

    public static Double addQuantitiesOfElement(Element e) {
        Double totqte = elements.get(elementIndexOf(e)).getQuantite() + e.getQuantite();
        elements.get(elementIndexOf(e)).setQuantite(totqte);
        return totqte;
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

    public static Element getElementByCode(String code){
        for(Element element : elements){
            if (Objects.equals(element.getCode(), code)) return element;
        }
        return null;
    }

    public static Element getElementByNamePriceUnit(String name, String pp, String sp, String unit){
        for(Element element : elements){
            if (Objects.equals(element.getNom(), name) &&
                    Objects.equals(element.getPrixAchat().toString(), pp) &&
                    Objects.equals(element.getPrixVente().toString(), sp) &&
                    Objects.equals(element.getUniteMesure().name(), unit)) return element;
        }
        return null;
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