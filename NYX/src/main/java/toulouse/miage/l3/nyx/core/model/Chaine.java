package toulouse.miage.l3.nyx.core.model;
import java.util.HashMap;
import java.util.Map;

public class Chaine {

    /** code de la chaine de production */
    private String code;

    /** nom de la chaine de production */
    private String nom;
    private HashMap<Element, Double> listeElementEntree;
    private HashMap<Element, Double> listeElementSortie;
    private String listeElementEntrees;
    private String liseElementSorties;

    public Chaine(String code, String nom, HashMap<Element, Double> listeElementEntree, HashMap<Element, Double> listeElementSortie) {
        this.code = code;
        this.nom = nom;
        this.listeElementEntree = listeElementEntree;
        this.listeElementSortie = listeElementSortie;
        this.listeElementEntrees = getFormattedListeEntree();
        this.liseElementSorties = getFormattedListeSortie();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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

    public String getListeElementEntree() {
        return getFormattedListeEntree();
    }

    public String getListeElementSortie() {
        return getFormattedListeSortie();
    }
}
