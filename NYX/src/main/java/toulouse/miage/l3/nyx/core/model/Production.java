package toulouse.miage.l3.nyx.core.model;

public class Production {
    private int niveauActivation;
    private int indicateurCommande;

    public void setNiveauActivation(int niveauActivation) {
        this.niveauActivation = niveauActivation;
    }

    public void setIndicateurCommande(int indicateurCommande) {
        this.indicateurCommande = indicateurCommande;
    }

    public static boolean estProductionPossible() {
        return false;
    }

    public static void produire() {

    }
}
