package toulouse.miage.l3.nyx;
import java.util.HashMap;

public class Chaine {

    private String code;
    private String nom;
    private HashMap<Element, Integer> listeElementEntree;
    private HashMap<Element, Integer> listeElementSortie;

    public Chaine(String code, String nom, HashMap<Element, Integer> listeElementEntree, HashMap<Element, Integer> listeElementSortie) {
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

    public HashMap<Element, Integer> getListeElementEntree() {
        return listeElementEntree;
    }

    public void setListeElementEntree(HashMap<Element, Integer> listeElementEntree) {
        this.listeElementEntree = listeElementEntree;
    }

    public HashMap<Element, Integer> getListeElementSortie() {
        return listeElementSortie;
    }

    public void setListeElementSortie(HashMap<Element, Integer> listeElementSortie) {
        this.listeElementSortie = listeElementSortie;
    }

}
