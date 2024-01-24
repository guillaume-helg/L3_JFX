package toulouse.miage.l3.nyx.core.model;
import java.util.HashMap;

public class Chaine {

    /** code de la chaine de production */
    private String code;

    /** nom de la chaine de production */
    private String nom;
    private HashMap<Element, Double> listeElementEntree;
    private HashMap<Element, Double> listeElementSortie;

    public Chaine(String code, String nom, HashMap<Element, Double> listeElementEntree, HashMap<Element, Double> listeElementSortie) {
        this.code = code;
        this.nom = nom;
        this.listeElementEntree = listeElementEntree;
        this.listeElementSortie = listeElementSortie;
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
}
