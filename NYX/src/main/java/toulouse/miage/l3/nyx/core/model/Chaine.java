/*
 * Chaine.java 06/02/2024
 * Licence MIAGE, Université Paul Sabatier, pas de copyright, pas de droit d'auteur
 */
package toulouse.miage.l3.nyx.core.model;
import java.util.HashMap;
import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.getElements;


/**
 * Class Chaines
 *
 * @author Guillaume Helg
 * @version 1.0
 */
public class Chaine {

    /** code of chaine */
    private String code;
    /** name of the chain */
    private String nom;
    /** list of each element and his quantity needed by the chaine to create something */
    protected HashMap<Element, Double> listeElementEntree;
    /** list of each element we make by using this chaine */
    private HashMap<Element, Double> listeElementSortie;
    /** toString of the Hashmap listeElementEntree */
    private String listeElementEntrees;
    /** toString of the Hashmap listeElementSortie */
    private String listeElementSorties;

    /**
     * Constructeur de la chaine (code),(nom),(listeElementEntree),(listeElementSortie)
     * @param code :
     * @param nom :
     * @param listeElementEntree
     * @param listeElementSortie
     */
    public Chaine(String code, String nom, HashMap<Element, Double> listeElementEntree, HashMap<Element, Double> listeElementSortie) {
        this.code = code;
        this.nom = nom;
        this.listeElementEntree = listeElementEntree;
        this.listeElementSortie = listeElementSortie;
        this.listeElementEntrees = getFormattedListeEntree();
        this.listeElementSorties = getFormattedListeSortie();
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return
     */
    public String getFormattedListeEntree() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : listeElementEntree.entrySet()) {
            str.append(entry.getKey().getCode());
            str.append(" * ");
            str.append(entry.getValue());
            str.append(", ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }

    /**
     *
     * @return
     */
    public String getFormattedListeSortie() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Element, Double> entry : listeElementSortie.entrySet()) {
            str.append(entry.getKey().getCode());
            str.append(" * ");
            str.append(entry.getValue());
            str.append(", ");
        }
        str.setLength(str.length() - 2);
        return str.toString();
    }

    /**
     *
     * @return
     */
    public String getListeElementEntree() {
        return getFormattedListeEntree();
    }

    /**
     *
     * @return
     */
    public String getListeElementSortie() {
        return getFormattedListeSortie();
    }

    /**
     *
     * @return
     */
    public String toString() {
        String str = this.code + "\n" + this.nom + "\n" + this.getFormattedListeEntree() + "\n" + this.getFormattedListeSortie();
        return str;
    }

    /**
     *
     * @param qtt
     * @return
     */
    public boolean isFeasible(int qtt) {
        boolean feasible = false;

        for (Map.Entry<Element, Double> currentElement : this.listeElementEntree.entrySet()) {
            Element element = currentElement.getKey();
            if (getElements().contains(element)) {
                int index = getElements().indexOf(element);
                getElements().get(index).setQuantite(element.getQuantite()-(currentElement.getValue() * qtt));
            } else {
                System.out.println("Erreur, element inexistant");
            }

            if (element.getQuantite() >= 0) {
                feasible = true;
            } else {
                return false;
            }

        }
        return feasible;
    }
}
