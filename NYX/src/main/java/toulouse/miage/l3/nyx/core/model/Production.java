package toulouse.miage.l3.nyx.core.model;

public class Production {
    private static int niveauActivation;
    private static int indicateurCommande;

    public static void setNiveauActivation(int niveauActivation) {
        Production.niveauActivation = niveauActivation;
    }

    public static void setIndicateurCommande(int indicateurCommande) {
        Production.indicateurCommande = indicateurCommande;
    }

    public static boolean estProductionPossible() {
        return false;
    }

    public static void produire() {

    }
}
