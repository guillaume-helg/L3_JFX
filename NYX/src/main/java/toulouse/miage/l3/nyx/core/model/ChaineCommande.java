package toulouse.miage.l3.nyx.core.model;

import java.util.HashMap;

public class ChaineCommande extends Chaine{

    private int qte;
    public ChaineCommande(String code, String nom, HashMap<Element, Double> listeElementEntree, HashMap<Element, Double> listeElementSortie) {
        super(code, nom, listeElementEntree, listeElementSortie);
    }

    public ChaineCommande(String code, String nom, HashMap<Element, Double> listeElementEntree, HashMap<Element, Double> listeElementSortie, int qte) {
        super(code, nom, listeElementEntree, listeElementSortie);
        this.qte = qte;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
